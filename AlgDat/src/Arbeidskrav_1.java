import java.util.Date;
import java.util.Random;

public class Arbeidskrav_1 {
    public static void main(String[] args) {
        Random rand = new Random();
        int nSize = 100000;
        int [] change = new int[nSize];
        for (int i = 0; i < nSize; i++) {
            change[i] = (rand.nextInt(20) - 10);
        }

        Date start = new Date();
        System.out.println(stonkCheck(change));
        Date slutt = new Date();
        System.out.println(slutt.getTime() - start.getTime());
    }

    public static String stonkCheck(int[] priceChange){
        int bestBuyDay = 0;
        int bestSellDay = 0;
        int price = 0;
        int priceCheck = 0;
        int bestSum = 0;
        int sum;

        for (int i = 0; i < priceChange.length; i++) {
            price += priceChange[i];
            for (int j = i; j < (priceChange.length - i); j++) {
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