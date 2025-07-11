package estudoDirigidoAula02;

public class TruckFactory extends VehicleFactory {
    public VehicleInterface newVehicle(){return new Truck();}
}
