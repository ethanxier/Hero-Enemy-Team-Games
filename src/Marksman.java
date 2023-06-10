package src;
public class Marksman extends Hero{
    private double plusPhysicalAttack;

    public Marksman(String name, double PhysicalAttack, double maxHP, double maxEnergy) {
        super(name, PhysicalAttack, maxHP, maxEnergy);
        plusPhysicalAttack = 0.2*physicalAttackDefault;
    }

    @Override
    public void attackEnemy(Hero enemy) {
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
            enemy.getAttacked(this.physicalAttack+this.plusPhysicalAttack);
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
}
