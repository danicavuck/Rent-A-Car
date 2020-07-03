import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';

@Component({
  selector: 'app-add-advert',
  templateUrl: './add-advert.component.html',
  styleUrls: ['./add-advert.component.css']
})
export class AddAdvertComponent implements OnInit {

  images = null;

  cities : City[] = [
    {value: 'Novi Sad', viewValue: 'Novi Sad'},
    {value: 'Kraljevo', viewValue: 'Kraljevo'},
    {value: 'Nis', viewValue: 'Nis'},
    {value: 'Beograd', viewValue: 'Beograd'}
  ];


  brands : Brand[] = [];

  models: Model[] = [];

  fuels: FuelType[] = [];

  transmissions: Transmission[] = [];


  bodyTypes: BodyType[] = [];

  rentSpan: RentSpan = {
    rentSpan: [new Date()]
  };

  data: Advert = {
    description: '',
    price: 0,
    username: '',
    carLocation: '',
    dateStart: new Date(),
    dateEnd: new Date(),
    brand: '',
    model: '',
    fuel: '',
    transmission: '',
    bodyType: '',
    mileage: 0,
    isRentLimited: false,
    limitInKilometers: 0,
    numberOfSeatsForChildren: 0
  };

  constructor(private http: HttpClient, private router: Router) { 
    this.fetchBrand();
    this.fetchBodyType();
    this.fetchModels();
    this.fetchFuelType();
    this.fetchTransmission();
  }

  ngOnInit(): void {
    
  }

  onSubmit() {
    const apiEndpoint = 'http://localhost:8080/posting-service/advert';

    this.data.dateStart = this.rentSpan.rentSpan[0];
    this.data.dateEnd = this.rentSpan.rentSpan[1];
    this.data.username = localStorage.getItem('username');
    this.http.post(apiEndpoint, this.data, {responseType: 'text', withCredentials: true}).subscribe(response => {
      this.saveImages(response);
    }, err => {
      console.log('Error', err);
    });
  }

  saveImages(uuid: string) {
    let body = new FormData();
    body.append('images', this.images);

    const apiEndpoint = 'http://localhost:8080/image-service/profile-image/' + uuid;
    this.http.post(apiEndpoint, body, {responseType: 'text', withCredentials: true}).subscribe(() => {
      console.log('Image posted');
    }, err => {
      console.log('Error occurred: ', err);
    });
    
  }

  onFileSelected(event) {
    this.images = event.target.files[0];
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

}

export interface Advert {
  description: string;
  price: number;
  username: string;
  carLocation: string;
  dateStart: Date;
  dateEnd: Date;
  brand: string;
  model: string;
  fuel: string;
  transmission: string;
  bodyType: string;
  mileage: number;
  isRentLimited: boolean;
  limitInKilometers: number;
  numberOfSeatsForChildren: number;
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

interface RentSpan {
  rentSpan: Date[];
};