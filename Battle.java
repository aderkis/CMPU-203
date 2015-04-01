package game2;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javalib.colors.*;
import javalib.funworld.*;
import javalib.worldimages.OverlayImages;
import javalib.worldimages.Posn;
import javalib.worldimages.TextImage;
import javalib.worldimages.WorldImage;

public class Battle extends World {
    
    public Player player;
    public Enemy enemy;
    public Integer playerHP;
    public Integer enemyHP;
    public int numLyricalAttacks;
    public int numPresenceAttacks;
    public boolean isPlayerTurn;
    public boolean endBattle;
    public List<Attack> validPlayerAttacks;
    public List<Attack> validEnemyAttacks;
    public AttackList menu;
    public Random rng = new Random();
            
    public static Attack skip = new Attack("Skip turn", 0, 0, 0);
    
    //lyricism attacks
    public static List<Attack> lyricAttacks = new ArrayList<>();
    public static Attack endOfLineRhyme = new Attack("End-of-line rhyme", 0, 1, 5);
    public static Attack simile = new Attack("Simile", 0, 5, 8);
    public static Attack easyReference = new Attack("Popular reference", 0, 10, 10);
    public static Attack metaphor = new Attack("Metaphor", 0, 20, 15);
    public static Attack internalRhyme = new Attack("Internal rhyme", 0, 30, 17);
    public static Attack doubleTime = new Attack("Double time flow", 0, 40, 20);
    public static Attack obscureReference = new Attack("Obscure reference", 0, 50, 25);
    
    //presence attacks
    public static List<Attack> presenceAttacks = new ArrayList<>();
    public static Attack motherInsult = new Attack("Mother insult", 1, 1, 5);
    public static Attack shoutout = new Attack("Shoutout", 1, 5, 8);
    public static Attack easyJoke = new Attack("Easy joke", 1, 10, 10);
    public static Attack backgroundInsult = new Attack("Background insult", 1, 20, 15);
    public static Attack funnyJoke = new Attack("Funny joke", 1, 30, 17);
    public static Attack expose = new Attack("Expose flaw", 1, 40, 20);
    public static Attack getPersonal = new Attack("Get personal", 1, 50, 25);
    
    public Battle(Player player, Enemy enemy) {
        this.player = player;
        this.player.mana = player.flow;
        this.enemy = enemy;
        this.enemy.mana = enemy.flow*5;
        this.playerHP = player.hype*5;
        this.enemyHP = 50;
        this.numLyricalAttacks = 0;
        this.numPresenceAttacks = 0;
        this.isPlayerTurn = true;
        this.endBattle = false;
        lyricAttacks.add(skip);
        lyricAttacks.add(endOfLineRhyme);
        lyricAttacks.add(simile);
        lyricAttacks.add(easyReference);
        lyricAttacks.add(metaphor);
        lyricAttacks.add(internalRhyme);
        lyricAttacks.add(doubleTime);
        lyricAttacks.add(obscureReference);
        presenceAttacks.add(motherInsult);
        presenceAttacks.add(shoutout);
        presenceAttacks.add(easyJoke);
        presenceAttacks.add(backgroundInsult);
        presenceAttacks.add(funnyJoke);
        presenceAttacks.add(expose);
        presenceAttacks.add(getPersonal);

        this.validPlayerAttacks = validAttacks(player);
        this.validEnemyAttacks = validAttacks(enemy);
        this.menu = new AttackList(this.validPlayerAttacks, 0);
    }
    
    public World onTick() {
        Battle answer = this;
        answer.validEnemyAttacks = validAttacks(answer.enemy);
        List<Attack> valid = answer.validEnemyAttacks;
        answer.validPlayerAttacks = validAttacks(answer.player);
        answer.menu = new AttackList(answer.validPlayerAttacks, answer.menu.selectedIndex);
        if (!answer.isOver()) {
            if (!answer.isPlayerTurn) {
                if (valid.size() <= 1) {
                    answer = answer.enemyAttack(answer.player, skip);
                } else {
                    Attack randAttack = valid.get(rng.nextInt(valid.size()));
                    answer = answer.enemyAttack(answer.player, randAttack);
                }
            }
        } else if (answer.playerWonHuh()) {
            if (numLyricalAttacks >= numPresenceAttacks) {
                answer.player.win(answer.xp(), 0);
                answer.endBattle = true;
            } else {
                answer.player.win(answer.xp(), 1);
                answer.endBattle = true;
            }
        }
        return answer;
    }

