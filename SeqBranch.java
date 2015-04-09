package data2;

public class SeqBranch<D extends Comparable> implements Sequence<D>, Sequenced<D> {

    public D key;
    public int count;
    public Sequence<D> next;
    
    public SeqBranch(D key, int count, Sequence<D> next) {
        this.key = key;
        this.count = count;
        this.next = next;
    }
    
    public D here() {
        return key;
    }

    public Sequence<D> next() {
        if(count==1) {
            return next;
        } else {
            return new SeqBranch(key, count-1, next);
        }
    }

    public boolean hasNext() {
        return true;
    }

    public Sequence<D> seq() {
        return this;
    }
    
    
    
}
