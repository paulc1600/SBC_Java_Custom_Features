package Pages;

public abstract class Pet {
    String name;

    protected Pet() {
    }

    public String getName() {
        return "<" + name + ">";
    }

    public void setName() {
        this.name = name;
        return;
    }

    public Pet(String name) {
        this.name = name;
    }

    public void walk() {
        System.out.println(getClass() + " " + name + " is walking!");
    }

    public void sleep() {
        System.out.println(getClass() + " Zzzzzzz!");
    }

    public abstract void speak();

    public void eat(String what) {
        System.out.println(getClass() + " " + name + " is eating " + what);
    }
}
