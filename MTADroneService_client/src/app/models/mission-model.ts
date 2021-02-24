import {DroneModel} from 'src/app/models/drone-model'
export class MissionModel{
    missionID:string="";
    missionDrone:DroneModel=new DroneModel("","","");
    missionType:string="";
    missionLongitude:string="";
    missionLatitude:string="";
    missionDate:string="";
    missionStatus:string="";
}