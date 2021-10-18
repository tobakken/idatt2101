import java.util.Date;
import java.util.Random;

public class Arbeidskrav_1 {
    public static void main(String[] args) {
        int[] change = {-1, 0, -9, -1, 2, -1, 2, -1, 5};
        //Lager en array med tilfeldige tall mellom -10 og 10 som representerer endringen i aksjekurs fra dag til dag.
        //nSize bestemmer størrelsen på arrayen.
        //Bruker den innebygde Random-funksjonen til Java, men denne gir problemer med større nSize. Algoritmen jeg
        // bruker er likevel såpass treig at jeg ikke kommer opp i store nok n-verdier til at det gir for ulogiske svar.
/*        Random rand = new Random();
        int nSize = 1000000;
        System.out.println("N-size: " + nSize);
        int [] change = new int[nSize];
        for (int i = 0; i < nSize; i++) {
            change[i] = (rand.nextInt(21) - 10);
        }*/

        Date start = new Date();
        System.out.println(stockCheck(change));
        Date slutt = new Date();
        System.out.println("Runtime in milliseconds: " + (slutt.getTime() - start.getTime()));

        System.out.println(stonkCheck(change));
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
            for (int j = i; j < (priceChange.length); j++) { //O(n)
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

    public static String stonkCheck(int[] priceChange){
        int bestBuy = 0;
        int bestSell = 0;
        int profit = 0;
        int sum = 0;
        int testBuyDay = 0;
        for (int day = 0; day < priceChange.length; ++day){
            sum += priceChange[day];
            if (sum < 0){
                testBuyDay = day + 1;
                sum = 0;
            } else if (sum > profit) {
                profit = sum;
                bestBuy = testBuyDay;
                bestSell = day + 1;
            }
        }
        return "Profitt: " + profit + ", beste kjøpsdag: " + bestBuy + ", beste salg: " + bestSell;
    }
}