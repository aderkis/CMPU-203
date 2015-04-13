package game2;

import javalib.colors.Black;
import javalib.colors.Red;
import javalib.worldimages.EllipseImage;
import javalib.worldimages.OverlayImages;
import javalib.worldimages.Posn;
import javalib.worldimages.TextImage;
import javalib.worldimages.WorldImage;

public class Player extends Rapper {
       
    public int hype;
        
    static int MAXW = 1000;
    static int MAXH = 750;
    
    public Player(String name, int type) {
        this.name = name;
        this.p = new Posn(0,0);
        this.dx = 0;
        this.dy = 0;
        this.flow = 5;
        this.hype = 5;
        this.type = 0;
        if(type==0) {
            this.lyrics = 10;
            this.presence = 1;
        } else {
            this.lyrics = 1;
            this.presence = 10;
        }
    }
    
    
    public Rapper move(String key) {
        Player answer = this;
        
        if(key.equals("left")) {
            answer.dx = -10;
        }
        if(key.equals("up")) {
            answer.dy = -10;
        }
        if(key.equals("down")) {
            answer.dy = 10;
        }
        if(key.equals("right")) {
            answer.dx = 10;
        }
        
        int nx = p.x + answer.dx;
        int ny = p.y + answer.dy;
        answer.p.x = nx;
        answer.dx = 0;
        answer.p.y = ny;
        answer.dy = 0;

        if (nx < 0 && ny < 0) {
            answer.p.x = 0;
            answer.p.y = 0;
        } else if (nx > MAXW && ny > MAXH) {
            answer.p.x = MAXW;
            answer.p.y = MAXH;
        } else if (nx > MAXW && ny < 0) {
            answer.p.x = MAXW;
            answer.p.y = 0;
        } else if (nx < 0 && ny > MAXH) {
            answer.p.x = 0;
            answer.p.y = MAXH;
        } else if (nx < 0) {
            answer.p.x = 0;
        } else if (nx > MAXW) {
            answer.p.x = MAXW;
        } else if (ny < 0) {
            answer.p.y = 0;
        } else if (ny > MAXH) {
            answer.p.y = MAXH;
        }
        
        return answer;       
    }
    
    public Player win(int xp, int attribute) {
        Player newPlayer = this;
        if(attribute==0) {
            newPlayer.lyrics += xp;
        } else {
            newPlayer.presence += xp;
        }
        newPlayer.flow += xp/2;
        newPlayer.hype += xp/2;
        return newPlayer;
    }
    
    public Player loss(int xp, int attribute) {
        Player newPlayer = this;
        if(attribute==0) {
            newPlayer.lyrics += xp/2;
        } else {
            newPlayer.presence += xp/2;
        }
        newPlayer.flow += xp/4;
        newPlayer.hype -= xp/2;
        return newPlayer;      
    }
    
    public WorldImage draw() {
        WorldImage answer = new EllipseImage(new Posn(100, 100), 100, 50, new Red());
        answer = new OverlayImages(answer, new TextImage(new Posn(100, 100), name, new Black()));
        return answer;
    }
    
    public WorldImage draw(Posn p) {
        WorldImage answer = new EllipseImage(p, 100, 50, new Red());
        answer = new OverlayImages(answer, new TextImage(p, name, new Black()));
        return answer;
    }
    
    private Rapper kill() {return this;}
   
    
}
