/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

package Sorts;

public class Sort<Item extends  Comparable<Item>> {

    protected boolean less(Item v, Item w) {
        return  v.compareTo(w) < 0;
    }

    protected void exchange(Item[] a, int i, int j) {
        Item temp = a[i];
        a[i] = a[j];
        a[j] = temp;
    }

    protected boolean isSorted(Item[] a) {
        for (int i = 1; i < a.length; i++) {
            if (less(a[i], a[i-1])) return false;
        }
        return true;
    }

    public static void main(String[] args) {

    }
}
