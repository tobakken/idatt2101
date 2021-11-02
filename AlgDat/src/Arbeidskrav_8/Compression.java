package Arbeidskrav_8;

import java.io.*;
import java.util.Comparator;
import java.util.PriorityQueue;

public class Compression {
    public static void main(String[] args) {


    }
}



class Bytes {
    private static char[] data;
    private static String text;

    public Bytes(){

    }

    public static void readBytes(String path){
        try {
            DataInputStream dIns = new DataInputStream(new BufferedInputStream(new FileInputStream(path)));
            char[] data = new char[dIns.available()];
            String text = new String(dIns.readAllBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void printBytes(){
        data = text.toCharArray();
        for (char c : data){
            System.out.println((byte) c);
        }
    }
}


