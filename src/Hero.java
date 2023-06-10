package src;
public abstract class Hero {
    protected String name;
    protected double HP;
    protected double maxHP;
    protected double energy;
    protected double maxEnergy;
    protected double physicalAttack;
    protected double physicalAttackDefault;
    protected Item[] items;
    protected int jumlahItem;
    protected double armor;
    protected double lifeSteal;
    protected int level;

    public Hero(String name, double physicalAttack, double maxHP, double maxEnergy) {
        this.name = name;
        this.physicalAttack = physicalAttack;
        this.physicalAttackDefault = physicalAttack;
        this.maxHP = maxHP;
        this.HP = maxHP;
        this.maxEnergy = maxEnergy;
        this.energy = maxEnergy;
        items = new Item[8];
        jumlahItem = 0;
        armor = 0;
        lifeSteal = 0;
        level = 1;
    }

    public void equipItem(Item item){
        if (!isItemExist(item.getName())) {
            items[jumlahItem] = item;
            double persentaseAwal = HP/maxHP;
            this.maxHP += item.getPlusHP();
            this.HP = persentaseAwal*maxHP;
            this.armor += item.getPlusArmor();
            this.physicalAttack += item.getPlusPhysicalAttack();
            this.lifeSteal += item.getPlusLifeSteal();
            jumlahItem++;
            this.energy += item.getPlusEnergy();
            if (energy >= maxEnergy) {
                energy = maxEnergy+1;
            }
            energy--;
            System.out.println(name + " successfully equipped " + item.getName() + "!");
        } else {
            System.out.println("Can't equip same item");
        }
    }

    private boolean isItemExist(String name){
        for (int i = 0; i < jumlahItem; i++) {
            if (name.equals(items[i].getName())) {
                return true;
            }
        }
        return false;
    }

    public void attackEnemy(Hero enemy){
        if (energy > 0) {
            double HpEnow = enemy.HP;
            if (enemy instanceof Assasin) {
                Assasin ass = (Assasin) enemy;
                if (ass.isStealth()) {
                    energy--;
                    System.out.println("The enemy is stealth");
                    ass.setStealth(false);
                    return;
                }
            }
            enemy.getAttacked(this.physicalAttack);
            HP += lifeSteal*(HpEnow-enemy.HP);
            if (HP>maxHP) {
                HP = maxHP;
            }
            energy--;
            System.out.printf("%s hits %s for %.0f damage\n", name, enemy.name, HpEnow-enemy.HP);
            if (lifeSteal != 0) {
                System.out.printf("+%.1f HP\n", lifeSteal*(HpEnow-enemy.HP));
            }
        } else {
            return;
        }
    }

    public void getAttacked(double damage){
        if (armor != 0) {
            damage -= damage*armor;
            this.HP -= damage;
        } else {
            this.HP -= damage;
        }
    }

    public void healing(){
        this.HP += 0.19*this.maxHP;
        System.out.printf("%s's HP +%.1f", name, 0.19*this.maxHP);
        if (HP >= maxHP) {
            HP = maxHP;
            System.out.println(" (HP is full!)");
        } else {
            System.out.println();
        }
        energy--;
    }

    public void status(){
        System.out.printf("%-10s : Lvl = %d [ HP = %.0f/%.0f |", name, level, HP, maxHP);
        printPA();
        System.out.printf("| Energi = %.0f/%.0f | Armor = %.2f | Life Steal = %.2f ]\n", energy, maxEnergy, armor, lifeSteal);
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

    public void printPA(){
        if (physicalAttack == physicalAttackDefault) {
            System.out.printf(" PA = %.0f (default) ", physicalAttack );
        } else {
            System.out.printf(" PA = %2.0f (%2.0f+%2.0f)   ", physicalAttack, physicalAttackDefault, physicalAttack-physicalAttackDefault);
        }
    }

    public void upgradeLevel(){
        level++;
        maxHP *= level;
        maxEnergy *= level;
        physicalAttack -= physicalAttackDefault;
        physicalAttackDefault *= level;
        physicalAttack += physicalAttackDefault;
    }

    public void resetEnergy(){
        energy = maxEnergy;
    }
}
