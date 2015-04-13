package game2;

import java.util.ArrayList;
import java.util.List;
import javalib.funworld.*;

public class Game2 {

    static final int WIDTH = 1000;
    static final int HEIGHT = 750;
    static boolean isBattle = false;

    public static void main(String[] args) {
        Player player = new Player("Jah", 0);
        Enemy enemy = new Enemy(5, 5, 5);
        List<Rapper> rappers = new ArrayList<>();
        Everyone set = new Everyone(player, rappers);
        set.add(enemy);
        if (isBattle) {
            World battle = new Battle(player, enemy, set);
            battle.bigBang(WIDTH, HEIGHT, 0.1);
        } else {
            set.add(enemy);
            World overworld = new Overworld(set);
            overworld.bigBang(WIDTH, HEIGHT, 0.1);
        }

    }



}
