package game1;
import net.slashie.libjcsi.wswing.WSwingConsoleInterface;
import net.slashie.libjcsi.ConsoleSystemInterface;
import net.slashie.libjcsi.CharKey;
import java.util.*;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Game1 {
    
    public enum DemoMode { ANIMATION, SLIDESHOW, REACTIVE };
    static DemoMode mode = DemoMode.REACTIVE;

    public static void main(String[] args) throws InterruptedException {
        ConsoleSystemInterface s =
            new WSwingConsoleInterface("game1 by jah", true);

        s.cls();
        s.print(1, 0, "prepare thyself", s.RED);
        s.refresh();
        s.inkey();
        
        List<Item> items = new ArrayList<Item>();
        items.add(new Hero());
        items.add(new Enemy());
        items.add(new Cannon());
        items.add(new Missile());
        Elements set = new Elements(items);

        while (true) {
            s.cls();
            set.draw(s);
            s.refresh();
            if ( mode == DemoMode.ANIMATION ) {
                // If you want an animation, then ignore the input and
                // sleep before continuing
                TimeUnit.MILLISECONDS.sleep(16 * 4);
            } else if ( mode == DemoMode.SLIDESHOW ) {
                // If you want the player to press a specific key
                // before continuing:
                s.waitKey(CharKey.SPACE);
            } else if ( mode == DemoMode.REACTIVE ) {
                // If you want the player to press any key and/or you
                // want to inspect the key before continuing
                CharKey k = s.inkey();
                set.react(k);
                set.collisions();
            }
            set.tick();
        }
    }
    

    }
    
interface Item {
    
    public Item tick();
    public Item react(CharKey k );
    public void draw(ConsoleSystemInterface s);
    public int getX();
    public int getY();
    public int radius();
    public void hit();
    public int type();
    public Item collision(Item p);
    public boolean isDeadHuh();
    
}

class Elements {
    
    List<Item> elements = new ArrayList<Item>();
    
    public Elements(List<Item> elements) {
        this.elements = elements;
    }
    
    public Elements add(Item elt) {
        Elements answer = this;
        answer.elements.add(elt);
        return answer;
    }
    
    public void tick() {
        for(int i=0; i<elements.size(); i++) {
            Elements answer = this;
            answer.elements.set(i, elements.get(i).tick());
        }
    }
    
    public void react(CharKey k) {
        for(int i=0; i<elements.size(); i++) {
            Elements answer = this;
            answer.elements.set(i, elements.get(i).react(k));
        }
    }
    
    public void draw(ConsoleSystemInterface s) {
        for(int i=0; i<elements.size(); i++) {
            elements.get(i).draw(s);
        }
    }
    
    public static int dist(Item p, Item q) {
        return (int)Math.sqrt((p.getX()-q.getX())^2 + (p.getY()-q.getY())^2);
    }
    
    public Elements collisions() {
        Elements answer = this;
        for (int i = 0; i < elements.size(); i++) {
            for (int j = 0; j < elements.size(); i++) {
                Item itemI = elements.get(i);
                Item itemJ = elements.get(j);
                if (!(itemI.isDeadHuh() || itemJ.isDeadHuh())) {
                    if (i != j) {
                        if ((dist(itemI, itemJ) < itemI.radius())
                                || (dist(itemI, itemJ) < itemJ.radius())) {
                            answer.elements.set(i, itemI.collision(itemJ));
                            answer.elements.set(j, itemJ.collision(itemI));
                        }
                    }
                }
            }
        }
        return answer;
    }

}

class Dead implements Item {
    
    //0 is Hero
    //1 is Enemy
    //2 is Cannon
    //3 is Missile
    //only intended to be Enemies and Missiles because if a Hero dies, game is over
    
    int formerType;
    
    public Dead(int type) {
        this.formerType = type;
    }
    
    public Item tick() {return this;}
    public Item react(CharKey k ) {return this;}
    public void draw(ConsoleSystemInterface s) {}
    public int getX() {return -1;}
    public int getY() {return -1;}
    public int radius() {return -1;}
    public void hit() {}
    public int type() {return -1;}
    public Item collision(Item p) {return this;}
    public boolean isDeadHuh() {return true;}
    
}

class Hero implements Item {
    
    int x;
    int y;
    int dx;
    int dy;
    int hp;
    boolean isHit;
    final int RADIUS = 5;
    
