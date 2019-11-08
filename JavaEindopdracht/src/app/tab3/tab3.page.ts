import { Component } from '@angular/core';
import { IotServiceService } from '../iot-service.service';

@Component({
  selector: 'app-tab3',
  templateUrl: 'tab3.page.html',
  styleUrls: ['tab3.page.scss']
})
export class Tab3Page {

  constructor(private iotService: IotServiceService) {}

}
