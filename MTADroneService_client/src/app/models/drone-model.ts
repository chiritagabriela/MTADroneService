import { DroneCoordinatesModel } from "./drone-coord-model";

export class DroneModel{
    droneID:string="";
    droneModel:string="";
    droneStatus:string="";
    currentDroneCoordinates:DroneCoordinatesModel;

    constructor(private id:string, private model:string, private status:string, private coordinates:DroneCoordinatesModel){
        this.droneID = id;
        this.droneModel = model;
        this.status = status;
        this.currentDroneCoordinates = coordinates;
    }

    getDroneCoordinates(){
        return this.getDroneCoordinates;
    }
    
}