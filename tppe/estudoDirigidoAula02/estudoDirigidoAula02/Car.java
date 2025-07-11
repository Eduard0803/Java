package estudoDirigidoAula02;

public class Car implements VehicleInterface {
    private String brand;
    private String model;
    private String plate;

    public Car(){}

    public Car(String brand, String model, String plate) {
        this.brand = brand;
        this.model = model;
        this.plate = plate;
    }

    public String getBrand(){return brand;}
    public void setBrand(String brand){this.brand = brand;}
    
    public String getModel(){return model;}
    public void setModel(String model){this.model = model;}

    public String getPlate(){return plate;}
    public void setPlate(String plate){this.plate = plate;}
}
