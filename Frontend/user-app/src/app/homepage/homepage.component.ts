import { Component, OnInit, Input } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';


@Component({
  selector: 'app-homepage',
  templateUrl: './homepage.component.html',
  styleUrls: ['./homepage.component.css']
})
export class HomepageComponent implements OnInit {
  
  sortingCriteria : string = '';

  advancedQuery: AdvancedQuery = {
    carLocation: '',
    bodyType: '',
    brand: '',
    fuelType: '',
    model: '',
    transmission: '',
    priceMin: 0,
    priceMax: 0,
    limitInKilometers: 0,
    mileageMin: 0,
    mileageMax: 0,
    numberOfSeatsForChildren: 0,
    protectionAvailable: true,
    from: new Date,
    until: new Date
  };
  loggedIn: boolean = false;
  showAdvancedSearch : boolean = false;
  noAdverts: boolean = false;
  adverts: Advert[];
  dataForSorting : DataForSorting = {
    adverts : this.adverts,
    criteria: this.sortingCriteria
  };
  imageURL: string;
  response: Response;

  brands : Brand[] = [];

  models: Model[] = [];

  fuels: FuelType[] = [];

  transmissions: Transmission[] = [];


  bodyTypes: BodyType[] = [];

  min = Date();
  rentSpan: RentSpan = {
    rentSpan: [new Date()]
  };

  advancedRentSpan: RentSpan = {
    rentSpan: [new Date()]
  };

  numberOfAdverts = [2, 2, 3, 4, 5];

  cities : City[] = [
    {value: 'Novi Sad', viewValue: 'Novi Sad'},
    {value: 'Kraljevo', viewValue: 'Kraljevo'},
    {value: 'Nis', viewValue: 'Nis'},
    {value: 'Beograd', viewValue: 'Beograd'}
  ];

  query: Query = {
    city: '',
    from: new Date(),
    until: new Date()
  };

  constructor(private http: HttpClient, private router: Router) { }

  ngOnInit(): void {
    let status = localStorage.getItem('loggedIn');
    status === 'true' ? this.loggedIn = true : this.loggedIn = false;
    this.fetchAdverts();
    this.fetchBrand();
    this.fetchBodyType();
    this.fetchModels();
    this.fetchFuelType();
    this.fetchTransmission();
  }

  async fetchAdverts() {
    const apiEndpoint = 'http://localhost:8080/search-service/advert';
    this.http.get(apiEndpoint).subscribe(response => {
        this.adverts = response as Array<Advert>;
        if(this.adverts.length === 0) {
          this.noAdverts = true;
        } else {
          this.noAdverts = false;
        }
        this.assignImagesToAdverts(this.adverts);
    }, err => {
      console.log('Unable to fetch adverts: ', err);
    });
  }

  async assignImagesToAdverts(adverts : Array<Advert>) {
    for(let i=0; i<adverts.length; i++) {
      this.getImageURL(this.adverts[i]);
    }
  }

  formatAdvertDate(givenDate: Date) {
    let date = new Date(givenDate);
    return (date.getUTCDate() + "." + (date.getMonth() + 1) + "." + date.getFullYear() + ".");
  }

  async getImageURL(advert: Advert) {
    const apiEndpoint = 'http://localhost:8080/image-service/profile-image/' + advert.uuid;
    this.http.get(apiEndpoint, {responseType: 'text', withCredentials: true}).subscribe(response => {
      advert.imageURL = response as string;
    }, err => {
      console.log('Error fetching image URL', err);
    });
  }

  onSearch() {
    this.query.from = this.rentSpan.rentSpan[0];
    this.query.until = this.rentSpan.rentSpan[1];
    
    const apiEndpoint = 'http://localhost:8080/search-service/filter';
    this.http.post(apiEndpoint, this.query).subscribe(res => {
      this.adverts = res as Array<Advert>;
      this.assignImagesToAdverts(this.adverts);
      if(this.adverts.length === 0) {
        this.noAdverts = true;
      } else {
        this.noAdverts = false;
      }
    }, err => {
      console.log('Error filtering adverts', err);
    });
    
  }

  onLogout() {
    const apiEndpoint = 'http://localhost:8080/auth-service/logout';

    this.http.post(apiEndpoint, '', {responseType: 'json',withCredentials: true}).subscribe(() => {
      this.loggedIn = false;
      localStorage.clear();
    }, err => {
      console.log('Unable to log out', err);
    });
  }

