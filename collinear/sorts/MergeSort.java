/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

package sorts;

import edu.princeton.cs.algs4.StdOut;

public class MergeSort<Item extends Comparable<Item>> {

    public void merge(Item[] arr, Item[] aux, int lo, int mid, int hi) {

        // Copy array to auxilliary array
        for (int k = lo; k <= hi; k++) aux[k] = arr[k];

        // Merge the two sub-arrays
        int i = lo, j = mid + 1;
        for (int k = lo; k <= hi; k++) {
            if (i > mid) arr[k] = aux[j++];
            else if (j > hi) arr[k] = aux[i++];
            else if (aux[j].compareTo(aux[i]) < 0) arr[k] = aux[j++];
            else arr[k] = aux[i++];
        }
    }

    private void sort(Item[] arr, Item[] aux, int lo, int hi) {
        if (hi <= lo) return;
        int mid = lo + (hi - lo) / 2;
        sort(arr, aux, lo, mid);
        sort(arr, aux, mid + 1, hi);
        merge(arr, aux, lo, mid, hi);
    }

    public void sort(Item[] arr) {
        Item[] aux = (Item[]) new Comparable [arr.length];
        sort(arr, aux, 0, arr.length-1);
    }

    public static void main(String[] args) {
        if (args == null) return;

        Integer[] arr = new Integer[args.length];
        for (int i = 0; i < args.length; i++) {
            arr[i] = Integer.parseInt(args[i]);
        }

        MergeSort<Integer> sorter = new MergeSort<Integer>();
        sorter.sort(arr);

        for (Integer i: arr) {
            StdOut.print(i + " ");
        }

        StdOut.println(" ");
    }
}
