/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

package Sorts;

import edu.princeton.cs.algs4.StdOut;

public class SelectionSort<Item extends Comparable<Item>> extends Sort<Item> {

    public void sort(Item[] arr) {
        for (int i = 0; i < arr.length; i++) {
            int min = i;
            for (int j = i+1; j < arr.length; j++) {
                if (less(arr[j], arr[min])) min = j;
            }
            exchange(arr, i, min);
        }
    }

    public static void main(String[] args) {
        if (args.length == 0) return;
        SelectionSort<Integer> sorter = new SelectionSort<>();
        Integer[] arr = new Integer[args.length];
        for (int i = 0; i < args.length; i++) {
            arr[i] = Integer.parseInt(args[i]);
        }
        sorter.sort(arr);
        for (int i: arr) StdOut.println(i);
    }
}
