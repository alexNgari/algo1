/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

package Sorts;

import edu.princeton.cs.algs4.StdOut;

public class ShellSort<Item extends Comparable<Item>> extends Sort<Item> {

    public void sort(Item[] arr) {
        int h = 1;
        while(h < arr.length/3) h = 3*h+1;
        while(h >= 1) {
            for (int i = h; i < arr.length; i++) {
                for (int j = i; j >= h; j--) {
                    if (less(arr[j], arr[j-h])) exchange(arr, j, j-h);
                }
            }
            h /= 3;
        }
    }

    public static void main(String[] args) {
        if (args.length == 0) return;
        ShellSort<Integer> sorter = new ShellSort<>();
        Integer[] arr = new Integer[args.length];
        for (int i = 0; i < args.length; i++) {
            arr[i] = Integer.parseInt(args[i]);
        }
        sorter.sort(arr);
        for (int i: arr) StdOut.println(i);
    }
}
