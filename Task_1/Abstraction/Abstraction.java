 abstract class Vehicles {
    abstract void start();
    abstract void stop();
    void fuelUp() {
        System.out.println("Fueling the vehicle...");
    }
}

class Cars extends Vehicles {
    void start() {
        System.out.println("Car is starting.");
    }
    void stop() {
        System.out.println("Car is stopping.");
    }
}

class Bike extends Vehicles {
    void start() {
        System.out.println("Bike is starting.");
    }
    void stop() {
        System.out.println("Bike is stopping.");
    }
}

public class Abstraction {
    public static void main(String[] args) {
        Vehicles car = new Cars();
        Vehicles bike = new Bike();

        car.fuelUp();
        car.start();
        car.stop();

        bike.fuelUp();
        bike.start();
        bike.stop();
    }
}
