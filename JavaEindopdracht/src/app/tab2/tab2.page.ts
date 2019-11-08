import { Component } from '@angular/core';
import { IotServiceService } from '../iot-service.service';
import { Chart } from 'chart.js';
import { GoogleChartsModule } from 'angular-google-charts';


@Component({
  selector: 'app-tab2',
  templateUrl: 'tab2.page.html',
  styleUrls: ['tab2.page.scss']
})
export class Tab2Page {

  constructor(private iotService: IotServiceService) { }



  ngOnInit() {
  }
  
}

