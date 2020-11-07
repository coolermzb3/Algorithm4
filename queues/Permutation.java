/* *****************************************************************************
 *  Author:          Yu Zihong
 *  Date:            2020/10/29 14:33
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class Permutation {
    public static void main(String[] args) {
        /***********************************************************************
         * use Equal probability sampling k from unknown population n
         *
         * 1. sample all the first k element
         * 2. for a next m th (m>k) element, sample it with the probability k/m
         *    and substitute a random element from the sampled k elements
         *
         * when m+1 th element comes, the m th element will be substitute
         * with the probability k/(m+1) * 1/k = 1/(m+1)
         * so m th element will stay with the probability 1-1/(m+1) = m/(m+1)
         *
         * so that the probability that it will be finally sampled is
         *     k/m * m/(m+1) * (m+1)/(m+2) * ... * (n-1)/n = k/n
         * meet the requirement!
         **********************************************************************/
        int k = Integer.parseInt(args[0]);
        int m = 1;
        RandomizedQueue<String> randomizedQueue = new RandomizedQueue<>();
        
        if (k == 0) {
            return;
        }

        while (!StdIn.isEmpty()) {
            String string = StdIn.readString();
            if (m <= k) {
                randomizedQueue.enqueue(string);
            }
            else {
                if (StdRandom.uniform(m) < k) {
                    randomizedQueue.dequeue();
                    randomizedQueue.enqueue(string);
                }
            }
            m++;
        }

        for (String s : randomizedQueue) {
            StdOut.println(s);
        }
    }
}