    public World onKeyEvent(String key) {
        Battle answer = this;
        if (!answer.endBattle) {
            if (answer.isPlayerTurn) {          
                int index = answer.menu.selectedIndex;
                List<Attack> valid = answer.menu.attacks;
                if (valid.isEmpty()) {
                    answer = answer.nextTurn();
                }
                switch (key) {
                    case "l":
                        if (index < valid.size() && index >= 0) {
                            answer = answer.playerAttack(answer.enemy, valid.get(index));
                        }   break;
                    case "up":
                        answer.menu = answer.menu.up();
                        break;
                    case "down":
                        answer.menu = answer.menu.down();
                        break;
                    case "s":
                        System.out.println("Debug button pressed!");
                        break;
                }
            }
        }

        return answer;
    }
    
    public WorldImage makeImage() {
        WorldImage answer = new OverlayImages(player.draw(), enemy.draw());
        answer = new OverlayImages(answer, new TextImage(new Posn(50, 500), "Your HP: " + playerHP.toString(), new Black()));
        answer = new OverlayImages(answer, new TextImage(new Posn(50, 515), "Your mana: " + player.mana.toString(), new Black()));
        answer = new OverlayImages(answer, new TextImage(new Posn(50, 530), "His HP: " + enemyHP.toString(), new Black()));
        answer = new OverlayImages(answer, new TextImage(new Posn(50, 545), "His mana: " + enemy.mana.toString(), new Black()));
        if(isPlayerTurn) {
            answer = new OverlayImages(answer, new TextImage(new Posn(50, 560), "Your turn", new Black()));
            answer = new OverlayImages(answer, new TextImage(new Posn(500, 560), "Attacks:", new Black()));
            for(int i=0; i<this.menu.attacks.size(); i++) {
                Attack att = this.menu.attacks.get(i);
                answer = new OverlayImages(answer, att.draw(new Posn(500, 575+15*i)));
            }
        } else {
            answer = new OverlayImages(answer, new TextImage(new Posn(50, 560), "His turn", new Black()));
        }
        return answer;
    }
    
    public World onMouseClicked(Posn p) {return this;}
    
    public Battle playerAttack(Rapper target, Attack move) {
        Battle answer = this;
        answer.player.mana -= move.requiredLevel;
        answer.enemyHP -= move.damage;
        if(move.type==0) {
            answer.numLyricalAttacks += 1;
        } else {
            answer.numPresenceAttacks += 1;
        }
        answer.enemy.mana += 1;
        return answer.nextTurn();
    }
    
    public Battle enemyAttack(Rapper target, Attack move) {
        Battle answer = this;
        answer.enemy.mana -= move.requiredLevel;
        answer.playerHP -= move.damage;
        answer.player.mana += 1;
        return answer.nextTurn();
    }
    
    public boolean isOver() {
        return (playerHP<=0||enemyHP<=0);
    }
    
    public Battle nextTurn() {
        Battle answer = this;
        answer.isPlayerTurn = !isPlayerTurn;
        return answer;
    }
    
    public int xp() {
        return (enemy.flow+enemy.lyrics+enemy.presence)/3;
    }
    
    public static List<Attack> validAttacks(Rapper r) {
        List<Attack> answer = new ArrayList<>();
        int lyrics = r.lyrics;
        int presence = r.presence;
        Integer mana = r.mana;
        for(Attack att : lyricAttacks) {
            if(lyrics>=att.requiredLevel && mana >= att.requiredLevel) {
                answer.add(att);
            }
        }
        for(Attack att : presenceAttacks) {
            if(presence>=att.requiredLevel && mana >= att.requiredLevel) {
                answer.add(att);
            }
        }
        return answer;
    }
    
    public boolean playerWonHuh() {
        if(this.isOver()) {
            return enemyHP<=0;
        } else {
            return false;
        }
    }
    
    
    
    
}
