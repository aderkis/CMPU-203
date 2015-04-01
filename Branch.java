package data2;

public class Branch<D extends Comparable> extends MultiSet<D> {
    
    public D key;
    public int count;
    public MultiSet left;
    public MultiSet right;
    
    // initializes key, left, right to input variables
    public Branch(MultiSet<D> left, D key, MultiSet<D> right) {
        this.key = key;
        this.count = 1;
        this.left = left;
        this.right = right;
    }
    
    // cardinality counts the key plus the number of other keys in the set
    public int cardinality() {
        return this.count+left.cardinality()+right.cardinality();
    }
    
    // a branch is never empty
    public boolean isEmptyHuh() {
        return false;
    }
    
    // searches from key outward until key equals elt, returns false otherwise
    public boolean member(D elt) {
        boolean answer = false;
        if(key == elt) {
            answer = true;
        } else if(key.compareTo(elt) > 0) {
            answer = left.member(elt);
        } else if(key.compareTo(elt) < 0) {
            answer = right.member(elt);
        }
        return answer;
    }
    
    // places the new key in the correct spot in the tree, returning the original
    //  if elt matches any key during recursion
    public MultiSet<D> add(D elt) {      
        if(elt.compareTo(key) < 0) {
            return new Branch(left.add(elt), key, right);
        } else if(elt.compareTo(key) > 0) {
            return new Branch(left, key, right.add(elt));
        } else {return this;}
    }
    
    
    // returns the set without key if key matches elt, otherwise searches down
    //  branches until match is ultimately found or not found
    public MultiSet<D> remove(D elt) {
        if(elt==key) {
            return left.union(right);
        } else if(elt.compareTo(key) > 0) {
            return new Branch(left, key, right.remove(elt));
        } else {
            return new Branch(left.remove(elt), key, right);
        }
    }
    
    // recursively combines sets with both children of the branch and the key
    public MultiSet<D> union(MultiSet<D> set) {
        return set.union(left).union(right).add(key);
    }
    
    // includes the key if it is in the set, excludes it otherwise
    public MultiSet<D> inter(MultiSet<D> set) {
        if(set.member(key)) {
            return new Branch(left.inter(set), key, right.inter(set));
        } else {
            return left.union(right).inter(set);
        }
    }
    
    // removes key from both sets and recursively finds the difference between those
    public MultiSet diff(MultiSet<D> set) {
        return this.remove(key).diff(set.remove(key));
    }
    
    // sets are equal iff they are subsets of each other
    public boolean equal(MultiSet<D> set) {
        return this.subset(set)&&set.subset(this);

    }
    
    // if key is a member of the set, return whether its children are subsets of the set
    public boolean subset(MultiSet<D> set) {
        if(set.member(key)) {
            return this.left.subset(set) && this.right.subset(set);
        } else {return false;}
    }
}
