package data2;

public abstract class MultiSet<D extends Comparable> implements Sequenced<D> {
    
    /**
     * The multiplicity of the key is stored in the MultiSet class to be
     * consistent with a base case of Leaf having count=0.
     */    
    public int count;

    /**
     * Returns the multiplicity of the current key.
     * 
     * @return an int equal to the multiplicity of the key 
     */
    public int multiplicity() {
        return this.count;
    }
    
    /**
     * Returns the multiplicity of elt, with 0 returned in the case that
     * elt is not in the set.
     * 
     * @param elt: a comparable data type to be searched for in the set
     * @return an int equal to the multiplicity of elt
     */
    public abstract int multiplicity(D elt);
    
    /**
     * Returns the number of objects in the set, including repeated
     * elements.
     * 
     * @return an int equal to the number of elements in the set
     */
    public abstract int cardinality();
    
    /**
     * Returns the length of the longest possible path in the set.
     * 
     * @return an int equal to the longest path length in the set
     */
    public abstract int height();
    
    /**
     * Returns whether or not the set contains any elements.
     * 
     * @return a boolean that is true only when the set contains no elements 
     */
    public abstract boolean isEmptyHuh();
    
    /**
     * Returns whether or not elt is currently in the set.
     * 
     * @param elt: a comparable data type to be searched for in the set
     * @return a boolean that is true only when the multiplicity of
     *         elt in the set is greater than 0
     */
    public abstract boolean member(D elt);
    
    /**
     * Returns the current balanced set with elt added in the proper part of the
     * tree; the multiplicity of elt is increased by 1 if elt is already in
     * the set.
     * 
     * @param elt: a comparable object to be added to the set
     * @return a MultiSet equal to the original with elt added once
     */
    public abstract MultiSet<D> add(D elt);
    
    /**
     * Returns the current balanced set with elt added in the proper part of the
     * tree; the multiplicity of elt is increased by count if elt is already in
     * the set.
     * 
     * @param elt: a comparable object to be added to the set
     * @param count: an int equal to the number of times elt is to be added
     * @return a MultiSet equal to the original with elt added count times
     */
    public abstract MultiSet<D> add(D elt, int count);
    
    /**
     * Returns the current balanced set with elt removed once; the multiplicity
     * of elt is decreased by 1 if elt is in the original set more than once.
     * 
     * @param elt: a comparable object to be removed from the set
     * @return a MultiSet equal to the original with elt removed once
     */
    public abstract MultiSet<D> remove(D elt);
    
    /**
     * Returns the current balanced set with elt removed count times; elt
     * will remain at zero multiplicity if count exceeds the number
     * of occurrences of elt in the set.
     * 
     * @param elt: a comparable object to be removed from the set
     * @param count: an int equal to the number of times elt is to be removed
     * @return a MultiSet equal to the original with elt removed count times
     */
    public abstract MultiSet<D> remove(D elt, int count);
    
    /**
     * Returns the current balanced set with all occurrences of elt removed.
     * 
     * @param elt: a comparable object to be removed from the set
     * @return a MultiSet equal to the original with no occurrences of elt
     */
    public abstract MultiSet<D> removeAll(D elt);
    
    /**
     * Returns the union of the original set and the input set, which is
     * equal to the MultiSet of all elements that are in either the original
     * set or the input set. The multiplicity of an object that is a
     * member of both sets is equal to the sum of the multiplicity of the
     * object in each set.
     * 
     * @param set: a MultiSet to be united with the original set
     * @return a MultiSet equal to the MultiSet union of the original set and
     *         the input set
     */
    public abstract MultiSet<D> union(MultiSet<D> set);
    
    /**
     * Returns the intersection of the original set and the input set, which
     * is equal to the MultiSet containing all elements that are a member of
     * both sets. The multiplicity of an object that is a member of both sets is
     * equal to the lower of the multiplicities of the objects in each set.
     * 
     * @param set: a MultiSet to be intersected with the original set
     * @return a MultiSet equal to the MultiSet intersection of the original
     *         set and the input set
     */
    public abstract MultiSet<D> inter(MultiSet<D> set);
    
    /**
     * Returns the set of objects in the input set that are not a member of
     * the original set. Objects that occur in the original set fewer than or
     * equal times to the input set are removed from the new set. Otherwise,
     * the multiplicity of an object is the difference between the two
     * in the sets.
     * 
     * @param set: a MultiSet which will have intersecting elements with the
     *             original set removed
     * @return a MultiSet equal to the set difference between the input set
     *         and the original set
     */
    public abstract MultiSet<D> diff(MultiSet<D> set);
    
    /**
     * Returns whether or not the original set and the input set are equal,
     * including the multiplicity of each respective object.
     * 
     * @param set: a MultiSet which will be compared with the original set
     * @return a boolean only true when the original set and input set are
     *         equal
     */
    public abstract boolean equal(MultiSet<D> set);
    
    /**
     * Returns whether or not the original set is a subset of the input set,
     * checking that the multiplicity of all objects in the original set do
     * not exceed the multiplicity of the respective objects in the input set.
     * 
     * @param set: a MultiSet which will be compared with the original set
     * @return a boolean only true when the original set is a subset of the
     *         input set
     */
    public abstract boolean subset(MultiSet<D> set);
    
    /**
     * Returns the AVL-balanced equivalent of the original set.
     * 
     * @return a MultiSet equal to the original set with all branches balanced 
     */
    public abstract MultiSet<D> balance();
    
    /**
     * Returns the original set rotated once to the left:
     *  A            B
     *   \          / \
     *    B   >>>  A   C
     *     \
     *      C
     * 
     * @return a MultiSet equal to the original set rotated once to the left
     */
    public abstract MultiSet<D> rotateLeft();
    
    /**
     * Returns the original set rotated once to the right:
     *      C          B
     *     /          / \
     *    B   >>>    A   C
     *   /
     *  A
     * 
     * @return a MultiSet equal to the original set rotated once to the right
     */
    public abstract MultiSet<D> rotateRight();
    
    /**
     * Returns an abstract sequence for the purposes of iteration over the
     * original set.
     * 
     * @return a sequence representing the objects in the set
     */
    public abstract Sequence<D> seq();
    
    /**
     * Returns a String representing the elements of the set in ascending order.
     * 
     * @return a String representing the set 
     */
    public abstract String toString();
    
    
}
