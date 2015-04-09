package data2;

public class SeqLeaf<D extends Comparable> implements Sequence<D>, Sequenced<D> {
    
    public SeqLeaf() {}

    public D here() {
        throw new RuntimeException("Nothing here!");
    }

    public Sequence<D> next() {
        return this;
    }

    public boolean hasNext() {
        return false;
    }

    public Sequence<D> seq() {
        return this;
    }
    
}
