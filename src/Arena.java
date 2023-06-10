package src;
import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;

public class Arena {
    private Hero[] heroes;
    private Item[] items;
    private Hero[] team1;
    private Hero[] team2;
    private int round;

    public Arena(Hero[] heroes, Item[] items){
        this.heroes = heroes;
        this.items = items;
        round = 1;
    }

    public void start() throws InterruptedException{
        System.out.println("==========================================================");
        System.out.println("                   Welcome to the Arena                   ");
        System.out.println("==========================================================");
        while (true) {
            Scanner inp = new Scanner(System.in);
            System.out.println("Please select a game mode:");
            System.out.println("  1. Single player against computer");
            System.out.println("  2. Multiplayer mode");
            System.out.print("select: ");
            int pilihan = 0;
            try {
                pilihan = inp.nextInt();
                switch(pilihan){
                    case 1:
                    System.out.println();
                    startWithComputer();
                    return;
                    case 2:
                    System.out.println();
                    startMultiplayer();
                    return;
                    default:
                    System.out.println("Invalid choice!");
                }
            } catch (InputMismatchException e) {
                System.out.println("enter the input correctly!!\n");
                continue;
            }
        }
    }

    private void startWithComputer() throws InterruptedException{
        Scanner inp = new Scanner(System.in);
        System.out.println("           You have chosen single player mode             ");
        System.out.println("==========================================================");
        int jumlahHero1, jumlahHero2, pilihan;
        Hero[] you, bot;
        
        System.out.print("How many heroes do you want to select? ");
        jumlahHero1=inp.nextInt();
        team1 = new Hero[jumlahHero1];

        System.out.println("Please select your hero:");
        for(int j=0; j<heroes.length; j++){
            System.out.printf("%d. %-10s", (j+1), heroes[j].name);
            if (heroes[j] instanceof Tank) {
                System.out.printf("%45s", "T");
            } else if (heroes[j] instanceof Marksman) {
                System.out.printf("%45s", "MM");
            } else if (heroes[j] instanceof Assasin) {
                System.out.printf("%45s", "A");
            }
            System.out.println();
        }

        for (int i = 0; i < jumlahHero1; i++) {
            System.out.print("select: ");
            pilihan = inp.nextInt();
            if(pilihan<1 || pilihan>heroes.length){
                System.out.println("Invalid choice!, select again");
                i--;
                continue;
            }
            if (!isHeroExist(heroes[pilihan-1].name, team1)) {
                team1[i] = pickHero(heroes[pilihan-1]);
            } else {
                System.out.println("Can't dupplicate hero, select again");
                i--;
                continue;
            }
        }

        System.out.print("You have selected");
        for (int i = 0; i < jumlahHero1; i++) {
            for (int j = 0; j < 2; j++) {
                Thread.sleep(100);
            }
            if (i == 0) {
                System.out.print(" " + team1[i].name);
            } else {
                System.out.print(", " + team1[i].name);
            }
        }

        for (int i = 0; i < 5; i++) {
            System.out.print(".");
            Thread.sleep(200);
        }
        
        System.out.println("\n\n>>> Computer turn");
        System.out.println("\nHow many heroes bot do you want to select?");
        jumlahHero2=inp.nextInt();
        team2 = new Hero[jumlahHero2];

        Random random = new Random();

        for (int i = 0; i < jumlahHero2; i++) {
            int randomChoice = random.nextInt(heroes.length);
            if (!isHeroExist(heroes[randomChoice].name, team2)) {
                team2[i] = pickHero(heroes[randomChoice]);
            } else {
                i--;
                continue;
            }
        }

        for (int i = 0; i < 5; i++) {
            System.out.print(".");
            Thread.sleep(200);
        }
        System.out.print("\nThe computer has selected: ");
        for (int i = 0; i < jumlahHero2; i++) {
            for (int j = 0; j < 2; j++) {
                Thread.sleep(100);
            }
            if (i == 0) {
                System.out.print(team2[i].name);
            } else {
                System.out.print(", " + team2[i].name);
            }
        }
        System.out.println();

        you = team1.clone();
        bot = team2.clone();

        battleStartWithComputer(you, bot);
    }

