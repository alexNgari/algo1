/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

package Sorts;

import edu.princeton.cs.algs4.StdOut;

public class InsertionSort<Item extends Comparable<Item>> extends Sort<Item> {

    public void sort(Item[] arr) {
        for (int i = 1; i < arr.length; i++) {
            for (int j = i; j > 0; j--) {
                if (less(arr[j], arr[j-1])) exchange(arr, j, j-1);
            }
        }
    }

    public static void main(String[] args) {
        if (args.length == 0) return;
        InsertionSort<Integer> sorter = new InsertionSort<>();
        Integer[] arr = new Integer[args.length];
        for (int i = 0; i < args.length; i++) {
            arr[i] = Integer.parseInt(args[i]);
        }
        sorter.sort(arr);
        for (int i: arr) StdOut.println(i);
    }
}
