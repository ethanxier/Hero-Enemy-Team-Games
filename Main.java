import src.Arena;
import src.Assasin;
import src.Hero;
import src.Item;
import src.Marksman;
import src.Tank;

class Main{

    public static void main(String[] args) throws Exception {
        Hero h1 = new Marksman("Miya", 60, 300, 3);
        Hero h2 = new Assasin("Aamon", 50, 400, 5);
        Hero h3 = new Tank("Lolita", 20, 800, 3);
        Hero h4 = new Marksman("Claude", 55, 350, 3);
        Hero h5 = new Assasin("Gusion", 45, 600, 4);
        Hero h6 = new Tank("Atlas", 15, 1000, 3);

        Hero[] heroes = {h1, h2, h3, h4, h5, h6};

        Item i1 = new Item("BOD", 40, 0, 0, 0, 0);
        Item i2 = new Item("Windtalker", 0, 0, 300, 0, 0);
        Item i3 = new Item("Heart of Tarrasque", 0, 200, 1000, 0, 0);
        Item i4 = new Item("Arcane Boots", 0, 0, 0, 1, 0);
        Item i5 = new Item("Golden Staff", 50, 0, 0, 0, 0.1);
        Item i6 = new Item("Steel Sword", 55, 0.25, 0, 0, 0.2);
        Item i7 = new Item("Platinum Shield", 0, 0.4, 150, 0, 0);
        Item i8 = new Item("Elixir", 0, 0, 300, 2, 0);
        Item i9 = new Item("Vitality Ring", 0, 0, 50, 0, 0.15);
        Item i10 = new Item("Crystal Staff", 30, 0, 0, 2, 0);
        
        Item[] items = {i1, i2, i3, i4, i5, i6, i7, i8, i9, i10};

        Arena arena = new Arena(heroes, items);
        arena.start();
    }
}