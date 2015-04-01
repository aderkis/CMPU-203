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
    public int hype;
    public Integer mana;
    
    public abstract Rapper win(int xp, int attribute);
    public abstract Rapper loss(int xp, int attribute);
    public abstract Rapper move(String key);
    public abstract boolean isDeadHuh();
    public abstract WorldImage draw();
    public abstract WorldImage draw(Posn p);
    public abstract boolean isPlayerHuh();
    
    
}
