package game1;
import net.slashie.libjcsi.wswing.WSwingConsoleInterface;
import net.slashie.libjcsi.ConsoleSystemInterface;
import net.slashie.libjcsi.CharKey;
import java.util.*;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.awt.Rectangle;

public class Game1 {
    
    public enum DemoMode { ANIMATION, SLIDESHOW, REACTIVE };
    static DemoMode mode = DemoMode.REACTIVE;

    public static void main(String[] args) throws InterruptedException {
        int missileTimer = 0;
        int enemyTimer = 0;
        Random rng = new Random();
        
        ConsoleSystemInterface s =
            new WSwingConsoleInterface("game1 by jah", true);

        s.cls();
        s.print(1, 0, "prepare thyself, then press any key", s.RED);
        s.refresh();
        s.inkey();
        
        List<Item> items = new ArrayList<Item>();
        Elements set = new Elements(items);
        set.add(new Hero());

        while (!set.isEndHuh()) {
            if(missileTimer==0) {
                set.add(new Missile());
                missileTimer = 1+rng.nextInt(5);
            }
            if(enemyTimer==0) {
                set.add(new Enemy());
                enemyTimer = 1+rng.nextInt(10);
            }
            s.cls();           
            set.draw(s);
            s.print(0, 24, "SCORE: " + set.score(), s.YELLOW);
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
            s.refresh();
            missileTimer = missileTimer - 1;
            enemyTimer = enemyTimer - 1;
            set.tick();            
        }       
       
        s.cls();
        s.print(0, 0, "GAME OVER", s.WHITE);
        s.print(0, 1, "SCORE: " + set.score(), s.YELLOW);
        
    }
    

    }
    
interface Item {
    
    public Item tick();
    public Item react(CharKey k );
    public void draw(ConsoleSystemInterface s);
    public int getX();
    public int getY();
    public Rectangle bounds();
    public int type();
    public Item collision(Item p);
    public boolean isEndHuh();
    public Item end();
    public boolean wasEnemy();
    
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
    
    public Elements collisions() {
        Elements answer = this;
        for (int i = 0; i < elements.size(); i++) {
            for (int j = 0; j < elements.size(); j++) { 
                Item itemI = elements.get(i);
                Item itemJ = elements.get(j);
                Rectangle iBounds = itemI.bounds();
                Rectangle jBounds = itemJ.bounds();
                if (!(itemI.type()==-1 || itemJ.type()==-1)) {
                    if (i != j) {
                        if (iBounds.intersects(jBounds)) {
                            answer.elements.set(i, itemI.collision(itemJ));
                            answer.elements.set(j, itemJ.collision(itemI));
                        }
                    }
                }
            }
        }
        return answer;
    }
    
    public boolean isEndHuh() {
        boolean answer = false;
        for(int i=0; i<this.elements.size(); i++) {
            if(this.elements.get(i).isEndHuh()) {
                answer = true;
            }
        }
        return answer;
    }
    
    public String score() {
        Integer answer = 0;
        for(int i=0; i<this.elements.size(); i++) {
            if(this.elements.get(i).type()==-1) {
                if(this.elements.get(i).wasEnemy()) {
                    answer += 1;
                }
            }
        }
        return answer.toString();
    }

}

class Dead implements Item {
    
    //0 is Hero
    //1 is Enemy
    //2 is Missile
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
    public Rectangle bounds() {return new Rectangle();}
    public int type() {return -1;}
    public Item collision(Item p) {return this;}
    public boolean isEndHuh() {return false;}
    public Item end() {return this;}
    
    public boolean wasEnemy() {
        return this.formerType==1;
    }
    
}

class Hero implements Item {
    
    int x;
    int y;
    int dx;
    int dy;
    int lastX;
    int lastY;
    int hp;
    boolean isDead;
    
    static int MAXH = 23;
    static int MAXW = 76;

    public Hero() {
        Random rng = new Random();
        this.x = rng.nextInt(MAXW);
        this.dx = 0;
        this.y = rng.nextInt(MAXH);
        this.dy = 0;
        this.hp = 3;
        this.isDead = false;
        
    }
    private Hero( int x, int dx, int y, int dy, int lastX, int lastY, int hp) {
        this.x = x;
        this.dx = dx;
        this.y = y;
        this.dy = dy;
        this.hp = hp;
        this.isDead = false;
    }

    public Item tick() {
        int nx = x + dx;
        int ny = y + dy;
        if (this.hp <= 0) {
            return this.end();
        } else if (nx < 0 && ny < 0) {
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
        String answer = "";
        switch(hp) {
            case 2:
                answer += ".";
                break;
            case 3:
                answer += "!";
                break;
            default:
                answer += " ";
                break;
        }
        s.print(x, y + 0, "GOOD", s.CYAN);
        s.print(x, y + 1, "GUY"+answer, s.CYAN);
    }
    
    public int getX() {
        return this.x;
    }
    
    public int getY() {
        return this.y;
    }
    
    public Rectangle bounds() {
        return new Rectangle(x, y, 4, 2);
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
                answer = new Hero(this.x, 0, this.y, 0, lastX, lastY, this.hp-1);
                break;
            default:
                break;
        }
        return answer;
    }
    
