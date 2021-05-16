import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { map } from 'rxjs/operators';
import { CookieService } from 'ngx-cookie-service';
import { Observable, of, empty, BehaviorSubject } from 'rxjs';
import { MissionModel } from '../models/mission-model';
import { DroneModel } from '../models/drone-model';

@Injectable({
  providedIn: 'root'
})
export class DronemissionService {

  constructor(private httpClient:HttpClient) { }

  startSearchMission(formValue:any): Observable<MissionModel>{
    const url = `https://romtadroneservice.mooo.com/services/search`;
    return this.httpClient.post<MissionModel>(url, formValue)
    .pipe(map(missionModel =>{
      return missionModel;
    })
    );
  }

  startSurveilMission(formValue:any): Observable<MissionModel>{
    const url = `https://romtadroneservice.mooo.com/services/surveil`;
    return this.httpClient.post<MissionModel>(url, formValue)
    .pipe(map(missionModel =>{
      return missionModel;
    })
    );
  }

  startDeliveryMission(formValue:any): Observable<MissionModel>{
    const url = `https://romtadroneservice.mooo.com/services/delivery`;
    return this.httpClient.post<MissionModel>(url, formValue)
    .pipe(map(missionModel =>{
      return missionModel;
    })
    );
  }

  getCurrentPosition(droneId:String): Observable<DroneModel>{
    const url = `https://romtadroneservice.mooo.com/drone/get_current_position/` + droneId;
    return this.httpClient.get<DroneModel>(url);
  }

  getAllMissions(): Observable<MissionModel>{
    const url = `https://romtadroneservice.mooo.com/missions/all_missions`;
    return this.httpClient.get<MissionModel>(url);
  }

  getCurrentMission():Observable<MissionModel>{
    const url = `https://romtadroneservice.mooo.com/missions/current_mission/`;
    return this.httpClient.get<MissionModel>(url);
  }

  downloadImage(imageName:string) : Observable<any> { 
    const url = `https://romtadroneservice.mooo.com/communication/get_image/` + imageName;
    return this.httpClient.get(url)
  } 
}