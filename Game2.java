package game2;

import java.util.ArrayList;
import java.util.List;
import javalib.funworld.*;


public class Game2 {
    
    static final int WIDTH = 1000;
    static final int HEIGHT = 750;
    static boolean isBattle = true;
    
    public static void main(String[] args) {
        if (isBattle) {
            Player player = new Player("Jah", 0);
            Enemy enemy = new Enemy(5, 5, 5, 5);
            World battle = new Battle(player, enemy);
            battle.bigBang(WIDTH, HEIGHT, 1);
        } else {
            Player player = new Player("Jah", 0);
            Enemy enemy = new Enemy(5, 5, 5, 5);
            List<Rapper> rappers = new ArrayList<>();
            Everyone set = new Everyone(rappers);
            set.add(enemy);
            World overworld = new Overworld(player, set);
            overworld.bigBang(WIDTH, HEIGHT, 0.1);
        }
        

    }
    


}
