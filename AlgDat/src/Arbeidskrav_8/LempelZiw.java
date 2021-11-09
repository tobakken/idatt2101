package Arbeidskrav_8;

import java.util.ArrayList;
import java.util.Arrays;

public class LempelZiw {

    private static final int BUFFERSIZE = (1<<11) - 1;
    private static final int POINTERSIZE = (1<<4) - 1;
    private static final int MIN_SIZE_POINTER = 5;
    private final char[] data;


    public LempelZiw(byte[] dataBytes){
        data = new char[dataBytes.length];
        for (int i = 0; i < dataBytes.length; i++) {
            data[i] = (char) dataBytes[i];
        }
    }

    public byte[] lZ(){
        ArrayList<Byte> compressed = new ArrayList<>();

        StringBuilder notCompressed = new StringBuilder();

        for (int i = 0; i < data.length;){
            Pointer pointer = findPointer(i);
            if (pointer != null){
                //If pointer found and no uncompressed bytes are stored, add the values of the pointer to the compressed file
                if (notCompressed.length() != 0){
                    compressed.add((byte) (notCompressed.length()));
                    for (int c = 0; c < notCompressed.length(); c++){
                        compressed.add((byte) notCompressed.charAt(c));
                    }
                    notCompressed = new StringBuilder();
                }

                //1DDD DDDD | DDDD LLLL

                compressed.add((byte) (pointer.getDistance()*(-1)));
                compressed.add((byte) pointer.getLength());

                //compressed.add((byte) (pointer.getDistance() >> 4 | (1<<7)));
                //compressed.add((byte) ((pointer.getDistance() & 0x0F) | ((pointer.getLength() - 1))));
                i += pointer.getLength();
            } else {
                notCompressed.append(data[i]);

                if (notCompressed.length() == 127){
                    compressed.add((byte) (notCompressed.length()));
                    for (int c = 0; c < notCompressed.length(); c++){
                        compressed.add((byte) notCompressed.charAt(c));
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

        int max = index + POINTERSIZE;
        if (max > data.length - 1) max = data.length - 1;

        int min = index - BUFFERSIZE;
        if (min < 0) min = 0;

        char[] buffer = Arrays.copyOfRange(data, min, index);

        int i = index + MIN_SIZE_POINTER - 1;

        outer:
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
                    continue outer;
                }
                else {
                    int l = k - 1;
                    while (l >= 0 && searchWord[l] != buffer[j+k]){
                        l--;
                    }
                    j += k + 1;
                }
            }
            break;
        }
        if (pointer.getDistance() > 0 && pointer.getLength() > 0) return pointer;
        return null;
    }

    public byte[] deCompress(byte[] data){
        ArrayList<Byte> b = new ArrayList<>();
        int index = 0;

        int i = 0;
        while (i < data.length - 1){
            byte condition = data[i];
            if (condition >= 0){
                for (int j = 0; j < condition; j++) {
                    b.add(data[i + j + 1]);
                }
                index += condition;
                i += condition + 1;
            }
            else {
                //int jump = ((condition & 127) << 4) | ((data[i + 1] >> 4) & POINTERSIZE);
                //int length = (data[i + 1] & 0x0F) + 1;
                int jump = condition*(-1);
                int length = data[i + 1];

                for (int j = 0; j < length; j++) {
                    b.add(b.get(index - jump + j));
                }
                index += length;
                i += 2;
            }
        }
        byte[] deCompressed = new byte[b.size()];
        for (int j = 0; j < b.size(); j++) {
            deCompressed[j] = b.get(j);
        }
        return deCompressed;
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
