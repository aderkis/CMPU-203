package game2;

import java.util.ArrayList;
import java.util.List;

public class Everyone {
    
    public List<Rapper> rappers = new ArrayList();
    public Rapper player;
    public boolean battle;
    
    public Everyone(Rapper player, List<Rapper> rappers) {
        this.player = player;
        this.rappers = rappers;
        this.battle = false;
    }
    
    public Everyone add(Rapper r) {
        Everyone answer = this;
        answer.rappers.add(r);
        return answer;
    }
    
    public Everyone remove(Rapper r) {
        Everyone answer = this;
        answer.rappers.remove(r);
        return answer;
    }
    
    public Everyone move(String key) {
        Everyone answer = this;
        answer.player = answer.player.move(key);
        for (int i = 0; i < rappers.size(); i++) {
            answer.rappers.set(i, rappers.get(i).move(key));

        }
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
