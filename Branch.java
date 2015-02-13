package data1;
import java.util.Random;

public class Branch implements FiniteSet {
    
    public int key;
    public FiniteSet left;
    public FiniteSet right;
    
    // initializes key, left, right to input variables
    public Branch(FiniteSet left, int key, FiniteSet right) {
        this.key = key;
        this.left = left;
        this.right = right;
    }
    
    // cardinality counts the key plus the number of other keys in the set
    public int cardinality() {
        return 1+left.cardinality()+right.cardinality();
    }
    
    // a branch is never empty
    public boolean isEmptyHuh() {
        return false;
    }
    
    // searches from key outward until key equals elt, returns false otherwise
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
    
    // places the new key in the correct spot in the tree, returning the original
    //  if elt matches any key during recursion
    public FiniteSet add(int elt) {      
        if(elt < key) {
            return new Branch(left.add(elt), key, right);
        } else if(elt > key) {
            return new Branch(left, key, right.add(elt));
        } else {return this;}
    }
    
    
    // returns the set without key if key matches elt, otherwise searches down
    //  branches until match is ultimately found or not found
    public FiniteSet remove(int elt) {
        if(elt==key) {
            return left.union(right);
        } else if(elt > key) {
            return new Branch(left, key, right.remove(elt));
        } else {
            return new Branch(left.remove(elt), key, right);
        }
    }
    
    // recursively combines sets with both children of the branch and the key
    public FiniteSet union(FiniteSet set) {
        return set.union(left).union(right).add(key);
    }
    
    // includes the key if it is in the set, excludes it otherwise
    public FiniteSet inter(FiniteSet set) {
        if(set.member(key)) {
            return new Branch(left.inter(set), key, right.inter(set));
        } else {
            return left.union(right).inter(set);
        }
    }
    
    // removes key from both sets and recursively finds the difference between those
    public FiniteSet diff(FiniteSet set) {
        return this.remove(key).diff(set.remove(key));
    }
    
    // sets are equal iff they are subsets of each other
    public boolean equal(FiniteSet set) {
        return this.subset(set)&&set.subset(this);

    }
    
    // if key is a member of the set, return whether its children are subsets of the set
    public boolean subset(FiniteSet set) {
        if(set.member(key)) {
            return this.left.subset(set) && this.right.subset(set);
        } else {return false;}
    }
    
    // toString is used for testing
    public String toString() {
        return left.toString()+" "+Integer.toString(key)
                +" "+right.toString();
    }
    
    // made static method to generate random branch of specified size for tests
    public static FiniteSet randBranch(int size) {
        FiniteSet answer = new Leaf();
        Random rng = new Random();
        for(int i=0; i<size; i++){
            answer = answer.add(rng.nextInt());
        }
        return answer;
    }
    
    // member (add t x) y = true <-> x = y \/ member t y = true
    public boolean prop1Tester(int x, int y) {
        return this.add(x).member(y)==
                ((x==y)||this.member(y));
    }
    
    // member (union s s') x = true <-> member s x = true \/ member s' x = true
    public boolean prop2Tester(FiniteSet set, int x) {
        return (set.union(this).member(x))==
                (this.member(x) || set.member(x));
    }

    
            
}