  async fetchBrand() {
    const apiEndpoint = 'http://localhost:8080/admin-service/brand';
    this.http.get(apiEndpoint).subscribe(response => {
      this.brands = response as Array<Brand>;
    }, error => {
      console.log('Unable to fetch Brands');
    });
  }

  async fetchBodyType() {
    const apiEndpoint = 'http://localhost:8080/admin-service/body-type';
    this.http.get(apiEndpoint).subscribe(response => {
      this.bodyTypes = response as Array<BodyType>;
    }, error => {
      console.log('Unable to fetch Body Type');
    });
  }

  async fetchModels() {
    const apiEndpoint = 'http://localhost:8080/admin-service/model';
    this.http.get(apiEndpoint).subscribe(response => {
      this.models = response as Array<Model>;
    }, error => {
      console.log('Unable to fetch Model');
    });
  }

  async fetchFuelType() {
    const apiEndpoint = 'http://localhost:8080/admin-service/fuel-type';
    this.http.get(apiEndpoint).subscribe(response => {
      this.fuels = response as Array<FuelType>;
    }, error => {
      console.log('Unable to fetch Fuel Type');
    });
  }

  async fetchTransmission() {
    const apiEndpoint = 'http://localhost:8080/admin-service/transmission-type';
    this.http.get(apiEndpoint).subscribe(response => {
      this.transmissions = response as Array<Transmission>;
    }, error => {
      console.log('Unable to fetch Transmission');
    });
  }

  validateInput() {
    return this.query.city.length > 0 && this.query.until > this.query.from && this.query.from > new Date();
  }

  routToDetails(advert: Advert) {
    localStorage.setItem('advertUUID', advert.uuid);
    this.router.navigateByUrl("/details");
  }

  async onSwitchSearchState() {
    this.showAdvancedSearch = !this.showAdvancedSearch;
  }

  async onAdvancedSearch() {
    this.advancedQuery.from = this.advancedRentSpan.rentSpan[0];
    this.advancedQuery.until = this.advancedRentSpan.rentSpan[1];

    const apiEndpoint = 'http://localhost:8080/search-service/advanced/filter';
    this.http.post(apiEndpoint, this.advancedQuery).subscribe(res => {
      this.adverts = res as Array<Advert>;
      this.assignImagesToAdverts(this.adverts);
      if(this.adverts.length === 0) {
        this.noAdverts = true;
      } else {
        this.noAdverts = false;
      }
    }, err => {
      console.log('Unable to perform advanced filter');
    });
  }

  async onSort() {
    this.dataForSorting.adverts = this.adverts;
    this.dataForSorting.criteria = this.sortingCriteria;

    const apiEndpoint = 'http://localhost:8080/search-service/sort';
    this.http.post(apiEndpoint, this.dataForSorting).subscribe(res => {
      this.adverts = res as Array<Advert>;
        if(this.adverts.length === 0) {
          this.noAdverts = true;
        } else {
          this.noAdverts = false;
        }
        this.assignImagesToAdverts(this.adverts);

    }, err => {
      console.log(`Filtering cannot be performed due to error ${err}`);
    });
  }

}

export interface Query {
  city: string;
  from: Date;
  until: Date;
};

interface City {
  value: string;
  viewValue: string;
};

interface RentSpan {
  rentSpan: Date[];
};

export interface Advert {
  publisher: string;
  price: number;
  carLocation: string;
  availableForRentFrom: Date;
  availableForRentUntil: Date;
  brand: string;
  model: string;
  fuel: string;
  transmission: string;
  bodyType: string;
  mileage: number;
  isRentLimited: boolean;
  limitInKilometers: number;
  numberOfSeatsForChildren: number;
  uuid: string;
  imageURL: string;
};

interface City {
  value: string;
  viewValue: string;
};

interface FuelType {
  id: number;
  active: boolean;
  fuelType: string;
};

interface Transmission {
  id: number;
  transmissionType: string;
};

interface Brand {
  id: number;
  brandName: string;
  active: boolean;
};

interface Model {
  id: number;
  modelName: string;
  active: boolean;
};

interface BodyType {
  id: number;
  bodyType: string;
  active: boolean;
};

interface AdvancedQuery {
  carLocation: string;
  brand: string;
  model: string;
  transmission: string;
  bodyType: string;
  fuelType: string;
  priceMin: number;
  priceMax: number;
  mileageMin: number;
  mileageMax: number;
  limitInKilometers: number;
  protectionAvailable: boolean;
  numberOfSeatsForChildren: number;
  from: Date;
  until: Date;
}

interface DataForSorting {
  adverts : Advert[];
  criteria : string;
}