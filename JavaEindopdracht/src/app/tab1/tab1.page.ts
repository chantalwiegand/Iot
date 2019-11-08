import { Component } from '@angular/core';
import { IotServiceService } from '../iot-service.service';

@Component({
  selector: 'app-tab1',
  templateUrl: 'tab1.page.html',
  styleUrls: ['tab1.page.scss']
})
export class Tab1Page {

  constructor(private iotService: IotServiceService) {}

}
