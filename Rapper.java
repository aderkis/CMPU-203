package game2;

import javalib.worldimages.*;

public abstract class Rapper {
    
    public String name;
    public WorldImage sprite;
    public Posn p;
    public int dx;
    public int dy;
    public int flow;
    public int lyrics;
    public int presence;
    public int type;

    public Integer mana;
    
    public abstract Rapper win(int xp, int attribute);
    public abstract Rapper loss(int xp, int attribute);
    public abstract Rapper move(String key);
    public abstract WorldImage draw();
    public abstract WorldImage draw(Posn p);
    
    public Integer dist(Rapper r) {
        int diffX = r.p.x-p.x;
        int diffY = r.p.y-p.y;
        return (int)Math.sqrt((int)(Math.pow(diffX, 2)+Math.pow(diffY, 2)));
    }
    
    
}
