package game2;

import java.util.List;

public class AttackList {
    
    public List<Attack> attacks;
    public int selectedIndex;
    public int lastIndex;
    
    public AttackList(List<Attack> attacks, int selectedIndex) {
        this.attacks = attacks;
        this.selectedIndex = selectedIndex;
        this.lastIndex = selectedIndex;
        for(int i=0; i<attacks.size(); i++) {
            if(i==selectedIndex) {
                this.attacks.get(i).selected = true;
            } else {
                this.attacks.get(i).selected = false;
            }
        }          
    }
    
    public AttackList up() {
        AttackList answer = this;
        if (selectedIndex > 0) {
            answer = new AttackList(this.attacks, this.selectedIndex - 1);
        }
        return answer;
    }
    
    public AttackList down() {
        AttackList answer = this;
        if (selectedIndex <= attacks.size()) {
            answer = new AttackList(this.attacks, this.selectedIndex + 1);
        }
        return answer;
    }
    
    
    
}
