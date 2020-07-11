import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-managing-entities',
  templateUrl: './managing-entities.component.html',
  styleUrls: ['./managing-entities.component.css']
})
export class ManagingEntitiesComponent implements OnInit {

  public brandColumns: string[] = ['brand', 'delete'];
  public modelColumns: string[] = ['model', 'delete'];
  public transmissionColumns: string[] = ['transmission', 'delete'];
  public fuelColumns: string[] = ['fuel', 'delete'];
  public bodyColumns: string[] = ['body', 'delete'];

  carBrand : string = '';
  carBrandDTO : Brand = {
    active: true,
    brandName: '',
    id: 0
  };
  carModel : string = '';
  carModelDTO : Model = {
    active: true,
    modelName: '',
    newModelName: '',
    id: 0
  };

  transmissionType : string = '';
  transmissionTypeDTO : Transmission = {
    id: 0,
    transmissionType: ''
  };

  bodyType : string = '';
  bodyTypeDTO : BodyType = {
    newBodyType: '',
    bodyType: '',
    id: 0
  };

  fuelType : string = '';
  fuelTypeDTO : FuelType = {
    active : true,
    fuelType: '',
    id: 0
  };

  brands : Array<Brand>;
  bodyTypes : Array<BodyType>;
  models : Array<Model>;
  fuels : Array<FuelType>;
  transmissions : Array<Transmission>;

  constructor(private http: HttpClient) {
    this.fetchBrand();
    this.fetchBodyType();
    this.fetchModels();
    this.fetchFuelType();
    this.fetchTransmission();
  }

  ngOnInit(): void {
  }

  async onSubmitBrand() {
    const apiEndpoint = 'http://localhost:8080/admin-service/brand';
    this.carBrandDTO.brandName = this.carBrand;
    this.http.post(apiEndpoint, this.carBrandDTO, {responseType: 'text'}).subscribe(() => {
      this.carBrand = '';
      this.fetchBrand();
    }, err => {
      console.log(`Could not add car brand due to error ${err}`);
    });
  }

  async onSubmitBodyType(){
    const apiEndpoint = 'http://localhost:8080/admin-service/body-type';
    this.bodyTypeDTO.bodyType = this.bodyType;
    this.http.post(apiEndpoint, this.bodyTypeDTO, {responseType: 'text'}).subscribe(() => {
      this.bodyType = '';
      this.fetchBodyType();
    }, err => {
      console.log(`Couldn't add new body type due to error ${err}`);
    });
  }

  async onSubmitModel() {
    const apiEndpoint = 'http://localhost:8080/admin-service/model';
    this.carModelDTO.modelName = this.carModel;
    this.http.post(apiEndpoint, this.carModelDTO, {responseType: 'text'}).subscribe(() => {
      this.carModel = '';
      this.fetchModels();
    }, err => {
      console.log(`Unable to post model due to eror ${err}`);
    });
  }

  async onSubmitFuelType() {
    const apiEndpoint = 'http://localhost:8080/admin-service/fuel-type';
    this.fuelTypeDTO.fuelType = this.fuelType;
    this.http.post(apiEndpoint, this.fuelTypeDTO, {responseType: 'text'}).subscribe(() => {
      this.fuelType = '';
      this.fetchFuelType();
    }, err => {
      console.log(`Unable to post fuel type due to error ${err}`);
    });
  }

  async onSubmitTransmissionType() {
    const apiEndpoint = 'http://localhost:8080/admin-service/transmission-type';
    this.transmissionTypeDTO.transmissionType = this.transmissionType;
    this.http.post(apiEndpoint, this.transmissionTypeDTO, {responseType: 'text'}).subscribe(() => {
      this.transmissionType = '';
      this.fetchTransmission();
    }, err => {
      console.log(`Unable to fetch transmission`);
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

  async onDeleteBrand(brand : Brand) {
    const apiEndpoint = 'http://localhost:8080/admin-service/brand/delete';
    this.http.post(apiEndpoint, brand, {responseType: 'text'}).subscribe(() => {
      this.fetchBrand();
    }, err => {
      console.log('Unable to delete Brand');
    });
  }

  async onDeleteModel(model : Model) {
    const apiEndpoint = 'http://localhost:8080/admin-service/model/delete';
    this.http.post(apiEndpoint, model, {responseType: 'text'}).subscribe(() => {
      this.fetchModels();
    }, err => {
      console.log('Unable to delete Model');
    });
  }

  async onDeleteTransmission(transmission : Transmission) {
    const apiEndpoint = 'http://localhost:8080/admin-service/transmission-type/delete';
    this.http.post(apiEndpoint, transmission, {responseType: 'text'}).subscribe(() => {
      this.fetchTransmission();
    }, err => {
      console.log('Unable to delete Transmission');
    });
  }

  async onDeleteFuel(fuel : FuelType) {
    const apiEndpoint = 'http://localhost:8080/admin-service/fuel-type/delete';
    this.http.post(apiEndpoint, fuel, {responseType: 'text'}).subscribe(() => {
      this.fetchFuelType();
    }, err => {
      console.log('Unable to delete Fuel');
    });
  }

  async onDeleteBody(body : BodyType) {
    const apiEndpoint = 'http://localhost:8080/admin-service/body-type/delete';
    this.http.post(apiEndpoint, body, {responseType: 'text'}).subscribe(() => {
      this.fetchBodyType();
    }, err => {
      console.log('Unable to delete Body Type');
    });
  }
}

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
  newModelName: string;
  active: boolean;
};

interface BodyType {
  id: number;
  bodyType: string;
  newBodyType: string;
};