package game1;

import net.slashie.libjcsi.wswing.WSwingConsoleInterface;
import net.slashie.libjcsi.ConsoleSystemInterface;
import net.slashie.libjcsi.CharKey;
import java.util.*;
import java.util.Random;
import java.awt.Rectangle;

public class Game1 {
    
    static boolean TEST = false;

    public static void main(String[] args) throws InterruptedException {
        
        if(TEST) {
            System.out.println("Number of failed tests: " + Test.test().toString());
            System.out.println("Test 1 result (0=pass, 1=fail): " + Test.test1().toString());
            System.out.println("Test 2 result (0=pass, 1=fail): " + Test.test2().toString());
            System.out.println("Test 3 result (0=pass, 1=fail): " + Test.test3().toString());
            System.out.println("Test 4 result (0=pass, 1=fail): " + Test.test4().toString());
            System.out.println("Test 5 result (0=pass, 1=fail): " + Test.test5().toString());
            System.out.println("Test 6 result (0=pass, 1=fail): " + Test.test6().toString());
            System.out.println("Test 7 result (0=pass, 1=fail): " + Test.test7().toString());
            System.out.println("Test 8 result (0=pass, 1=fail): " + Test.test8().toString());
            System.out.println("Test 9 result (0=pass, 1=fail): " + Test.test9().toString());
            System.out.println("Test 10 result (0=pass, 1=fail): " + Test.test10().toString());
            System.out.println("Test 11 result (0=pass, 1=fail): " + Test.test11().toString());
            
        }

        List<Item> items = new ArrayList<>();
        Elements set = new Elements(items);

        ConsoleSystemInterface s
                = new WSwingConsoleInterface("game1 by jah", true);

        game(s, set);
    }

    static void game(ConsoleSystemInterface s, Elements set) {
        int missileTimer = 0;
        int enemyTimer = 0;
        Random rng = new Random();

        s.cls();
        s.print(1, 0, "prepare thyself, then press any key", s.RED);
        s.refresh();
        s.inkey();

        set.add(new Hero());

        while (!set.isEndHuh()) {
            if (missileTimer == 0) {
                set.add(new Missile());
                missileTimer = 1 + rng.nextInt(4);
            }
            if (enemyTimer == 0) {
                set.add(new Enemy());
                enemyTimer = 1 + rng.nextInt(10);
            }
            s.cls();
            set.draw(s);
            s.print(0, 24, "SCORE: " + set.score(), s.YELLOW);
            s.refresh();

            CharKey k = s.inkey();
            set.react(k);
            set.collisions();

            s.refresh();
            missileTimer = missileTimer - 1;
            enemyTimer = enemyTimer - 1;
            set.tick();
        }
        
        resetPrompt(set, s);

        
    }
    
    static void resetPrompt(Elements set, ConsoleSystemInterface s) {
        s.cls();
        s.print(0, 0, "GAME OVER", s.WHITE);
        s.print(0, 1, "SCORE: " + set.score(), s.YELLOW);
        s.print(0, 10, "PRESS UP TO PLAY AGAIN, DOWN TO QUIT", s.BLUE);

        CharKey u = s.inkey();
        s.refresh();
        if (u.isUpArrow()) {
            ConsoleSystemInterface e
                    = new WSwingConsoleInterface("game1 by jah", true);
            List<Item> newItems = new ArrayList<>();
            Elements newSet = new Elements(newItems);
            game(e, newSet);
        } else if (u.isDownArrow()) {
            System.exit(0);
        } else {
            resetPrompt(set, s);
        }

    }

}

class Test {
    
        
    public Test() {}
    
    public static Integer test() {
        return test1()+test2()+test3()+test4()+test5()+test6()+test7()+test8()+test9()+test10()+test11();
    }
    
    public static Integer test1() {
        List<Item> testItems = new ArrayList<>();
        Elements testSet = new Elements(testItems);
        //testing collision in a set where there is a dead and a non-dead object, nothing should happen  
        testSet.add(new Dead(1));
        testSet.add(new Hero());
        Elements answer = testSet;
        testSet.collisions();
        if(answer==testSet) {
            return 0;
        } else {
            return 1;
        }
    }
    
    public static Integer test2() {
        List<Item> testItems = new ArrayList<>();
        Elements testSet = new Elements(testItems);
        //testing that only Dead(1) items increase score
        testSet.add(new Dead(2));
        testSet.add(new Dead(1));
        testSet.add(new Dead(1));
        testSet.add(new Hero());
        if(testSet.scoreInt()==2) {
            return 0;
        } else {
            return 1;
        }
    }
    
    public static Integer test3() {
        //checking Hero constructor initializes stuff right
        Hero test = new Hero();
        if(!(test.x>76)&&!(test.y>23)&&(test.dx==0)&&(test.dy==0)&&(!test.isDead)&&(test.hp==3)) {
            return 0;
        } else {
            return 1;
        }
    }
    
