package src;
public class Item {
    private String name;
    private double plusPhysicalAttack;
    private double plusArmor;
    private double plusHP;
    private double plusEnergy;
    private double plusLifeSteal;

    public Item(String name, double plusPhysicalAttack, double plusArmor, double plusHP, double plusEnergy,
            double plusLifeSteal) {
        this.name = name;
        this.plusPhysicalAttack = plusPhysicalAttack;
        this.plusArmor = plusArmor;
        this.plusHP = plusHP;
        this.plusEnergy = plusEnergy;
        this.plusLifeSteal = plusLifeSteal;
    }

    public String getName() {
        return name;
    }

    public double getPlusPhysicalAttack() {
        return plusPhysicalAttack;
    }

    public double getPlusArmor() {
        return plusArmor;
    }

    public double getPlusHP() {
        return plusHP;
    }

    public double getPlusEnergy() {
        return plusEnergy;
    }

    public double getPlusLifeSteal() {
        return plusLifeSteal;
    }
}