    private void battleStartWithComputer(Hero[] you, Hero[] bot) throws InterruptedException{
        System.out.println("\n==============================================================================================================");
        System.out.println("                                            <<< ROUND " + round + " >>>");
        System.out.println("==============================================================================================================");

        team1Turn(you, bot);
        computerTurn(you, bot);
        round++;

        int jumlahHero1 = sisaHero(you);
        int jumlahHero2 = sisaHero(bot);

        if (jumlahHero1 > 0 && jumlahHero2 > 0) {
            for (int i = 0; i < you.length; i++) {
                if (you[i] != null) {
                    you[i].resetEnergy();
                }
            }
            for (int i = 0; i < bot.length; i++) {
                if (bot[i] != null) {
                    bot[i].resetEnergy();
                }
            }

            battleStartWithComputer(you, bot);
        } else if (jumlahHero1 > 0 && jumlahHero2 <= 0) {
            System.out.println("\n==============================================================================================================");
            System.out.println("                                          <<<< ! YOU WIN ! >>>>");
            System.out.println("=============================================================================================================="); 
        } else if (jumlahHero1 <= 0 && jumlahHero2 > 0) {
            System.out.println("\n==============================================================================================================");
            System.out.println("                                          <<<< ! YOU LOSE ! >>>>");
            System.out.println("==============================================================================================================");
        }
    }

    private void startMultiplayer() throws InterruptedException{
        Scanner inp = new Scanner(System.in);
        System.out.println("         You have chosen multiplayer player mode          ");
        System.out.println("==========================================================");
        int jumlahHero1, jumlahHero2, pilihan;
        Hero[] heroes1, heroes2;
        
        System.out.println("\n>>> TEAM 1 Turn Choice\n");
        System.out.print("How many heroes do you want to select? ");
        jumlahHero1=inp.nextInt();
        team1 = new Hero[jumlahHero1];

        System.out.println("Please select your hero:");
        for(int j=0; j<heroes.length; j++){
            System.out.printf("%d. %-10s", (j+1), heroes[j].name);
            if (heroes[j] instanceof Tank) {
                System.out.printf("%45s", "T");
            } else if (heroes[j] instanceof Marksman) {
                System.out.printf("%45s", "MM");
            } else if (heroes[j] instanceof Assasin) {
                System.out.printf("%45s", "A");
            }
            System.out.println();
        }

        for (int i = 0; i < jumlahHero1; i++) {
            System.out.print("select: ");
            pilihan = inp.nextInt();
            if(pilihan<1 || pilihan>heroes.length){
                System.out.println("Invalid choice!, select again");
                i--;
                continue;
            }
            if (!isHeroExist(heroes[pilihan-1].name, team1)) {
                team1[i] = pickHero(heroes[pilihan-1]);
            } else {
                System.out.println("Can't dupplicate hero, select again");
                i--;
                continue;
            }
        }

        System.out.print("You have selected");
        for (int i = 0; i < jumlahHero1; i++) {
            for (int j = 0; j < 2; j++) {
                Thread.sleep(100);
            }
            if (i == 0) {
                System.out.print(" " + team1[i].name);
            } else {
                System.out.print(", " + team1[i].name);
            }
        }

        for (int i = 0; i < 5; i++) {
            System.out.print(".");
            Thread.sleep(200);
        }
        System.out.println();

        System.out.println("\n>>> TEAM 2 Turn Choice\n");
        System.out.print("How many heroes do you want to select? ");
        jumlahHero2=inp.nextInt();
        team2 = new Hero[jumlahHero2];

        System.out.println("Please select your hero:");
        for(int j=0; j<heroes.length; j++){
            System.out.printf("%d. %-10s", (j+1), heroes[j].name);
            if (heroes[j] instanceof Tank) {
                System.out.printf("%45s", "T");
            } else if (heroes[j] instanceof Marksman) {
                System.out.printf("%45s", "MM");
            } else if (heroes[j] instanceof Assasin) {
                System.out.printf("%45s", "A");
            }
            System.out.println();
        }

        for (int i = 0; i < jumlahHero2; i++) {
            System.out.print("select: ");
            pilihan = inp.nextInt();
            if(pilihan<1 || pilihan>heroes.length){
                System.out.println("Invalid choice!, select again");
                i--;
                continue;
            }
            if (!isHeroExist(heroes[pilihan-1].name, team2)) {
                team2[i] = pickHero(heroes[pilihan-1]);
            } else {
                System.out.println("Can't dupplicate hero, select again");
                i--;
                continue;
            }
        }

        System.out.print("You have selected");
        for (int i = 0; i < jumlahHero2; i++) {
            for (int j = 0; j < 2; j++) {
                Thread.sleep(100);
            }
            if (i == 0) {
                System.out.print(" " + team2[i].name);
            } else {
                System.out.print(", " + team2[i].name);
            }
        }

        for (int i = 0; i < 5; i++) {
            System.out.print(".");
            Thread.sleep(200);
        }
        System.out.println();

        heroes1 = team1.clone();
        heroes2 = team2.clone();

        battleStartMultiPlayer(heroes1, heroes2);
    }

