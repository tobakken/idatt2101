package Arbeidskrav_8;

import java.io.*;

public class Compression {
    public static void main(String[] args) throws IOException {
        Bytes bytes = new Bytes("./test.txt");
        byte[] data = bytes.getData();
        compress(data, bytes);
        bytes.readNewFile("./testHuff.txt");
        data = bytes.getData();
        deCompress(data, bytes);

    }

    public static void compress(byte[] data, Bytes bytes) throws IOException {
/*        LempelZiw lempelZiw = new LempelZiw(data);
        byte[] compressedArrayLZ = lempelZiw.lZ();
        bytes.writeToFile(compressedArrayLZ, "LZ");*/
        Huffman huffman = new Huffman(data);
        byte[] compressedHuffman = huffman.compressHuff();

        bytes.writeToFile(compressedHuffman, "Huff");
    }

    public static void deCompress(byte[] data, Bytes bytes) throws IOException {


        Huffman huffman = new Huffman(data);
        byte[] deCompressedHuff = huffman.deCompress();
        //bytes.writeToFile(deCompressedHuff, "deCompressedHuff");
/*        LempelZiw LZ = new LempelZiw(deCompressedHuff);
        byte[] deCompressedLZ = LZ.deCompress(data);*/
        bytes.writeToFile(deCompressedHuff, "deCompressedHuff");
    }

}



class Bytes {
    private byte[] data;
    private DataInputStream input;
    private DataOutputStream output;
    private String pathToFile;

    public Bytes(String pathToFile) throws IOException {
        this.pathToFile = pathToFile;
        readNewFile(pathToFile);
    }

    public void readNewFile(String pathToFile) throws IOException {
        this.input = new DataInputStream(new BufferedInputStream(new FileInputStream(pathToFile)));
        data = input.readAllBytes();
    }

    public byte[] getData() {
        return data;
    }

    public void writeToFile(byte[] byteArray, String method) throws IOException {
        this.output = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(new StringBuilder(pathToFile).insert((pathToFile.length()-4), method).toString())));

        output.write(byteArray);
        output.flush();
    }

    public void printBytes(){
        for (byte c : data){
            System.out.println(c);
        }
    }
}


