package game2;

import javalib.colors.Black;
import javalib.funworld.World;
import javalib.worldimages.OverlayImages;
import javalib.worldimages.Posn;
import javalib.worldimages.TextImage;
import javalib.worldimages.WorldImage;

public class Overworld extends World {
    
    public Rapper player;
    public Everyone set;
    
    public Overworld(Player player, Everyone set) {
        this.player = player;
        this.set = set;
    }
    
    public World onTick() {
        Overworld answer = this;
        for (Rapper r : answer.set.rappers) {
            r = r.move("");
            
        }
        return answer;
    }

    public World onKeyEvent(String key) {
        Overworld answer = this;
        answer.player = answer.player.move(key);
        return this;
    }
    
    public WorldImage makeImage() {
        WorldImage answer = new TextImage(new Posn(50, 850), "hey", new Black());
        answer = new OverlayImages(answer, player.draw(player.p));
        for(Rapper r : set.rappers) {
            answer = new OverlayImages(answer, r.draw(r.p));
        }
        return answer;
    }
    
}
