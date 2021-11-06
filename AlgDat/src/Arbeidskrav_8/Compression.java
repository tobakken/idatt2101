package Arbeidskrav_8;

import java.io.*;
import java.nio.file.Files;

public class Compression {
    public static void main(String[] args) throws IOException {
        Bytes bytes = new Bytes("./opg8-2021.pdf", "LempelZiv");
        byte[] data = bytes.getData();
        lempelZiv(data, bytes);

    }

    public static void lempelZiv(byte[] data, Bytes bytes) throws IOException {
        LempelZiw lempelZiw = new LempelZiw(data);
        byte[] compressedArrayLZ = lempelZiw.lZ();
        bytes.writeCompressed(compressedArrayLZ);
    }
}



class Bytes {
    private byte[] data;
    private final File input;
    private final File output;

    public Bytes(String pathToFile, String method) throws IOException {
        this.input = new File(pathToFile);
        this.output = new File("./opg8-2021LZ.pdf");
        data = Files.readAllBytes(input.toPath());
    }

    public byte[] getData() {
        return data;
    }

    public void writeCompressed(byte[] byteArray) throws IOException {
        FileOutputStream fos = new FileOutputStream(output);
        fos.write(byteArray);
    }

    public void printBytes(){
        for (byte c : data){
            System.out.println(c);
        }
    }
}


