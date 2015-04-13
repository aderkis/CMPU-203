package game2;

import javalib.colors.Black;
import javalib.funworld.World;
import javalib.worldimages.OverlayImages;
import javalib.worldimages.Posn;
import javalib.worldimages.TextImage;
import javalib.worldimages.WorldImage;

public class Overworld extends World {
    
    public Everyone set;
    
    public Overworld(Everyone set) {
        this.set = set;
    }
    
    public World onTick() {
        Overworld answer = this;
        if (!set.battle) {       
            for (Rapper r : answer.set.rappers) {
                r = r.move("that dope");
            }
            answer.set = answer.set.collisions();
            return answer;
        } else {
           return new Battle((Player) set.player, (Enemy)set.rappers.get(0), set);
        }

    }

    public World onKeyEvent(String key) {       
        Overworld answer = this;
        if ("d".equals(key)) {
            int d = answer.set.player.dist(answer.set.rappers.get(0));
            System.out.println("Distance: " + d);
        }
        answer.set.player = answer.set.player.move(key);
        return this;
    }
    
    public WorldImage makeImage() {
        WorldImage answer = set.player.draw(set.player.p);
        for(Rapper r : set.rappers) {
            answer = new OverlayImages(answer, r.draw(r.p));
        }
        return answer;
    }
    
}
