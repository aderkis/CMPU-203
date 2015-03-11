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
        //items.add(new Hero());
        items.add(new Enemy());
        //items.add(new Cannon());
        //items.add(new Missile());
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
    public int type();
    public Item collision(Item p);
    public Item setDirection(int d);
    public Item setPathLength(int p);
    
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
            for (int j = 0; j < elements.size(); j++) { 
                Item itemI = elements.get(i);
                Item itemJ = elements.get(j);
                if (!(itemI.type()==-1 || itemJ.type()==-1)) {
                    if (i != j) {
                        if ((dist(itemI, itemJ) <= itemI.radius())
                                || (dist(itemI, itemJ) <= itemJ.radius())) {
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
    public int type() {return -1;}
    public Item collision(Item p) {return this;}
    public Item setDirection(int d) {return this;}
    public Item setPathLength(int p) {return this;}
    
}

class Hero implements Item {
    
    int x;
    int y;
    int dx;
    int dy;
    int lastX;
    int lastY;
    int hp;
    final int RADIUS = 2;
    
    static int MAXH = 22;
    static int MAXW = 73;
    static int MAX = MAXH;

    public Hero() {
        this(MAXW/2, 0, MAXH/2, 0, MAXW/2, MAXH/2, 3);
    }
    private Hero( int x, int dx, int y, int dy, int lastX, int lastY, int hp) {
        this.x = x;
        this.dx = dx;
        this.y = y;
        this.dy = dy;
        this.hp = hp;
    }


    public Item tick () {
        int nx = x + dx;
        int ny = y + dy;
        if ( nx < 0 && ny < 0) {
            return new Hero(0, 0, 0, 0, x, y, this.hp);
        } else if (nx > MAXW && ny > MAXH) {
            return new Hero(MAXW, 0, MAXH, 0, x, y, this.hp);
        } else if (nx > MAXW && ny < 0) {
            return new Hero(MAXW, 0, 0, 0, x, y, this.hp);
        } else if (nx < 0 && ny > MAXH) {
            return new Hero(0, 0, MAXH, 0, x, y, this.hp);
        } else if (nx < 0) {
            return new Hero(0, 0, ny, 0, x, y, this.hp);
        } else if (nx > MAXW) {
            return new Hero(MAXW, 0, ny, 0, x, y, this.hp);
        } else if (ny < 0) {
            return new Hero(nx, 0, 0, 0, x, y, this.hp);
        } else if (ny > MAXH) {
            return new Hero(nx, 0, MAXH, 0, x, y, this.hp);
        } else {
            return new Hero(nx, 0, ny, 0, x, y, this.hp);
        }
    }

    public Item react( CharKey k ) {
        if ( k.isDownArrow() ) {
            return new Hero(x, 0, y, 1, lastX, lastY, this.hp);
        } else if (k.isUpArrow()) {
            return new Hero(x, 0, y, -1, lastX, lastY, this.hp);
        } else if (k.isRightArrow()) {
            return new Hero(x, 1, y, 0, lastX, lastY, this.hp);
        } else if (k.isLeftArrow()) {
            return new Hero(x, -1, y, 0, lastX, lastY, this.hp);
        } else {
            return this;
        }

    }

    public void draw(ConsoleSystemInterface s) {
        s.print(x, y + 0, "HERO", s.CYAN);
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
    
    public int type() {
        return 0;
    }
    
    public Item collision(Item p) {
        int type = p.type();
        Item answer = this;
        switch (type) {
            case 1:
                // +1 point
                answer = new Hero(p.getX(), 0, p.getY(), 0, lastX, lastY, this.hp);
                break;
            case 2:
                answer = new Hero(lastX, 0, lastY, 0, lastX, lastY, this.hp);
                break;
            case 3:
                if (this.hp == 1) {
                    answer = new Dead(0);
                } else {
                    answer = new Hero(this.x, 0, this.y, 0, lastX, lastY, this.hp--);
                }
                break;
            default:
                break;
        }
        return answer;
    }
    
    public Item setDirection(int d) {return this;}
    public Item setPathLength(int p) {return this;}
        
}

class Enemy implements Item {
    
    int x;
    int y;
    int dx;
    int dy;
    int direction;
    int pathLength;
    final int RADIUS = 2;
    
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
    }

    public Item tick () {
        int nx = x+1;
        int ny = y+1;
        int ndx = dx-1;
        int ndy = dy-1;
        if ( nx < 0 && ny < 0) {
            return new Enemy(0, 0, 0, 0);
        } else if (nx > MAXW && ny > MAXH) {
            return new Enemy(MAXW, 0, MAXH, 0);
        } else if (nx > MAXW && ny < 0) {
            return new Enemy(MAXW, 0, 0, 0);
        } else if (nx < 0 && ny > MAXH) {
            return new Enemy(0, 0, MAXH, 0);
        } else if (nx < 0) {
            return new Enemy(0, 0, ny, ndy);
        } else if (nx > MAXW) {
            return new Enemy(MAXW, 0, ny, ndy);
        } else if (ny < 0) {
            return new Enemy(nx, ndx, 0, 0);
        } else if (ny > MAXH) {
            return new Enemy(nx, ndx, MAXH, 0);
        } else {
            return new Enemy(nx, ndx, ny, ndy);
        }
    }

    public Item react(CharKey k) {
        Random randDir = new Random();
        Random randLength = new Random();
        int dir = randDir.nextInt(3);
        int path = 3 + randLength.nextInt(2);
        Item answer = this;
        if (this.pathLength == 0) {
            switch (dir) {
                case 0:
                    answer = new Enemy(x, path, y, 0);
                    break;
                case 1:
                    answer = new Enemy(x, -path, y, 0);
                    break;
                case 2:
                    answer = new Enemy(x, 0, y, path);
                    break;
                case 3:
                    answer = new Enemy(x, 0, y, -path);
                    break;
            }
        }
        answer.setDirection(dir);
        answer.setPathLength(path--);
        return answer;
    }
    
    public Item setDirection(int d) {
        Enemy answer = this;
        answer.direction = d;
        return answer;
    }
    
    public Item setPathLength(int p) {
        Enemy answer = this;
        answer.pathLength = p;
        return answer;
    }

    public void draw(ConsoleSystemInterface s) {
        s.print(x, y + 0, "ENEMY", s.WHITE);

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
    
    public Item collision(Item p) {
        int type = p.type();
        Item answer = this;
        switch (type) {
            case 0:
                answer = new Dead(1);
                break;
            case 1:
                answer = new Enemy(p.getX(), 0, p.getY(), 0);
                break;
            case 2:
                answer = new Enemy(p.getX(), 0, p.getY(), 0);
                break;
            case 3:
                answer = new Dead(1);
                break;
            default:
                break;
        }
        return answer;
    }
    
    public int type() {
        return 1;
    }
    
    
}

class Cannon implements Item {
    
    int x;
    int y;
    
    static int MAXH = 22;
    static int MAXW = 73;
    static int MAX = MAXH;
    final int RADIUS = 4;

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
  
    public int type() {
        return 2;
    }
    
    public Item collision(Item p) {return this;}
    public Item setDirection(int d) {return this;}
    public Item setPathLength(int p) {return this;}
    
}

class Missile implements Item {
    
    int x;
    int y;
    int dx;
    int dy;
    final int RADIUS = 2;
    
    static int MAXH = 22;
    static int MAXW = 73;
    static int MAX = MAXH;

    public Missile() {
        Random rng = new Random();
        this.x = 0;
        this.dx = 0;
        this.y = rng.nextInt(MAXH);
        this.dy = 0;     
    }
    public Missile( int x, int dx, int y, int dy ) {
        this.x = x;
        this.dx = dx;
        this.y = y;
        this.dy = dy;
    }

    public Item tick () {
        int nx = x + dx + 3;
        int ny = y + dy;
        Item answer = new Missile(nx, 0, ny, 0);
        if ( nx < 0 || ny < 0 || nx > MAXW || ny > MAXH) {
            answer = new Dead(3);
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
    
    public Item setDirection(int d) {return this;}
    public Item setPathLength(int p) {return this;}
    
}
