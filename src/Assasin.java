package src;
public class Assasin extends Hero{
    private boolean stealth;

    public boolean isStealth() {
        return stealth;
    }

    public Assasin(String name, double physicalAttack, double maxHP, double maxEnergy) {
        super(name, physicalAttack, maxHP, maxEnergy);
        this.stealth = false;
    }

    public void setStealth(boolean bool){
        if (!isStealth() && bool == true) {
            stealth = bool;
            System.out.println(name + " stealth activated!");
            energy--;
        } else if (isStealth() && bool == true){
            System.out.println("stealth is already active, can't activate");
        } else {
            stealth = bool;
        }
    }

    @Override
    public void status() {
        System.out.printf("%-10s : Lvl = %d [ HP = %.0f/%.0f |", name, level, HP, maxHP);
        super.printPA();
        System.out.printf("| Energi = %.0f/%.0f | Armor = %.2f | Life Steal = %.2f ]", energy, maxEnergy, armor, lifeSteal);
        if (stealth) {
            System.out.println(" O");
        } else {
            System.out.println(" X");
        }
        System.out.printf("%22s -> ", "equipment");
        if (jumlahItem == 0) {
            System.out.println("-");
        } else {
            for (int i = 0; i < jumlahItem; i++) {
                if (i == 0) {
                    System.out.print(" " + items[i].getName());
                } else {
                    System.out.print(", " + items[i].getName());
                }
            }
            System.out.print("\n");
        }
    }
}
