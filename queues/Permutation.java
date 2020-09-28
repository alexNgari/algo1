/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class Permutation {
    public static void main(String[] args) {
        if (args.length == 0) {
            return;
        }
        int k = Integer.parseInt(args[0]);
        if (k <= 0) {
            return;
        }
        int i = 1;
        RandomizedQueue<String> queue = new RandomizedQueue<String>();
        while (!StdIn.isEmpty()) {
            String temp = StdIn.readString();
            if (i <= k) {
                queue.enqueue(temp);
            } else {
                int rand = StdRandom.uniform(i+1);
                if (rand <= k) {
                    queue.dequeue();
                    queue.enqueue(temp);
                }
            }
            i++;
        }
        for (String item: queue) {
            StdOut.println(item);
        }
    }
}
