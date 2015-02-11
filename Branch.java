package data1;

public class Branch implements FiniteSet {
    
    public int key;
    public FiniteSet left;
    public FiniteSet right;
    
    public Branch(FiniteSet left, int key, FiniteSet right) {
        this.key = key;
        this.left = left;
        this.right = right;
    }
    
    public int cardinality() {
        return 1+left.cardinality()+right.cardinality();
    }
    
    public boolean isEmptyHuh() {
        return false;
    }
    
    public boolean member(int elt) {
        boolean answer = false;
        if(key == elt) {
            answer = true;
        } else if(key > elt) {
            answer = left.member(elt);
        } else if(key < elt) {
            answer = right.member(elt);
        }
        return answer;
    }
    
    public FiniteSet add(int elt) {
        FiniteSet answer = this;       
        if(elt < key) {
            answer = left.add(elt).union(right);
        } else if(elt > key) {
            answer = right.add(elt).union(left);
        }
        return answer;
    }
    
    public FiniteSet remove(int elt) {
        FiniteSet answer = this;
        if(elt < key) {
            answer = right.union(left.remove(elt));
        } else if(elt > key) {
            answer = left.union(right.remove(elt));
        } else {
            answer = left.union(right);
        }
        return answer;
    }
    
    public FiniteSet union(FiniteSet set) {
        return set.union(left).union(right).add(key);
    }
    
    public FiniteSet inter(FiniteSet set) {
        if(set.member(key)) {
            return new Branch(left.inter(set), key, right.inter(set));
        } else {
            return left.union(right).inter(set);
        }
    }
    
    public FiniteSet diff(FiniteSet set) {
        if(set.member(key)) {
            return this.diff(set.remove(key));
        } else {
            return new Branch(left.diff(set), key, right.diff(set));
        }
    }
    
    public boolean equal(FiniteSet set) {
        return this.subset(set)&&set.subset(this);

    }
    
    public boolean subset(FiniteSet set) {
        if(set.member(key)) {
            return (this.left.subset(set)||this.left.isEmptyHuh()) &&
                    (this.right.subset(set)||this.right.isEmptyHuh());
        } else {return false;}
    }
            
}