    public boolean isEndHuh() {
        return this.isDead;
        
    }
    
    public Item end() {
        Hero answer = this;
        answer.isDead = true;
        return answer;
    }
    
    public boolean wasEnemy() {return false;}
        
}

class Enemy implements Item {
    
    int x;
    int y;
    int dx;
    int dy;
    int direction;
    int pathLength;
    
    static int MAXH = 23;
    static int MAXW = 77;

    public Enemy() {
        Random rng = new Random();
        this.x = rng.nextInt(MAXW);
        this.dx = 0;
        this.y = rng.nextInt(MAXH);
        this.dy = 0;
    }
    
    private Enemy( int x, int dx, int y, int dy ) {
        this.x = x;
        this.dx = dx;
        this.y = y;
        this.dy = dy;
    }
    
    public Enemy(int x, int dx, int y, int dy, int dir, int path) {
        this.x = x;
        this.dx = dx;
        this.y = y;
        this.dy = dy;
        this.direction = dir;
        this.pathLength = path;
    }

    public Item tick () {
        int nx = x+dx;
        int ny = y+dy;
        if ( nx < 0 && ny < 0) {
            return new Enemy(0, 0, 0, 0, -1, 0);
        } else if (nx > MAXW && ny > MAXH) {
            return new Enemy(MAXW, 0, MAXH, 0, -1, 0);
        } else if (nx > MAXW && ny < 0) {
            return new Enemy(MAXW, 0, 0, 0, -1, 0);
        } else if (nx < 0 && ny > MAXH) {
            return new Enemy(0, 0, MAXH, 0, -1, 0);
        } else if (nx < 0) {
            return new Enemy(0, 0, ny, 0, -1, pathLength);
        } else if (nx > MAXW) {
            return new Enemy(MAXW, 0, ny, 0, -1, pathLength);
        } else if (ny < 0) {
            return new Enemy(nx, 0, 0, 0, -1, pathLength);
        } else if (ny > MAXH) {
            return new Enemy(nx, 0, MAXH, 0, -1, pathLength);
        } else {
            return new Enemy(nx, 0, ny, 0, direction, pathLength);
        }
    }

    public Item react(CharKey k) {
        Random rng = new Random();
        int dir = rng.nextInt(4);
        int path = 8+rng.nextInt(12);
        Enemy answer = this;
        if (this.pathLength <= 0 || this.direction == -1) {
            answer.pathLength = path;
            answer.direction = dir;
        }
        switch (direction) {
            case 0:
                answer = new Enemy(x, 1, y, 0, answer.direction, answer.pathLength-1);
                break;
            case 1:
                answer = new Enemy(x, -1, y, 0, answer.direction, answer.pathLength-1);
                break;
            case 2:
                answer = new Enemy(x, 0, y, -1, answer.direction, answer.pathLength-1);
                break;
        }
        return answer;
    }
    

    public void draw(ConsoleSystemInterface s) {
        s.print(x, y + 0, "BAD", s.WHITE);
        s.print(x, y + 1, "GUY", s.WHITE);

    }
    
    public int getX() {
        return this.x;
    }
    
    public int getY() {
        return this.y;
    }
    
    public Rectangle bounds() {
        return new Rectangle(x, y, 3, 2);
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
    
    public boolean isEndHuh() {return false;}
    public Item end() {return this;}
    public boolean wasEnemy() {return false;}
    
    
}

class Missile implements Item {
    
    int x;
    int y;
    int dx;
    int dy;
    int dir;
    
    static int MAXH = 24;
    static int MAXW = 76;

    public Missile() {
        Random rng = new Random();
        int newDir = rng.nextInt(2);
        switch(newDir) {
            case 0:
                this.x = 0;
                break;
            case 1:
                this.x = MAXW;
                break;
        }
        this.dx = 0;
        this.y = rng.nextInt(MAXH);
        this.dy = 0;
        this.dir = newDir;
    }
    
    public Missile( int x, int dx, int y, int dy, int dir ) {
        this.x = x;
        this.dx = dx;
        this.y = y;
        this.dy = dy;
        this.dir = dir;
    }

    public Item tick () {
        int nx = x;
        switch(dir) {
            case 0: 
                nx = nx+dx;
                break;
            case 1:
                nx = nx-dx;
                break;
        }
        Item answer = new Missile(nx, 0, y, 0, dir);
        if ( nx < 0 || nx > MAXW) {
            answer = new Dead(2);
        } 
        return answer;
    }

    public Item react( CharKey k ) {
        Missile answer = this;
        answer.dx = 3;
        return answer;
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
    
    public Rectangle bounds() {
        return new Rectangle(x, y, 4, 1);
    }
    
    public int type() {
        return 2;
    }
    
    public Item collision(Item p) {
        int type = p.type();
        Item answer = this;
        switch (type) {
            case 0:
                answer = new Dead(2);
                break;
            case 1:
                answer = new Dead(2);
                break;
            case 2:
                answer = new Dead(2);
                break;
            default:
                break;
        }
        return answer;
    }
    
    public boolean isEndHuh() {return false;}
    public Item end() {return this;}
    public boolean wasEnemy() {return false;}
    
}


