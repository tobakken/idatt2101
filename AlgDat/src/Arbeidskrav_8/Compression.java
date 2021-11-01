package Arbeidskrav_8;

public class Compression {
    public static void main(String[] args) {
        String test = "Problemer, problemer. Alltid problemer!\n" +
                "Dette er dagens problem. Problemet er\n" +
                "å komprimere problematisk tekst.";


    }

    public void find(String text){

    }

    public void boyerMoore(String text, String search){
        int[] badMatchTable = badMatch(search);
        int i = search.length() - 1;
        int j = search.length() - 1;
        while (i >= 0){
            if (!(search.charAt(i) == text.charAt(j))){
                j += badMatchTable[(int) search.charAt(i)];
            }
            i--;
            j--;
        }
    }

    public int[] badMatch(String search){
        int rounds = 0;
        int[] table = new int[256];
        for (int i = search.length()-1; i >= 0 ; i--) {
            for (int j = 97; j < 123; j++) {
                if ((int) search.charAt(i) == j){
                    table[j] = rounds;
                } else {
                    table[j] = search.length();
                }
            }
            rounds++;
        }
        return table;
    }
}
