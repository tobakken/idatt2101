package Arbeidskrav_8;

import java.nio.charset.StandardCharsets;
import java.util.Comparator;
import java.util.PriorityQueue;

public class Huffman {
    public static void printCode(HuffmanNode root, String s) {
        if (root.left == null && root.right == null && Character.isLetter(root.c)) {

            System.out.println(root.c + "   |  " + s);

            return;
        }
        printCode(root.left, s + "0");
        printCode(root.right, s + "1");
    }

    public static void main(String[] args) {

        byte[] charArray = "BCAADDDCCACACAC".getBytes(StandardCharsets.UTF_8);
        int[] charfreq = frequency(charArray);

        PriorityQueue<HuffmanNode> nodeQueue = new PriorityQueue<>(charfreq.length, new HuffmanComparator());

        for (int i = 0; i < charfreq.length; i++) {
            if (charfreq[i] != 0){
                HuffmanNode hn = new HuffmanNode();

                hn.c = (char)i;
                hn.value = charfreq[i];

                hn.left = null;
                hn.right = null;

                nodeQueue.add(hn);
            }

        }

        HuffmanNode root = null;

        while (nodeQueue.size() > 1) {

            HuffmanNode x = nodeQueue.peek();
            nodeQueue.poll();

            HuffmanNode y = nodeQueue.peek();
            nodeQueue.poll();

            HuffmanNode f = new HuffmanNode();

            f.value = x.value + y.value;
            f.c = '-';
            f.left = x;
            f.right = y;
            root = f;

            nodeQueue.add(f);
        }
        System.out.println(" Char | Huffman code ");
        System.out.println("--------------------");
        printCode(root, "");

    }

    public static int[] frequency(byte[] str) {
        int[] freq = new int[256];

        for (int i = 0; i < str.length; i++) {
            freq[str[i]]++;
        }

        for (int i = 0; i < freq.length; i++) {
            System.out.println((byte)i + ": " + freq[i]);
        }
        return freq;
    }
}

class HuffmanNode {
    int value;
    char c;
    HuffmanNode left;
    HuffmanNode right;
}

class HuffmanComparator implements Comparator<HuffmanNode> {
    public int compare(HuffmanNode h1, HuffmanNode h2) {
        return h1.value - h2.value;
    }
}
