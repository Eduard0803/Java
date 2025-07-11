package estudoDirigidoAula02;

public class CarFactory extends VehicleFactory {
    public VehicleInterface newVehicle(){return new Car();}
}
