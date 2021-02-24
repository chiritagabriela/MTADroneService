export class DroneModel{
    droneID:string="";
    droneModel:string="";
    droneStatus:string="";
    constructor(private id:string, private model:string, private status:string)
    {
        this.droneID = id;
        this.droneModel = model;
        this.status = status;
    }
    
}