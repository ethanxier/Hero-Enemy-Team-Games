package src;
public class Tank extends Hero{
    private double firstArmor;

    public Tank(String name, double PhysicalAttack, double maxHP, double maxEnergy) {
        super(name, PhysicalAttack, maxHP, maxEnergy);
        this.firstArmor = 0.2;
    }

    @Override
    public void getAttacked(double damage) {
        damage -= damage*firstArmor;
        if (armor != 0) {
            damage -= damage*armor;
            this.HP -= damage;
        } else {
            this.HP -= damage;
        }
    }

    @Override
    public void status() {
        System.out.printf("%-10s : Lvl = %d [ HP = %.0f/%.0f |", name, level, HP, maxHP);
        printPA();
        System.out.printf("| Energi = %.0f/%.0f | Armor = %.2f | Life Steal = %.2f ]\n", energy, maxEnergy, armor+firstArmor, lifeSteal);
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