    public static Integer test4() {
        //tests end(), isEndHuh() in Hero class
        Hero test = new Hero(0, 0, 0, 0, 0);
        if(test.tick().isEndHuh()) {
            return 0;
        } else {
            return 1;
        }  
    }
    
    public static Integer test5() {
        //tests boundaries in Hero tick()
        Hero test = new Hero(77, 1, 23, 1, 3);
        Item answer = test.tick();
        if(answer.getX()==77&&answer.getY()==23) {
            return 0;
        } else {
            return 1;
        }
    }
    
    public static Integer test6() {
        //testing collisions among all types of items, along with type()
        Hero testHero = new Hero();
        Enemy testEnemy = new Enemy();
        Missile testMissile = new Missile();
        Dead testDead = new Dead(1);
        boolean preTest0 = testDead.type()==-1;
        boolean preTest1 = testHero.type()==0;
        boolean preTest2 = testEnemy.type()==1;
        boolean preTest3 = testMissile.type()==2;
        //coordinates do not matter in collision code, just testing if there is one
        //these are all possible collisions
        Item coll1 = testHero.collision(testEnemy);
        Item coll2 = testHero.collision(testMissile);
        Item coll3 = testEnemy.collision(testHero);
        Item coll4 = testEnemy.collision(testEnemy);
        Item coll5 = testEnemy.collision(testMissile);
        Item coll6 = testMissile.collision(testHero);
        Item coll7 = testMissile.collision(testEnemy);
        Item coll8 = testMissile.collision(testMissile);
        boolean test1 = coll1==testHero;
        boolean test2 = coll2.getX()==testHero.getX()&&coll2.getY()==testHero.getY();
        boolean test3 = coll3.type()==-1;
        boolean test4 = coll4==testEnemy;
        boolean test5 = coll5.type()==-1;
        boolean test6 = coll6.type()==-1;
        boolean test7 = coll7.type()==-1;
        boolean test8 = coll8.type()==-1;
        if(preTest0&&preTest1&&preTest2&&preTest3&&test1&&test2&&test3&&test4&&test5&&test6&&test7&&test8) {
            return 0;
        } else {
            return 1;
        }    
    }
    
    public static Integer test7() {
        //checking Enemy constructor initializes stuff right
        Enemy test = new Enemy();
        if(!(test.x>77)&&!(test.y>23)&&(test.dx==0)&&(test.dy==0)) {
            return 0;
        } else {
            return 1;
        }
    }
    
    public static Integer test8() {
        //tests boundaries in Enemy tick()
        //last 2 arguments for constructed test Enemy do not matter in this test
        Enemy test = new Enemy(77, 1, 24, 1, 0, 1);
        Item answer = test.tick();
        if(answer.getX()==77&&answer.getY()==24) {
            return 0;
        } else {
            return 1;
        }
    }
    
    public static Integer test9() {
        //tests if new direction and path are given if Enemy pathlength reaches 0
        CharKey k = new CharKey(0);
        Enemy test = new Enemy(0, 0, 0, 0, -1, 0);
        test.react(k);
        if(test.direction>=0&&test.pathLength>=8) {
            return 0;
        } else {
            return 1;
        }
    }
    
    public static Integer test10() {
        //tests that missile constructor initializes stuff right
        Missile test = new Missile();
        if(test.dx==0&&test.dy==0&&(test.dir==0||test.dir==1)&&(test.getX()==76||test.getX()==0)) {
            return 0;
        } else {
            return 1;
        }
    }
    
    public static Integer test11() {
        //tests that Missile turns into Dead if it leaves boundaries
        //this is a missile moving left that is about to hit the left boundary
        Missile test = new Missile(0, 4, 0, 0, 1);
        if(test.tick().type()==-1) {
            return 0;
        } else {
            return 1;
        }
    }   
}

interface Item {

    public Item tick();
    public Item react(CharKey k);
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

    List<Item> elements = new ArrayList<>();

    public Elements(List<Item> elements) {
        this.elements = elements;
    }

    public Elements add(Item elt) {
        Elements answer = this;
        answer.elements.add(elt);
        return answer;
    }

    public void tick() {
        for (int i = 0; i < elements.size(); i++) {
            Elements answer = this;
            answer.elements.set(i, elements.get(i).tick());
        }
    }

    public void react(CharKey k) {
        for (int i = 0; i < elements.size(); i++) {
            Elements answer = this;
            answer.elements.set(i, elements.get(i).react(k));
        }
    }

