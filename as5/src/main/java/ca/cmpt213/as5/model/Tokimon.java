package ca.cmpt213.as5.model;

/**
 * An instance of object or Blueprint of my Tokimon Class
 */
public class Tokimon{
    private int id;
    private String name;
    private double weight;
    private double height;
    private String ability;
    private int strength;
    private String color;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public String getAbility() {
        return ability;
    }

    public void setAbility(String ability) {
        this.ability = ability;
    }

    public int getStrength() {
        return strength;
    }

    @Override
    public String toString() {
        return "Tokimon{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", weight=" + weight +
                ", height=" + height +
                ", ability='" + ability + '\'' +
                ", strength=" + strength +
                ", color='" + color + '\'' +
                '}';
    }

    public void setStrength(int strength) {
        this.strength = strength;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
