package game2;

import java.util.ArrayList;
import java.util.List;

public class Everyone {
    
    public List<Rapper> rappers = new ArrayList();
    
    public Everyone(List<Rapper> rappers) {
        this.rappers = rappers;
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
        for(int i=0; i<rappers.size(); i++) {
            answer.rappers.set(i, rappers.get(i).move(key));
        }
        return answer;
    }
}
