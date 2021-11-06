package Arbeidskrav_8;

import java.util.ArrayList;
import java.util.Arrays;

public class LempelZiw {

    private char[] data;

    public LempelZiw(char[] data){
        this.data = data;
    }


    public byte[] lZ(){
        ArrayList<Byte> compressed = new ArrayList<>();

        StringBuilder notCompressed = new StringBuilder();

        for (int i = 0; i < data.length; ){
            Pointer pointer = findPointer(i);
            if (pointer != null){
                //If pointer found and no uncompressed bytes are stored, add the values of the pointer to the compressed file
                if (!notCompressed.isEmpty()){
                    compressed.add((byte) notCompressed.length());
                    for (int c = 0; c < notCompressed.length(); c++){
                        compressed.add((byte) notCompressed.charAt(i));
                    }
                    notCompressed = new StringBuilder();
                }
                compressed.add((byte) (pointer.getDistance() >> 4 | (1<<7)));
                compressed.add((byte) ((pointer.getDistance() & 0x0F) | ((pointer.getLength()) - 1)));
            } else {
                notCompressed.append(data[i]);

                if (notCompressed.length() == 256){
                    compressed.add((byte) ((notCompressed.length() + 256)% 256));
                    for (int c = 0; c < notCompressed.length(); c++){
                        compressed.add((byte) notCompressed.charAt(i));
                    }
                    notCompressed = new StringBuilder();
                }
                i++;
            }
        }
        if (!notCompressed.isEmpty()) {
            compressed.add((byte) notCompressed.length());
            for (int c = 0; c < notCompressed.length(); c++) {
                compressed.add((byte) notCompressed.charAt(c));
            }
        }

        return toByteArray(compressed);
    }

    public byte[] toByteArray(ArrayList<Byte> list){
        byte[] byteArray = new byte[list.size()];
        for (int i = 0; i < list.size(); i++) {
            byteArray[i] = list.get(i);
        }
        return byteArray;
    }

    public Pointer findPointer (int index){
        Pointer pointer = new Pointer();

        int max = index + 4;
        if (max > data.length - 1) max = data.length - 1;

        int min = index - 256;
        if (min < 0) min = 0;

        char[] buffer = Arrays.copyOfRange(data, min, index);

        int i = index + 3;

        while (i < (max + 1)){
            char[] searchWord = Arrays.copyOfRange(data, index, i);
            int j = 0;
            while (searchWord.length + j <= buffer.length){
                int k = searchWord.length - 1;
                while (k >= 0 && searchWord[k] == buffer[j + k]){
                    k--;
                }
                if (k < 0){
                    pointer.setDistance(buffer.length - j);
                    pointer.setLength(searchWord.length);
                    i++;
                }
                else {
                    int l = k-1;
                    while (l >= 0 && searchWord[l] != buffer[j+k]){
                        l--;
                    }
                    j += k - 1;
                }
            }
        }
        if (pointer.getDistance() > 0 && pointer.getLength() > 0) return pointer;
        return null;
    }



}

class Pointer {
    private int length, distance;

    public Pointer(){
        this(-1,-1);
    }

    public Pointer (int matchLength, int jumpDist){
        this.length = matchLength;
        this.distance = jumpDist;
    }

    public int getLength() {
        return length;
    }

    public int getDistance() {
        return distance;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }
}
