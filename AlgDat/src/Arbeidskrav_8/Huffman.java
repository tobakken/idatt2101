package Arbeidskrav_8;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;

public class Huffman {

    private int[] charFreq;
    private byte[] data;

    public Huffman(byte[] data){
        this.data = data;
        charFreq = frequency(data);
    }

    public HuffmanNode huffmanTree() {


        PriorityQueue<HuffmanNode> nodeQueue = new PriorityQueue<>(charFreq.length, new HuffmanComparator());

        //Initialize and add all nodes to queue
        for (int i = 0; i < charFreq.length; i++) {
            if (charFreq[i] != 0){
                HuffmanNode hn = new HuffmanNode();

                hn.c = (char)i;
                hn.value = charFreq[i];

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

            nodeQueue.add(root);
        }
        return root;
    }
    public String encode(HuffmanNode root, int value, String s) {
        if (root == null) return null;
        if (root.value == value) return s;

        String huffCode = encode(root.left, value, (s + "0"));

        if (!huffCode.isEmpty()) return huffCode;

        return encode(root.right, value, s + "1");
    }

    public byte[] encodingArray(){
        String[] encodings = new String[charFreq.length];
        HuffmanNode root = huffmanTree();
        for (int i = 0; i < charFreq.length; i++) {
            encodings[i] = encode(root, i, "");
        }
        return encodingByteArray(encodings);
    }

    private byte[] encodingByteArray(String[] encodingArray){
        byte[] encodedByteArray = new byte[encodingArray.length];

        for (int i = 0; i < encodingArray.length; i++){
            encodedByteArray[i] = (byte) Integer.parseInt(encodingArray[i]);
        }
        return encodedByteArray;
    }

    public static int[] frequency(byte[] str) {
        int[] freq = new int[256];

        for (byte b : str) {
            freq[b]++;
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