    static int MAXH = 22;
    static int MAXW = 73;
    static int MAX = MAXH;

    public Hero() {
        this(MAXW/2, 0, MAXH/2, 0, 10);
    }
    private Hero( int x, int dx, int y, int dy, int hp) {
        this.x = x;
        this.dx = dx;
        this.y = y;
        this.dy = dy;
        this.hp = hp;
        this.isHit = false;
    }


    public Item tick () {
        int nx = x + dx;
        int ny = y + dy;
        if ( nx < 0 && ny < 0) {
            return new Hero(0, 0, 0, 0, this.hp);
        } else if (nx > MAXW && ny > MAXH) {
            return new Hero(MAXW, 0, MAXH, 0, this.hp);
        } else if (nx > MAXW && ny < 0) {
            return new Hero(MAXW, 0, 0, 0, this.hp);
        } else if (nx < 0 && ny > MAXH) {
            return new Hero(0, 0, MAXH, 0, this.hp);
        } else if (nx < 0) {
            return new Hero(0, 0, ny, 0, this.hp);
        } else if (nx > MAXW) {
            return new Hero(MAXW, 0, ny, 0, this.hp);
        } else if (ny < 0) {
            return new Hero(nx, 0, 0, 0, this.hp);
        } else if (ny > MAXH) {
            return new Hero(nx, 0, MAXH, 0, this.hp);
        } else {
            return new Hero(nx, 0, ny, 0, this.hp);
        }
    }

    public Item react( CharKey k ) {
        if ( k.isDownArrow() ) {
            return new Hero(x, 0, y, 1, this.hp);
        } else if (k.isUpArrow()) {
            return new Hero(x, 0, y, -1, this.hp);
        } else if (k.isRightArrow()) {
            return new Hero(x, 1, y, 0, this.hp);
        } else if (k.isLeftArrow()) {
            return new Hero(x, -1, y, 0, this.hp);
        } else {
            return this;
        }

    }

    public void draw(ConsoleSystemInterface s) {
        s.print(x, y + 0, "   +   ", s.CYAN);
        s.print(x, y + 1, "  / \\ ", s.CYAN);
        s.print(x, y + 2, "WARRIOR", s.CYAN);

    }
    
    public int getX() {
        return this.x;
    }
    
    public int getY() {
        return this.y;
    }
    
    public int radius() {
        return this.RADIUS;
    }
    
    public void hit() {
        this.isHit = true;
    }
    
    public int type() {
        return 0;
    }
    
    public Item collision(Item p) {
        int type = p.type();
        Item answer = this;
        switch (type) {
            case 1:
                // +1 point
                answer = new Hero(p.getX(), 0, p.getY(), 0, this.hp);
                break;
            case 3:
                if (this.hp == 1) {
                    answer = new Dead(0);
                } else {
                    answer = new Hero(this.x, 0, this.y, 0, this.hp--);
                }
                break;
            default:
                break;
        }
        return answer;
    }
    
    public boolean isDeadHuh() {return false;}
    
    
}

class Enemy implements Item {
    
    int x;
    int y;
    int dx;
    int dy;
    boolean isHit;
    final int RADIUS = 5;
    
    static int MAXH = 22;
    static int MAXW = 73;
    static int MAX = MAXH;

    public Enemy() {
        this(0, 0, 0, 0);
    }
    
    private Enemy( int x, int dx, int y, int dy ) {
        this.x = x;
        this.dx = dx;
        this.y = y;
        this.dy = dy;
        this.isHit = false;
    }

    public Item tick () {
        int nx = x + dx;
        int ny = y + dy;
        if ( nx < 0 && ny < 0) {
            return new Enemy(0, 0, 0, 0);
        } else if (nx > MAXW && ny > MAXH) {
            return new Enemy(MAXW, 0, MAXH, 0);
        } else if (nx > MAXW && ny < 0) {
            return new Enemy(MAXW, 0, 0, 0);
        } else if (nx < 0 && ny > MAXH) {
            return new Enemy(0, 0, MAXH, 0);
        } else if (nx < 0) {
            return new Enemy(0, 0, ny, 0);
        } else if (nx > MAXW) {
            return new Enemy(MAXW, 0, ny, 0);
        } else if (ny < 0) {
            return new Enemy(nx, 0, 0, 0);
        } else if (ny > MAXH) {
            return new Enemy(nx, 0, MAXH, 0);
        } else {
            return new Enemy(nx, 0, ny, 0);
        }
    }

