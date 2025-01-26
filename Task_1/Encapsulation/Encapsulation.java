public class Encapsulation {
    public static void main(String[] args) {
        Person myObj = new Person();
        myObj.setName("Piyush");
        System.out.println(myObj.getName());
    }
}

class Person {
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String newName) {
        this.name = newName;
    }
}
