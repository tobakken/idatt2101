import java.util.Date;
import java.util.Random;

public class Arbeidskrav_1 {
    public static void main(String[] args) {
        //int[] change = {-1, 3, -9, 2, 2, -1, -2, -1, 5};

        //Lager en array med tilfeldige tall mellom -10 og 10 som representerer endringen i aksjekurs fra dag til dag.
        //nSize bestemmer størrelsen på arrayen.
        //Bruker den innebygde Random-funksjonen til Java, men denne gir problemer med større nSize. Algoritmen jeg
        // bruker er likevel såpass treig at jeg ikke kommer opp i store nok n-verdier til at det gir for ulogiske svar.
        Random rand = new Random();
        int nSize = 1000000;
        System.out.println("N-size: " + nSize + "\n");
        int [] change = new int[nSize];
        for (int i = 0; i < nSize; i++) {
            change[i] = (rand.nextInt(21) - 10);
        }

        Date start = new Date();
        System.out.println(stockCheck(change));
        Date slutt = new Date();
        System.out.println(slutt.getTime() - start.getTime());
    }

    /*
    Totalt for metoden blir det O(n^2) siden det er for-loop i en for-loop.
    Har 10 deklarasjoner og én sjekk O(10n), men disse har forsvinnende liten påvirkning om n-> uendelig.
    */
    public static String stockCheck(int[] priceChange) {
        int bestBuyDay = 0;
        int bestSellDay = 0;
        int price = 0;
        int priceCheck = 0;
        int bestSum = 0;
        int sum;


        for (int i = 0; i < priceChange.length; i++) { //O(n)
            price += priceChange[i];
            for (int j = i; j < (priceChange.length - i); j++) { //O(n)
                priceCheck += priceChange[j];
                sum = priceCheck - price;
                if (sum > bestSum) {
                    bestSum = sum;
                    bestBuyDay = i + 1;
                    bestSellDay = j + 1;
                }
            }
            priceCheck = price;
        }
        return "Best buy day: " + bestBuyDay + " \nBest sell day: " + bestSellDay +
                "\nThat gives a profit of: " + bestSum;

    }
}