package PomEnvironment.Pet;

public class Dog extends Pet {
    public Dog(String name) {
        this.name = name;
    }

    public void speak() {
        System.out.println(getClass() + " " + name + " says, 'Woof! Woof!'");
    }
}
