import java.util.Arrays;
import java.util.Random;

public class Arbeidskrav_3 {
    public static void main(String[] args) {

        Random rand = new Random();
        int [] tabell = new int[1000];

        for (int i = 0; i < tabell.length; i++) {
            tabell[i] = rand.nextInt(1000);
        }


        //System.out.println(Arrays.toString(tabell));
        System.out.println(tabellTestRiktigSortert(tabell));

        quicksort(tabell, 0, tabell.length - 1);

        //System.out.println(Arrays.toString(tabell));
        System.out.println(tabellTestRiktigSortert(tabell));

    }

    public static void bytt(int []t, int v, int m){
        int hjelp = t[v];
        t[v] = t[m];
        t[m] = hjelp;
    }

    public static int median3sort(int []t, int v, int h){
        int m = (v+h)/2;
        if(t[v] > t[m]) bytt(t, v, m);
        if(t[m] > t[h]) {
            bytt(t, m, h);
            if (t[v] > t[m]) bytt(t, v, m);
        }
        return m;
    }

    public static void quicksort(int []t, int v, int h){
        if (h - v > 2){
            int delepos = splitt(t, v, h);
            quicksort(t, v, delepos - 1);
            quicksort(t, delepos + 1, h);
        } else median3sort(t, v, h);
    }

    private static int splitt(int []t, int v, int h){
        int iv, ih;
        int m = median3sort(t, v, h);
        int dv = t[m];
        bytt(t, m, h -1);
        for (iv = v, ih = h - 1;;){
            while (t[++iv] < dv);
            while (t[--ih] > dv);
            if (iv >= ih) break;
            bytt(t, iv, ih);
        }
        bytt(t, iv, h - 1);
        return iv;
    }

    public static boolean tabellTestRiktigSortert(int []t){
        for (int i = 0; i < (t.length - 1); i++) {
            if (t[i] > t[i+1]){
                return false;
            }
        }
        return true;
    }
}
