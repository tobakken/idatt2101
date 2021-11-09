package Arbeidskrav_8;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;

public class Huffman {

    private int[] charFreq;
    private byte[] data;
    private String[] huffString;
    private HuffmanNode root;
    private ArrayList<Byte> decompressed;

    public Huffman(byte[] data){
        this.data = data;
        charFreq = frequency(data);
        huffString = new String[256];
        this.root = huffmanTree();
        decompressed = new ArrayList<>();
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

    public byte[] compressHuff(){
        int input;
        int i = 0;
        int j;
        long currentByte = 0L;
        encode(root, "");
        ArrayList<Byte> bytes = new ArrayList<>();

        for (int k = 0; k < charFreq.length; k++) {
            bytes.add((byte) charFreq[k]);
        }

        String[] bitstrings = huffString;

        for (int k = 0; k < data.length; k++) {
            input = data[k];
            if (input < 0) input += 256;
            String bitstring = bitstrings[input];
            j = 0;
            while (j < bitstring.length()) {
                if (bitstring.charAt(j) == '0') currentByte = (currentByte << 1);
                else currentByte = ((currentByte << 1 | 1));

                ++j;
                ++i;

                if (i == 8) {
                    bytes.add((byte) currentByte);
                    i = 0;
                    currentByte = 0L;
                }
            }
        }
        while (i < 8 && i != 0){
            currentByte = (currentByte << 1);
            ++i;
        }
        bytes.add((byte) currentByte);

        byte[] compressed = new byte[bytes.size()];
        for (int k = 0; k < bytes.size(); k++) {
            compressed[k] = bytes.get(k);
        }
        return compressed;
    }

    public void encode(HuffmanNode root, String s) {
        if (root.left == null && root.right == null){
            huffString[root.c] = s;
            return;
        }
        encode(root.left, s + "0");
        encode(root.right, s + "1");
    }

    public int[] readFreqArray(byte[] compressedData){
        int[] freqArray = new int[256];
        for (int i = 0; i < freqArray.length; i++) {
            freqArray[i] = compressedData[i];
        }
        data = Arrays.copyOfRange(data, 256, data.length);
        return freqArray;
    }

    public byte[] deCompress(){
        charFreq = readFreqArray(data);

        HuffmanNode root = huffmanTree();

        byte characterAsBytes;
        int length = data.length;
        int lastbyte = data[length - 1];
        if (lastbyte > 0) length--;

        BitString h = new BitString(0,0);

        for (int i = 0; i < length; i++) {
            characterAsBytes = data[i];
            BitString bitstring = new BitString(8, characterAsBytes);
            h = BitString.concat(h, bitstring);
            h = writeCharactersTo(root, h);
        }

        if (lastbyte > 0) {
            BitString b = new BitString(lastbyte, data[length] >> (8 - lastbyte));
            h = BitString.concat(h, b);
            writeCharactersTo(root, h);
        }

        ArrayList<Byte> outputBytes = decompressed;

        byte[] decompressed = new byte[outputBytes.size()];
        for (int i = 0; i < outputBytes.size(); i++) {
            decompressed[i] = outputBytes.get(i);
        }
        return decompressed;
    }

    private BitString writeCharactersTo(HuffmanNode tree, BitString bitstring) {
        HuffmanNode tempTree = tree;
        int c = 0;

        for (int j = 1 << bitstring.length - 1; j != 0; j >>= 1) {
            c++;
            if ((bitstring.bits & j) == 0) tempTree = tempTree.left;
            else tempTree = tempTree.right;

            if (tempTree.left == null) {
                long character = tempTree.c;
                decompressed.add((byte) character);
                long temp = ~(0);
                bitstring.bits = (bitstring.bits & temp);
                bitstring.length = bitstring.length - c;

                c = 0;
                tempTree = tree;
            }
        }
        return bitstring;
    }

    public static int[] frequency(byte[] str) {
        int[] freq = new int[256];

        for (byte b : str) {
            freq[(b + 256)%256]++;
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

class BitString {
    int length;
    long bits;

    BitString(int length, long bitsAsLong) {
        this.length = length;
        this.bits = bitsAsLong;
    }

    BitString(int length, byte bits) {
        this.length = length;
        this.bits = convertByte(bits, length);
    }

    static BitString concat(BitString bitstring, BitString other) {
        int length = bitstring.length + other.length;
        long bits = other.bits | (bitstring.bits << other.length);

        if (length > 64)
            throw new IllegalArgumentException(
                    "Bitstring too long: " + bits + ", length=" + length);

        return new BitString(length, bits);
    }

    public long convertByte(byte bite, int length) {
        long temp = 0;
        for (long i = 1L << length - 1; i != 0; i >>= 1)
            if ((bite & i) == 0)
                temp = (temp << 1);
            else
                temp = ((temp << 1) | 1);

        return temp;
    }
}

