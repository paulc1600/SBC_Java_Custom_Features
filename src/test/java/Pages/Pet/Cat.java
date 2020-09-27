package Pages;

public class Cat extends Pet {
    public Cat(String name) {
        this.name = name;
    }

    public void speak() {
        System.out.println(getClass() + " " + name + " says, 'Meow!'");
    }
}
