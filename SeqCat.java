package data2;

public class SeqCat<D extends Comparable> implements Sequence<D>, Sequenced<D> {
    
    public Sequence<D> left;
    public Sequence<D> right;
    
    public SeqCat(Sequence<D> left, Sequence<D> right) {
        this.left = left;
        this.right = right;
    }

    public D here() {
        if(left.hasNext()) {
            return left.here();
        } else {
            return right.here();
        }
    }

    public Sequence<D> next() {
        if(left.hasNext()) {
            return new SeqCat(left.next(), right);
        } else {
            return right.next();
        }
    }

    public boolean hasNext() {
        return left.hasNext()||right.hasNext();
    }

    public Sequence<D> seq() {
        return this;
    }
    
}
