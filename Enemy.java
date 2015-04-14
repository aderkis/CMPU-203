package game2;

import java.util.Random;
import javalib.colors.Black;
import javalib.colors.Blue;
import javalib.worldimages.EllipseImage;
import javalib.worldimages.OverlayImages;
import javalib.worldimages.Posn;
import javalib.worldimages.TextImage;
import javalib.worldimages.WorldImage;

public class Enemy extends Rapper {
    
    public int pathLength;
    public int direction;
    public boolean isDead;
    
    static int MAXW = 1000;
    static int MAXH = 750;
    
    static String[] firstNames = new String[] {"", "Yung ", "Lil ", "Big "};
    static String[] lastNames = new String[] {"Killa", "Swag", "Spitta"};
    static WorldImage[] spritePool = new WorldImage[10];
    public Random rng = new Random();
    
    public Enemy(int flow, int lyrics, int presence) {
        this.name = firstNames[rng.nextInt(firstNames.length)]+lastNames[rng.nextInt(lastNames.length)];
        this.sprite = spritePool[rng.nextInt(spritePool.length)];
        this.p = new Posn(rng.nextInt(MAXW), rng.nextInt(MAXH));
        this.dx = 0;
        this.dy = 0;
        this.flow = flow;
        this.lyrics = lyrics;
        this.presence = presence;
        this.pathLength = 0;
        this.direction = -1;
        this.isDead = false; 
        this.type = 1;
    }
    
    
    public Rapper move(String key) {
        Enemy answer = this;
        int dir = rng.nextInt(4);
        int path = 5 + rng.nextInt(60);
        if (this.pathLength <= 0 || this.direction == -1) {
            answer.pathLength = path;
            answer.direction = dir;
        }
        switch (direction) {
            case 0:
                answer.dx = 10;
                break;
            case 1:
                answer.dx = -10;
                break;
            case 2:
                answer.dy = -10;
                break;
            case 3:
                answer.dy = 10;
                break;
        }
        answer.pathLength -= 1;
        
        int nx = p.x + answer.dx;
        int ny = p.y + answer.dy;
        
        answer.p.x = nx;
        answer.dx = 0;
        answer.p.y = ny;
        answer.dy = 0;
        
        if (nx < 0 && ny < 0) {
            answer.p.x = 0;
            answer.p.y = 0;
            answer.direction = -1;
        } else if (nx > MAXW && ny > MAXH) {
            answer.p.x = MAXW;
            answer.p.y = MAXH;
            answer.direction = -1;
        } else if (nx > MAXW && ny < 0) {
            answer.p.x = MAXW;
            answer.p.y = 0;
            answer.direction = -1;
        } else if (nx < 0 && ny > MAXH) {
            answer.p.x = 0;
            answer.p.y = MAXH;
            answer.direction = -1;
        } else if (nx < 0) {
            answer.p.x = 0;
            answer.direction = -1;
        } else if (nx > MAXW) {
            answer.p.x = MAXW;
            answer.direction = -1;
        } else if (ny < 0) {
            answer.p.y = 0;
            answer.direction = -1;
        } else if (ny > MAXH) {
            answer.p.y = MAXH;
            answer.direction = -1;
        } 
        return answer;
    }
    
    public boolean isDeadHuh() {
        return isDead;
    }
    
    public WorldImage draw() {
        WorldImage answer = new EllipseImage(new Posn(700, 100), 100, 50, new Blue());
        answer = new OverlayImages(answer, new TextImage(new Posn(700, 100), name, new Black()));
        return answer;
    }
    
    public WorldImage draw(Posn p) {
        WorldImage answer = new EllipseImage(p, 100, 50, new Blue());
        answer = new OverlayImages(answer, new TextImage(p, name, new Black()));
        answer = new OverlayImages(answer, new TextImage(new Posn(p.x, p.y+15), "Flow: " + flow, new Black()));
        return answer;
    }
    
    
}