    private void battleStartMultiPlayer(Hero[] team1, Hero[] team2) throws InterruptedException{
        System.out.println("\n==============================================================================================================");
        System.out.println("                                            <<< ROUND " + round + " >>>");
        System.out.println("==============================================================================================================");

        team1Turn(team1, team2);
        team2Turn(team1, team2);
        round++;

        int jumlahHero1 = sisaHero(team1);
        int jumlahHero2 = sisaHero(team2);

        if (jumlahHero1 > 0 && jumlahHero2 > 0) {
            for (int i = 0; i < team1.length; i++) {
                if (team1[i] != null) {
                    team1[i].resetEnergy();
                }
            }
            for (int i = 0; i < team2.length; i++) {
                if (team2[i] != null) {
                    team2[i].resetEnergy();
                }
            }

            battleStartWithComputer(team1, team2);
        } else if (jumlahHero1 > 0 && jumlahHero2 <= 0) {
            System.out.println("\n==============================================================================================================");
            System.out.println("                                          <<<< ! TEAM 1 WIN ! >>>>");
            System.out.println("=============================================================================================================="); 
        } else if (jumlahHero1 <= 0 && jumlahHero2 > 0) {
            System.out.println("\n==============================================================================================================");
            System.out.println("                                          <<<< ! TEAM 2 WIN ! >>>>");
            System.out.println("=============================================================================================================="); 
        }
    }

    private boolean isHeroExist(String name, Hero[] heroes){
        for (int i = 0; i < heroes.length; i++) {
            if (heroes[i] != null) {
                if (name.toLowerCase().equals(heroes[i].name.toLowerCase())) {
                    return true;
                }
            }
        }
        return false;
    }

    private Hero putHero(String name, Hero[] heroes){
        for (int i = 0; i < heroes.length; i++) {
            if (heroes[i] != null) {
                if (heroes[i].name.toLowerCase().equals(name.toLowerCase())) {
                    return heroes[i]; 
                }
            }
        }
        return null;
    }

    private void printAllPlayers(Hero[] team1, Hero[] team2) throws InterruptedException{
        for (int i = 0; i < 2; i++) {
            Thread.sleep(200);
        }
        System.out.println("TEAM 1");
        System.out.println("==============================================================================================================");
        for (int i = 0; i < team1.length; i++) {
            if (team1[i] != null) {
                team1[i].status();
            }
        }
        for (int i = 0; i < 2; i++) {
            Thread.sleep(200);
        }

        System.out.println("\nTEAM 2");
        System.out.println("==============================================================================================================");
        for (int i = 0; i < team2.length; i++) {
            if (team2[i] != null) {
                team2[i].status();
            }
        }
        for (int i = 0; i < 2; i++) {
            Thread.sleep(200);
        }
        System.out.println();
    }

    private void team1Turn(Hero[] team1, Hero[] team2) throws InterruptedException{
        if (sisaHero(team1) == 0 || sisaHero(team2) == 0) {
            return;
        }
        String pilihan;
        for (int i = 0; i < team1.length; i++) {
            if (team1[i] != null) {
                while (team1[i].energy > 0) {
                    System.out.println("\n>>> 1st TEAM TURN\n");
                    Scanner inp = new Scanner(System.in);
                    printAllPlayers(team1, team2);
                    System.out.println("Select action for " + team1[i].name);
                    System.out.println("- Attack <enemy name>");
                    System.out.println("- Recover");
                    System.out.println("- Equip");
                    if (team1[i] instanceof Assasin) {
                        System.out.println("- Stealth");
                    }
                    System.out.println("- End");
                    System.out.print("Select: ");
                    pilihan = inp.nextLine();
    
                    if (pilihan.toLowerCase().split(" ")[0].equals("attack")) {
                        if (isHeroExist(pilihan.split(" ")[1], team2)) {
                            Hero enemyChoice = putHero(pilihan.split(" ")[1], team2);
                            team1[i].attackEnemy(enemyChoice);
                            if (enemyChoice.HP <= 0) {
                                System.out.printf("\n>>> %s has been killed by %s\n", enemyChoice.name, team1[i].name);
                                team1[i].upgradeLevel();
                                deleteHero(enemyChoice, team2);
                            }
                            if (sisaHero(team2) == 0) {
                                return;
                            }
                        } else {
                            System.out.println("Wrong target!");
                        }
                    } else if (pilihan.toLowerCase().equals("recover")) {
                        team1[i].healing();
                    } else if (pilihan.toLowerCase().equals("equip")) {
                        System.out.println("\nPlease select your item:");
                        int pilihanItem = 0;
                        printItems();
                        while (true){
                            System.out.print("Select: ");
                            try {
                                pilihanItem = inp.nextInt();
                                if (pilihanItem > 0 && pilihanItem <= items.length) {
                                    break;
                                } else {
                                    System.out.println("Can't take item, select again");
                                }
                            } catch (Exception e) {
                                System.out.println("Can't take item, select again");
                                inp.next();
                            }
                        }
                        team1[i].equipItem(items[pilihanItem-1]);
                    } else if (team1[i] instanceof Assasin && pilihan.toLowerCase().equals("stealth")) {
                        Assasin hero = (Assasin) team1[i];
                        if (!hero.isStealth()) {
                            hero.setStealth(true);
                        } else {
                            System.out.println("stealth is already active, can't activate");
                        }
                    } else if (pilihan.toLowerCase().equals("end")){
                        team1[i].energy--;
                        continue;
                    } else {
                        System.out.println("enter the input correctly!!");
                    }
                }
            }
        }
        System.out.println();
    }