    public Item react( CharKey k ) {
        Random rng = new Random();
        Item answer = this;
        int direction = rng.nextInt(3);
        switch(direction) {
            case 0: answer = new Enemy(x, 1, y, 0); break;
            case 1: answer = new Enemy(x, -1, y, 0); break;
            case 2: answer = new Enemy(x, 0, y, 1); break;
            case 3: answer = new Enemy(x, 0, y, -1); break;             
        }
        return answer;
    }

    public void draw(ConsoleSystemInterface s) {
        s.print(x, y + 0, " /\\ /\\ ", s.WHITE);
        s.print(x, y + 1, " |  |  ", s.WHITE);
        s.print(x, y + 2, "DIABLO", s.WHITE);

    }
    
    public int getX() {
        return this.x;
    }
    
    public int getY() {
        return this.y;
    }
    
    public int radius() {
        return this.RADIUS;
    }
    
    public void hit() {
        this.isHit = true;
    }
    
    public Item collision(Item p) {
        int type = p.type();
        Item answer = this;
        switch(type) {
            case 0: answer = new Dead(1); break;
            case 1: answer = new Enemy(p.getX(), 0, p.getY(), 0);
            break;
            case 3: answer = new Dead(1);
                break;
            default: break;             
        }
        return answer;
    }
    
    public int type() {
        return 1;
    }
    
    public boolean isDeadHuh() {return false;}
    
}

class Cannon implements Item {
    
    int x;
    int y;
    
    static int MAXH = 22;
    static int MAXW = 73;
    static int MAX = MAXH;
    final int RADIUS = 7;

    public Cannon() {
        this(10, 10);
    }
    private Cannon( int x, int y ) {
        this.x = x;
        this.y = y;
    }

    public Item tick () {
        return this;
    }

    public Item react( CharKey k ) {
        return this;

    }
    
    public Item shoot() {
        return new Missile(x, 0, y, 0);
    }

    public void draw ( ConsoleSystemInterface s ) {
        s.print(x,  y+0, "  ______ ", s.GRAY);
        s.print(x,  y+1, " /      \\ ", s.GRAY);
        s.print(x,  y+2, "|       |", s.GRAY);
        s.print(x,  y+3, "|       |", s.GRAY);
        s.print(x,  y+4, "\\_______/", s.GRAY);
        
        
    }
    
    
    public int getX() {
        return this.x;
    }
    
    public int getY() {
        return this.y;
    }
    
    public int radius() {
        return this.RADIUS;
    }
    
    public void hit() { }
  
    public int type() {
        return 2;
    }
    
    public Item collision(Item p) {return this;}
    
    public boolean isDeadHuh() {return false;}
    
}

class Missile implements Item {
    
    int x;
    int y;
    int dx;
    int dy;
    boolean isHit;
    final int RADIUS = 2;
    
    static int MAXH = 22;
    static int MAXW = 73;
    static int MAX = MAXH;

    public Missile() {
        this(25, 0, 10, 0);
    }
    public Missile( int x, int dx, int y, int dy ) {
        this.x = x;
        this.dx = dx;
        this.y = y;
        this.dy = dy;
        this.isHit = false;
    }

    public Item tick () {
        int nx = x + dx + 3;
        int ny = y + dy;
        Missile answer = new Missile(nx, 0, ny, 0);
        if ( nx < 0 || ny < 0 || nx > MAXW || ny > MAXH) {
            answer.isHit = true;
        } 
        return answer;
    }

    public Item react( CharKey k ) {
        return this;

    }

    public void draw(ConsoleSystemInterface s) {
        s.print(x, y, "HATE", s.RED);
    }
    
    public int getX() {
        return this.x;
    }
    
    public int getY() {
        return this.y;
    }
    
    public int radius() {
        return this.RADIUS;
    }
    
    public void hit() {
        this.isHit = true;
    }
    
    public int type() {
        return 3;
    }
    
    public Item collision(Item p) {
        int type = p.type();
        Item answer = this;
        switch (type) {
            case 0:
                answer = new Dead(3);
                break;
            case 1:
                answer = new Dead(3);
                break;
            case 2:
                answer = new Dead(3);
                break;
            case 3:
                answer = new Dead(3);
                break;
            default:
                break;
        }
        return answer;
    }
    
    public boolean isDeadHuh() {return false;}
    
}
