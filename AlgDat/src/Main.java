public class Main {
    public static void main(String[] args) {
        int[] change = {-1, 3, -9, 2, 2, -1, 2, -1, -5};

        int bestBuyDay = 0;
        int bestSellDay = 0;
        int price = 0;
        int priceCheck = 0;
        int bestSum = 0;
        int sum;

        for (int i = 0; i < change.length; i++) {
            price += change[i];
            for (int j = i; j < (change.length - i); j++){
                priceCheck += change[j];
                sum = priceCheck - price;
                if (sum > bestSum){
                    bestSum = sum;
                    bestBuyDay = i + 1;
                    bestSellDay = j + 1;
                }
            }
            priceCheck = price;
        }

        System.out.println("Best buy day: " + bestBuyDay + " \nBest sell day: " + bestSellDay +
                "\nThat gives a profit of: " + bestSum);
    }
}
