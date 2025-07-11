package estudoDirigidoAula02;

public class Main {
    public static void main(String[] args){
        VehicleFactory factory;

        factory = new CarFactory();
        VehicleInterface carro = factory.newVehicle();
        carro.setBrand("Fiat");
        carro.setModel("Uno");
        carro.setPlate("ABC-1234");
        
        System.out.println("Carro: " + carro.getBrand() + " " + carro.getModel() + " " + carro.getPlate());
    }
    
}