    private void team2Turn(Hero[] team1, Hero[] team2) throws InterruptedException{
        if (sisaHero(team1) == 0 || sisaHero(team2) == 0) {
            return;
        }
        String pilihan;
        for (int i = 0; i < team2.length; i++) {
            if (team2[i] != null) {
                while (team2[i].energy > 0) {
                    System.out.println("\n>>> 2nd TEAM TURN\n");
                    Scanner inp = new Scanner(System.in);
                    printAllPlayers(team1, team2);
                    System.out.println("Select action for " + team2[i].name);
                    System.out.println("- Attack <enemy name>");
                    System.out.println("- Recover");
                    System.out.println("- Equip");
                    if (team2[i] instanceof Assasin) {
                        System.out.println("- Stealth");
                    }
                    System.out.println("- End");
                    System.out.print("Select: ");
                    pilihan = inp.nextLine();
    
                    if (pilihan.toLowerCase().split(" ")[0].equals("attack")) {
                        if (isHeroExist(pilihan.split(" ")[1], team1)) {
                            Hero enemyChoice = putHero(pilihan.split(" ")[1], team1);
                            team2[i].attackEnemy(enemyChoice);
                            if (enemyChoice.HP <= 0) {
                                System.out.printf("\n>>> %s has been killed by %s\n", enemyChoice.name, team2[i].name);
                                team2[i].upgradeLevel();
                                deleteHero(enemyChoice, team1);
                            }
                            if (sisaHero(team1) == 0) {
                                return;
                            }
                        } else {
                            System.out.println("Wrong target!");
                        }
                    } else if (pilihan.toLowerCase().equals("recover")) {
                        team2[i].healing();
                    } else if (pilihan.toLowerCase().equals("equip")) {
                        System.out.println("\nPlease select your item:");
                        int pilihanItem = 0;
                        printItems();
                        while (true){
                            System.out.print("Select: ");
                            try {
                                pilihanItem = inp.nextInt();
                                if (pilihanItem > 0 && pilihanItem <= items.length) {
                                    break;
                                } else {
                                    System.out.println("Can't take item, select again");
                                }
                            } catch (Exception e) {
                                System.out.println("Can't take item, select again");
                                inp.next();
                            }
                        }
                        team2[i].equipItem(items[pilihanItem-1]);
                    } else if (team2[i] instanceof Assasin && pilihan.toLowerCase().equals("stealth")) {
                        Assasin hero = (Assasin) team2[i];
                        if (!hero.isStealth()) {
                            hero.setStealth(true);
                        } else {
                            System.out.println("stealth is already active, can't activate");
                        }
                    } else if (pilihan.toLowerCase().equals("end")){
                        team2[i].energy--;
                        continue;
                    } else {
                        System.out.println("enter the input correctly!!");
                    }
                }
            }
        }
        System.out.println();
    }

