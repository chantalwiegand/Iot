import { Injectable } from '@angular/core';
import { Http } from '@angular/http';


@Injectable({
  providedIn: 'root'
})
export class IotServiceService {

  constructor(private http: Http) {
    this.getDhtData();
    this.getCpuData();
    this.getAllCpuData();
    this.getAllDhtData();
    this.ledOn();
  }

  BASE_URL = "http://10.9.10.158:7000/rest/devices"

  data = [];
  data2 = [];
  dhtData = [];
  cpuData = [];
  allCpuData = [];
  allDhtData = [];


  async getDhtData() {
    var dataResponse = await this.http.get(this.BASE_URL + '/dht').toPromise();
    this.dhtData.push(dataResponse.json());
  }

  async getCpuData() {
    var dataResponse = await this.http.get(this.BASE_URL + '/cpu').toPromise();
    this.cpuData.push(dataResponse.json());
  }

  async getAllCpuData() {
    var dataResponse = await this.http.get(this.BASE_URL + '/cpu/all').toPromise();
    this.allCpuData.push(dataResponse.json());
    this.data = this.allCpuData[0];
  }

  async getAllDhtData() {
    var dataResponse = await this.http.get(this.BASE_URL + '/dht/all').toPromise();
    this.allDhtData.push(dataResponse.json());
    this.data2 = this.allDhtData[0];
  }

  async ledOn() {
    this.http.get(this.BASE_URL + '/led/on', {}).toPromise();
  }

  async ledOff() {
    this.http.get(this.BASE_URL + '/led/off', {}).toPromise();
  }

  async ledBlink() {
    this.http.get(this.BASE_URL + '/led/blink', {}).toPromise();
  }

  async refreshCPU() {
    this.http.get(this.BASE_URL + '/cpu', {}).toPromise();
    window.location.reload();
  }

  async refreshDHT() {
    this.http.get(this.BASE_URL + '/dht', {}).toPromise();
    window.location.reload();
  }

  async refreshHome() {
    this.http.get(this.BASE_URL + '/cpu', {}).toPromise();
    this.http.get(this.BASE_URL + '/dht', {}).toPromise();
    window.location.reload();
  }

  


}
