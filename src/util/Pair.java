package util;

/**
 * This class represent a pair of objects of the same type.
 * the order of the values doesn't matters (e.g (1,2)=(2,1)).
 * @author Ido Guzi
 */
public class Pair<V> {
    private V left,right;

    /**
     * This constructor is the main and only constructor of this class.
     * @param l - the left value.
     * @param r - the right value.
     */
    public Pair(V l, V r){
        left=l;
        right=r;
    }

    /**
     * swap the element in the pair.
     * left value will become the right value
     * and right value will become the left value.
     * @return this pair after swaping the values.
     */
    public Pair<V> swap(){
        V tmp = left;
        left=right;
        right=tmp;
        return this;
    }

    /**
     * return the object left element.
     * @return the left element in the pair.
     */
    public V getLeft() {
        return left;
    }

    /**
     * Change the left element of the pair to V (left).
     * @param left - new left element.
     */
    public void setLeft(V left) {
        this.left = left;
    }

    /**
     * return the object right element.
     * @return the right element in the pair.
     */
    public V getRight() {
        return right;
    }

    /**
     * Change the right element of the pair to V (right).
     * @param right - new right element.
     */
    public void setRight(V right) {
        this.right = right;
    }

    /**
     * return a string representing the pair as a
     * mathematical (unordered) group with 2 elements.
     * @return a string representing the pair.
     */
    public String toString(){
        return "{"+left+","+right+"}";
    }

    /**
     * This method will return a hash code for the object.
     * the hashcode can be the same in different object but should
     * happen as rarely as possible.
     * to minimize collision the hash is based around:
     * for every 2 natural number a,b -> a+b and |a-b| is unique.
     * @return
     */
    @Override
    public int hashCode() {
        int lh = left.hashCode();
        int rh = right.hashCode();
        int hash = 53 * ((lh + rh)%10000000);
        int size = numOfDigits(Math.abs(lh-rh));
        for (int i=0;i<size;i++) {
            if (hash>100000000) hash=hash%123456789;
            hash *= 10;
        }
        hash += Math.abs(lh-rh);
        return hash;
    }

    /**
     * return true if this pair equal to object o.
     * {@code o} must be of type Pair with the same generic value type V.
     * @param o - any java object.
     * @return true if equals, false elsewhere.
     */
    @Override
    public boolean equals(Object o) {
        if (o==this) return true;
        if (o==null) return false;
        if (!(o instanceof Pair<?>)) return false;
        Pair<?> p = (Pair<?>) o;
        if (p.left.equals(left) && p.right.equals(right)) return true;
        if (p.left.equals(right) && p.right.equals(left)) return true;
        return false;
    }


    private int numOfDigits(int number){
        if (number < 100000) {
            if (number < 100) {
                if (number < 10) {
                    return 1;
                } else {
                    return 2;
                }
            } else {
                if (number < 1000) {
                    return 3;
                } else {
                    if (number < 10000) {
                        return 4;
                    } else {
                        return 5;
                    }
                }
            }
        } else {
            if (number < 10000000) {
                if (number < 1000000) {
                    return 6;
                } else {
                    return 7;
                }
            } else {
                if (number < 100000000) {
                    return 8;
                } else {
                    if (number < 1000000000) {
                        return 9;
                    } else {
                        return 10;
                    }
                }
            }
        }
    }

}
