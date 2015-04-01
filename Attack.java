package game2;
import javalib.colors.Black;
import javalib.colors.IColor;
import javalib.colors.Red;
import javalib.worldimages.Posn;
import javalib.worldimages.TextImage;
import javalib.worldimages.WorldImage;

public class Attack {
    
    public String name;
    public int type;
    public int requiredLevel;
    public int damage;
    public boolean selected;
    
    public Attack(String name, int type, int requiredLevel, int damage) {
        this.name = name;
        this.type = type;
        this.requiredLevel = requiredLevel;
        this.damage = damage;
    }
    
    public WorldImage draw(Posn p) {
        IColor color;
        if(selected) {
            color = new Red();
        } else {
            color = new Black();
        }
        return new TextImage(p, name, color);
    }
    
}
