package game1;
import net.slashie.libjcsi.wswing.WSwingConsoleInterface;
import net.slashie.libjcsi.ConsoleSystemInterface;
import net.slashie.libjcsi.CharKey;
import java.util.*;
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
            }
            set.tick();
        }
    }

    }
    
interface Item {
    
    public Item tick();
    public Item react(CharKey k );
    public void draw(ConsoleSystemInterface s);
    
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
            elements.get(i).tick();
        }
    }
    
    public void react(CharKey k) {
        for(int i=0; i<elements.size(); i++) {
            elements.get(i).react(k);
        }
    }
    
    public void draw(ConsoleSystemInterface s) {
        for(int i=0; i<elements.size(); i++) {
            elements.get(i).draw(s);
        }
    }
}

class Hero implements Item {
    
    int x;
    int y;
    int dx;
    int dy;
    
    static int MAXH = 22;
    static int MAXW = 73;
    static int MAX = MAXH;

    public Hero() {
        this(MAXW/2, 0, MAXH/2, 0);
    }
    private Hero( int x, int dx, int y, int dy ) {
        this.x = x;
        this.dx = dx;
        this.y = y;
        this.dy = dy;
    }

    public Item tick () {
        int nx = x + dx;
        int ny = y + dy;
        if ( nx < 0 && ny < 0) {
            return new Hero(0, 0, 0, 0);
        } else if (nx > MAXW && ny > MAXH) {
            return new Hero(MAXW, 0, MAXH, 0);
        } else if (nx > MAXW && ny < 0) {
            return new Hero(MAXW, 0, 0, 0);
        } else if (nx < 0 && ny > MAXH) {
            return new Hero(0, 0, MAXH, 0);
        } else if (nx < 0) {
            return new Hero(0, 0, ny, 0);
        } else if (nx > MAXW) {
            return new Hero(MAXW, 0, ny, 0);
        } else if (ny < 0) {
            return new Hero(nx, 0, 0, 0);
        } else if (ny > MAXH) {
            return new Hero(nx, 0, MAXH, 0);
        } else {
            return new Hero(nx, 0, ny, 0);
        }
    }

    public Item react( CharKey k ) {
        if ( k.isDownArrow() ) {
            return new Hero(x, 0, y, 1);
        } else if (k.isUpArrow()) {
            return new Hero(x, 0, y, -1);
        } else if (k.isRightArrow()) {
            return new Hero(x, 1, y, 0);
        } else if (k.isLeftArrow()) {
            return new Hero(x, -1, y, 0);
        } else {
            return this;
        }

    }

    public void draw ( ConsoleSystemInterface s ) {
        String disp;
        switch (y % 4) {
         case 0: disp =  "/"; break;
         case 1: disp =  "|"; break;
         case 2: disp = "\\"; break;
        default: disp =  "-"; break;
        }
        // s.print(x, y+3, disp, s.WHITE);

        s.print(x,  y+0, "   +   ", s.CYAN);
        s.print(x,  y+1, "  / \\ ", s.CYAN);
        s.print(x,  y+2, "WARRIOR", s.CYAN);
        
    }

}

class Enemy implements Item {
    
    int x;
    int y;
    int dx;
    int dy;
    
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
        int nx = x + dx + 1;
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
        return this;
    }

    public void draw ( ConsoleSystemInterface s ) {
        s.print(x,  y+0, " /\\ /\\ ", s.WHITE);
        s.print(x,  y+1, " |  |  ", s.WHITE);
        s.print(x,  y+2, "DIABLO", s.WHITE);
        
    }
    
}

class Cannon implements Item {
    
    int x;
    int y;
    
    static int MAXH = 22;
    static int MAXW = 73;
    static int MAX = MAXH;

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
    
}

class Missile implements Item {
    
    int x;
    int y;
    int dx;
    int dy;
    
    static int MAXH = 22;
    static int MAXW = 73;
    static int MAX = MAXH;

    public Missile() {
        this(50, 0, 50, 0);
    }
    public Missile( int x, int dx, int y, int dy ) {
        this.x = x;
        this.dx = dx;
        this.y = y;
        this.dy = dy;
    }

    public Item tick () {
        int nx = x + dx;
        int ny = y + dy;
        if ( nx < 0 && ny < 0) {
            return new Missile(0, 0, 0, 0);
        } else if (nx > MAXW && ny > MAXH) {
            return new Missile(MAXW, 0, MAXH, 0);
        } else if (nx > MAXW && ny < 0) {
            return new Missile(MAXW, 0, 0, 0);
        } else if (nx < 0 && ny > MAXH) {
            return new Missile(0, 0, MAXH, 0);
        } else if (nx < 0) {
            return new Missile(0, 0, ny, 0);
        } else if (nx > MAXW) {
            return new Missile(MAXW, 0, ny, 0);
        } else if (ny < 0) {
            return new Missile(nx, 0, 0, 0);
        } else if (ny > MAXH) {
            return new Missile(nx, 0, MAXH, 0);
        } else {
            return new Missile(nx, 0, ny, 0);
        }
    }

    public Item react( CharKey k ) {
        return this;

    }

    public void draw ( ConsoleSystemInterface s ) {
        s.print(x,  y, "HATE", s.RED);
        
    }
    
}