    public void draw(ConsoleSystemInterface s) {
        for (int i = 0; i < elements.size(); i++) {
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
                if (!(itemI.type() == -1 || itemJ.type() == -1)) {
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
        for (int i = 0; i < this.elements.size(); i++) {
            if (this.elements.get(i).isEndHuh()) {
                answer = true;
            }
        }
        return answer;
    }
    
    public Integer scoreInt() {
        Integer answer = 0;
        for (int i = 0; i < this.elements.size(); i++) {
            if (this.elements.get(i).type() == -1) {
                if (this.elements.get(i).wasEnemy()) {
                    answer += 1;
                }
            }
        }
        return answer;
    }

    public String score() {
        return this.scoreInt().toString();
    }

}

class Dead implements Item {

    //0 is Hero
    //1 is Enemy
    //2 is Missile
    //only intended to be Enemies and Missiles because if a Hero dies, game is over
    int formerType;

    public Dead(int type) {this.formerType = type;}
    public Item tick() {return this;}
    public Item react(CharKey k) {return this;}
    public void draw(ConsoleSystemInterface s) {}
    public int getX() {return -1;}
    public int getY() {return -1;}
    public Rectangle bounds() {return new Rectangle();}
    public int type() {return -1;}
    public Item collision(Item p) {return this;}
    public boolean isEndHuh() {return false;}
    public Item end() {return this;}
    public boolean wasEnemy() {return this.formerType == 1;}

}

class Hero implements Item {

    int x;
    int y;
    int dx;
    int dy;
    int hp;
    boolean isDead;

    static int MAXH = 23;
    static int MAXW = 77;

    public Hero() {
        Random rng = new Random();
        this.x = rng.nextInt(MAXW);
        this.dx = 0;
        this.y = rng.nextInt(MAXH);
        this.dy = 0;
        this.hp = 3;
        this.isDead = false;

    }

    public Hero(int x, int dx, int y, int dy, int hp) {
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

    public Item react(CharKey k) {
        if (k.isDownArrow()) {
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
        String answer = "";
        switch (hp) {
            case 2:
                answer += "!!";
                break;
            case 3:
                answer += "!!!";
                break;
            default:
                answer += " ! ";
                break;
        }
        s.print(x, y + 0, "SJW", s.CYAN);
        s.print(x, y + 1, answer, s.CYAN);
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

    public int type() {
        return 0;
    }

    public Item collision(Item p) {
        int type = p.type();
        Item answer = this;
        switch (type) {
            case 2:
                answer = new Hero(this.x, 0, this.y, 0, this.hp - 1);
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

    static int MAXH = 24;
    static int MAXW = 77;

    public Enemy() {
        Random rng = new Random();
        this.x = rng.nextInt(MAXW);
        this.dx = 0;
        this.y = rng.nextInt(MAXH);
        this.dy = 0;
    }

    public Enemy(int x, int dx, int y, int dy, int dir, int path) {
        this.x = x;
        this.dx = dx;
        this.y = y;
        this.dy = dy;
        this.direction = dir;
        this.pathLength = path;
    }

    public Item tick() {
        int nx = x + dx;
        int ny = y + dy;
        if (nx < 0 && ny < 0) {
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
        int path = 8 + rng.nextInt(12);
        Enemy answer = this;
        if (this.pathLength <= 0 || this.direction == -1) {
            answer.pathLength = path;
            answer.direction = dir;
        }
        switch (direction) {
            case 0:
                answer = new Enemy(x, 1, y, 0, answer.direction, answer.pathLength - 1);
                break;
            case 1:
                answer = new Enemy(x, -1, y, 0, answer.direction, answer.pathLength - 1);
                break;
            case 2:
                answer = new Enemy(x, 0, y, -1, answer.direction, answer.pathLength - 1);
                break;
            case 3:
                answer = new Enemy(x, 0, y, 1, answer.direction, answer.pathLength - 1);
                break;
        }
        return answer;
    }

    public void draw(ConsoleSystemInterface s) {
        s.print(x, y + 0, "MRA", s.WHITE);

    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public Rectangle bounds() {
        return new Rectangle(x, y, 3, 1);
    }

    public Item collision(Item p) {
        int type = p.type();
        Item answer = this;
        switch (type) {
            case 0:
                answer = new Dead(1);
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
        switch (newDir) {
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

    public Missile(int x, int dx, int y, int dy, int dir) {
        this.x = x;
        this.dx = dx;
        this.y = y;
        this.dy = dy;
        this.dir = dir;
    }

    public Item tick() {
        int nx = x;
        switch (dir) {
            case 0:
                nx = nx + dx;
                break;
            case 1:
                nx = nx - dx;
                break;
        }
        Item answer = new Missile(nx, 0, y, 0, dir);
        if (nx < 0 || nx > MAXW) {
            answer = new Dead(2);
        }
        return answer;
    }

    public Item react(CharKey k) {
        Missile answer = this;
        answer.dx = 4;
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