    private void computerTurn(Hero[] you, Hero[] bot) throws InterruptedException{
        if (sisaHero(bot) == 0 || sisaHero(you) == 0) {
            return;
        }
        Random random = new Random();
        System.out.println("\n>>> COMPUTER TURN");
        for (int i = 0; i < bot.length; i++) {
            if (bot[i] != null) {
                while (bot[i].energy > 0) {
                    printAllPlayers(you, bot);
                    System.out.print("Computer is choosing action for " + bot[i].name);
                    for (int j = 0; j < 5; j++) {
                        System.out.print(".");
                        Thread.sleep(200);
                    }
                    System.out.println();
        
                    if (bot[i] instanceof Assasin) {
                        int pilihan;
                        if (bot[i].HP == bot[i].maxHP) {
                            pilihan = random.nextInt(3);
                        } else {
                            pilihan = random.nextInt(4);
                        }
                        switch (pilihan) {
                            case 0:
                                int myHeroes = random.nextInt(you.length);
                                bot[i].attackEnemy(you[myHeroes]);
                                if (you[myHeroes].HP <= 0) {
                                    System.out.printf("\n>>> %s has been killed by %s\n", you[myHeroes].name, bot[i].name);
                                    bot[i].upgradeLevel();
                                    deleteHero(you[myHeroes], you);
                                }
                                if (sisaHero(you) == 0) {
                                    return;
                                }
                                break;
                            case 1:
                                int indexItem = random.nextInt(items.length);
                                bot[i].equipItem(items[indexItem]);
                                break;
                            case 2:
                                Assasin hero = (Assasin) bot[i];
                                hero.setStealth(true);
                                break;
                            case 3:
                                bot[i].healing();
                                break;    
                        }
                    } else {
                        int pilihan;
                        if (bot[i].HP == bot[i].maxHP) {
                            pilihan = random.nextInt(2);
                        } else {
                            pilihan = random.nextInt(3);
                        }
                        switch (pilihan) {
                            case 0:
                            int myHeroes = random.nextInt(you.length);
                            bot[i].attackEnemy(you[myHeroes]);
                            if (you[myHeroes].HP <= 0) {
                                System.out.printf("\n>>> %s has been killed by %s\n", you[myHeroes].name, bot[i].name);
                                bot[i].upgradeLevel();
                                deleteHero(you[myHeroes], you);
                            }
                            if (sisaHero(you) == 0) {
                                return;
                            }
                            break;
                            case 1:
                                int indexItem = random.nextInt(items.length);
                                bot[i].equipItem(items[indexItem]);
                                break;
                            case 2:
                                bot[i].healing();
                                break;
                        }                            
                    }
                    System.out.println();
                }
            }
        }
    }

    private void deleteHero(Hero hero, Hero[] heroes){
        for (int i = 0; i < heroes.length; i++) {
            if (heroes[i] != null) {
                if (hero.name == heroes[i].name) {
                    heroes[i] = null;
                }
            }
        }
    }

    public Hero pickHero(Hero hero) {
        Hero newHero = null;
        if (hero instanceof Assasin) {
            newHero = new Assasin(
                hero.name,
                hero.physicalAttack,
                hero.maxHP,
                hero.maxEnergy
            );
        } else if (hero instanceof Marksman) {
            newHero = new Marksman(
                hero.name,
                hero.physicalAttack,
                hero.maxHP,
                hero.maxEnergy
            );
        } else if (hero instanceof Tank) {
            newHero = new Tank(
                hero.name,
                hero.physicalAttack,
                hero.maxHP,
                hero.maxEnergy
            );
        }

        return newHero;
    }

    public int sisaHero(Hero[] heroes){
        int counter = 0;
        for (int i = 0; i < heroes.length; i++) {
            if (heroes[i] != null) {
                counter++;
            }
        }
        return counter;
    }

    private void printItems(){
        for(int i=0; i<items.length; i++){
            System.out.printf("%d. %-30s[", (i+1), items[i].getName());
            if (items[i].getPlusPhysicalAttack() != 0) {
                System.out.printf(" %s+%.0f", "PA", items[i].getPlusPhysicalAttack());
            }
            if (items[i].getPlusArmor() != 0) {
                System.out.printf(" %s+%.2f", "Armor", items[i].getPlusArmor());
            }
            if (items[i].getPlusEnergy() != 0) {
                System.out.printf(" %s+%.0f", "Energy", items[i].getPlusEnergy());
            }
            if (items[i].getPlusHP() != 0) {
                System.out.printf(" %s+%.0f", "HP", items[i].getPlusHP());
            }
            if (items[i].getPlusLifeSteal() != 0) {
                System.out.printf(" %s+%.2f", "LifeSteal", items[i].getPlusLifeSteal());
            }
            System.out.println(" ]");
        }
        System.out.println();
    }
}
