package game2;

import java.util.ArrayList;
import java.util.List;

public class Everyone {
    
    public List<Enemy> rappers = new ArrayList();
    public Player player;
    public boolean battle;
    
    public Everyone(Player player, List<Enemy> rappers) {
        this.player = player;
        this.rappers = rappers;
        this.battle = false;
    }
    
    public Everyone add(Enemy r) {
        Everyone answer = this;
        answer.rappers.add(r);
        return answer;
    }
    
    public Everyone remove(Enemy r) {
        Everyone answer = this;
        answer.rappers.remove(r);
        return answer;
    }

    public Everyone collisions() {
        Everyone answer = this;
        for(Rapper r : rappers) {
            if(player.dist(r)<=40) {
                answer.battle = true;
            }
        }
        return answer;
    }
}
