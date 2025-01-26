public class Inheritance {
    public static void main(String[] args) {
        Car myCar = new Car();
        myCar.honk();
        System.out.println(myCar.brand);
    }
}
class Vehicle {
    protected String brand = "Hyundai";
    public void honk() {                    // Vehicle method
        System.out.println("Burr, Burr!");
    }
}

class Car extends Vehicle {
    private String modelName = "Creta";
}