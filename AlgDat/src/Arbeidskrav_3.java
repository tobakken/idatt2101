import java.util.Arrays;
import java.util.Date;
import java.util.Random;

public class Arbeidskrav_3 {
    public static void main(String[] args) {

        int tabellsize = 1000000;
        int randomRange = 500000;

        Random rand = new Random();
        int[] tabell = new int[tabellsize];
        for (int i = 0; i < tabell.length; i++) {
            tabell[i] = rand.nextInt(randomRange);
        }

        System.out.println("Range of random numbers: " + randomRange);
        System.out.println("Sum: " + sum(tabell) + "\n");

        //Lager en kopi for sammenligning
        int[] tabellKopi1 = Arrays.copyOf(tabell, tabellsize);

        System.out.println(tabellTestRiktigSortert(tabell));
        System.out.println(tidtaking(tabell, tabellsize));
        System.out.println(sum(tabell));

        System.out.println("\n" + tabellTestRiktigSortert(tabellKopi1));
        System.out.println(tidtakingImprovedQS(tabellKopi1, tabellsize));
        System.out.println(sum(tabellKopi1));
    }

    //Tidtaking av vanlig quicksort
    private static String tidtaking(int[] tabell, int size) {
        Date start = new Date();
        quicksort(tabell, 0, size - 1);
        Date slutt = new Date();
        Double tid = (double)
                (slutt.getTime() - start.getTime());
        return String.format("Vanlig quicksort: \nMillisekund brukt: %.7f%n", tid) + ("Tabellen er sortert rett: " + tabellTestRiktigSortert(tabell));
    }

    //Tidtaking av forbedret quicksort
    private static String tidtakingImprovedQS(int[] tabell, int size) {
        Date start = new Date();
        quicksortImproved(tabell, 0, size - 1);
        Date slutt = new Date();
        Double tid = (double)
                (slutt.getTime() - start.getTime());
        return String.format("Quicksort med hjelpealgoritme: \nMillisekund brukt: %.7f%n", tid) +
                ("Tabellen er sortert rett: " + tabellTestRiktigSortert(tabell));
    }

    public static void bytt(int[] t, int v, int m) {
        int hjelp = t[v];
        t[v] = t[m];
        t[m] = hjelp;
    }

    public static int median3sort(int[] t, int v, int h) {
        int m = (v + h) / 2;
        if (t[v] > t[m]) bytt(t, v, m);
        if (t[m] > t[h]) {
            bytt(t, m, h);
            if (t[v] > t[m]) bytt(t, v, m);
        }
        return m;
    }

    //100% Quicksort slik den står i lærebok
    public static void quicksort(int[] t, int v, int h) {
        if (h - v > 2) {
            int delepos = splitt(t, v, h);
            quicksort(t, v, delepos - 1);
            quicksort(t, delepos + 1, h);
        } else median3sort(t, v, h);
    }

    //Quicksort med sjekk for like elementer i tabell
    public static void quicksortImproved(int[] t, int v, int h) {
        if (v > 0 && h < t.length - 1 && t[v - 1] == t[h + 1]) {}
        else {
            if (h - v > 2) {
                int delepos = splitt(t, v, h);
                quicksortImproved(t, v, delepos - 1);
                quicksortImproved(t, delepos + 1, h);
            } else median3sort(t, v, h);
        }

    }

    private static int splitt(int[] t, int v, int h) {
        int iv, ih;
        int m = median3sort(t, v, h);
        int dv = t[m];
        bytt(t, m, h - 1);
        for (iv = v, ih = h - 1; ; ) {
            while (t[++iv] < dv) ;
            while (t[--ih] > dv) ;
            if (iv >= ih) break;
            bytt(t, iv, ih);
        }
        bytt(t, iv, h - 1);
        return iv;
    }

    public static void boblesort(int[] t, int start, int stop) {
        for (int i = start - 1; i > stop; --i) {
            for (int j = 0; j < i; j++) {
                if (t[j] > t[j + 1]) {
                    bytt(t, j, j + 1);
                }
            }
        }
    }

    public static void innsettingssort(int[] t, int start, int length) {
        for (int i = start; i < length; i++) {
            int bytt = t[i];
            int j = i - 1;
            while (j >= 0 && t[j] > bytt) {
                t[j + 1] = t[j];
                --j;
            }
            t[j + 1] = bytt;
        }
    }

    public static boolean tabellTestRiktigSortert(int[] t) {
        for (int i = 0; i < (t.length - 1); i++) {
            if (t[i] > t[i + 1]) {
                return false;
            }
        }
        return true;
    }

    private static int sum(int[] t) {
        int sum = 0;
        for (int j : t) {
            sum += j;
        }
        return sum;
    }
}
