package data2;

public class Branch<D extends Comparable> extends MultiSet<D> {
    
    public D key;
    public MultiSet left;
    public MultiSet right;
    
    public Branch(D key) {
        this.key = key;
        this.count = 1;
        this.left = new Leaf();
        this.right = new Leaf();
        this.height = 1;
    }
    
    public Branch(D key, int count) {
        this.key = key;
        this.count = count;
        this.left = new Leaf();
        this.right = new Leaf();
        this.height = 1;
    }
    

    public Branch(MultiSet<D> left, D key, MultiSet<D> right) {
        this.key = key;
        this.count = 1;
        this.left = left;
        this.right = right;
        this.height = 1+left.height()+right.height();
    }
    
    public Branch(MultiSet<D> left, D key, int count, MultiSet<D> right) {
        this.key = key;
        this.count = count;
        this.left = left;
        this.right = right;
        this.height = 1+left.height()+right.height();
    }
    
    public int multiplicity(D elt) {
        if(key == elt) {
            return this.count;
        } else if(key.compareTo(elt) > 0) {
            return left.multiplicity(elt);
        } else {
            return right.multiplicity(elt);
        }
    }
    
    public int height() {
        return height;
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
        Branch<D> answer = this;
        if(elt.compareTo(key) < 0) {
            answer = new Branch(left.add(elt), key, count, right);
        } else if(elt.compareTo(key) > 0) {
            answer = new Branch(left, key, count, right.add(elt));
        } else {
            answer = new Branch(left, key, count+1, right);
        }
        return answer.balance();
    }
    
    public MultiSet<D> add(D elt, int count) {
        MultiSet<D> answer = this;
        for(int i=0; i<count; i++) {
            answer = answer.add(elt);
        }
        return answer;
    }
    
    
    // returns the set without key if key matches elt, otherwise searches down
    //  branches until match is ultimately found or not found
    public MultiSet<D> remove(D elt) {
        Branch<D> answer = this;
        if(elt==key) {
            if(count>1) {
                answer = new Branch(left, key, count-1, right);
            } else {
                return left.union(right);
            }
        } else if(elt.compareTo(key) > 0) {
            answer = new Branch(left, key, count, right.remove(elt));
        } else {
            answer = new Branch(left.remove(elt), key, count, right);
        }
        return answer.balance();
    }

    public MultiSet<D> remove(D elt, int count) {
        MultiSet<D> answer = this;
        int numRemoves = answer.count;
        if (count >= numRemoves) {
            answer = answer.removeAll(elt);
        } else {
            for (int i = 0; i < count; i++) {
                answer = answer.remove(elt);
            }
        }
        return answer;
    }
    
    public MultiSet<D> removeAll(D elt) {
        MultiSet<D> answer = this;
        int numRemoves = answer.count;
        for(int i=0; i<numRemoves; i++) {
            answer = answer.remove(elt);
        }
        return answer;
    }
    
    // recursively combines sets with both children of the branch and the key
    public MultiSet<D> union(MultiSet<D> set) {
        // probably need to re-do entirely
        MultiSet<D> answer = set.union(left).union(right);
        for(int i=0; i<count; i++) {
            answer = answer.add(key);
        }
        return answer;
    }
    
    // includes the key if it is in the set, excludes it otherwise
    public MultiSet<D> inter(MultiSet<D> set) {
        if(set.member(key)) {
            return new Branch(left.inter(set), key, min(this.count, set.count), right.inter(set));
        } else {
            return left.union(right).inter(set);
        }
    }
    
    //helper method for intersection
    private static int min(int a, int b) {
        if(a>b) {
            return b;
        } else {
            return a;
        }
    }
    
    // removes key from both sets and recursively finds the difference between those
    public MultiSet diff(MultiSet<D> set) {
        return this.remove(key, count).diff(set.remove(key, count));
    }
    
    // sets are equal iff they are subsets of each other
    public boolean equal(MultiSet<D> set) {
        return this.subset(set)&&set.subset(this);
    }
    
    // if key is a member of the set, return whether its children are subsets of the set
    public boolean subset(MultiSet<D> set) {
        if(set.member(key) && this.count <= set.count) {
            return this.left.subset(set) && this.right.subset(set);
        } else {return false;}
    }
    
    // 0: balanced
    // 1: left => 2
    // 2: right => 2
    public int balanceCase() {
        int diff = left.height()-right.height();
        if(diff>=2) {
            return 1;
        } else if(diff<=-2) {
            return 2;
        } else {
            return 0;
        }
    }
    
    public Branch<D> balance() {
        Branch<D> answer = this;
        switch (this.balanceCase()) {
            default:
                return answer;
            case 1:
                Branch<D> tempLeft = (Branch) left;
                // left-left
                if ((!tempLeft.left.isEmptyHuh()) && tempLeft.right.isEmptyHuh()) {
                    answer = answer.rotateRight();
                // left-right
                } else if ((!tempLeft.right.isEmptyHuh()) && tempLeft.left.isEmptyHuh()) {
                    answer = new Branch(left.rotateLeft(), key, count, right.balance());
                }
                return answer.balance();
            case 2:
                Branch<D> tempRight = (Branch) right;
                // right-right
                if ((!tempRight.right.isEmptyHuh()) && tempRight.left.isEmptyHuh()) {
                    answer = answer.rotateLeft();
                // right-left
                } else if ((!tempRight.left.isEmptyHuh()) && tempRight.right.isEmptyHuh()) {
                    answer = new Branch(left.balance(), key, count, right.rotateRight());
                }
                return answer.balance();
        }
    }
    
    public Branch<D> rotateRight() {
        Branch<D> tempLeft = (Branch)left;
        return new Branch(tempLeft.left, tempLeft.key, tempLeft.count, new Branch(tempLeft.right, key, count, right));
    }
    
    public Branch<D> rotateLeft() {
        Branch<D> tempRight = (Branch)right;
        return new Branch(new Branch(left, key, count, tempRight.left), tempRight.key, tempRight.count, tempRight.right);
    }
    
    public Sequence<D> seq() {
        return new SeqBranch(key, count, new SeqCat(left.seq(), right.seq()));
    }
    
    public String toString() {
        return left.toString() + " " + key.toString()
                + " " + right.toString();
    }
    
    
    
}
