public class Arbeidskrav_2 {
    public static void main(String[] args) {
        System.out.println(exponentCalc(2, 10));
    }

    // x^n
    public static double exponentCalc(double base, int exp){
        if (exp == 0){
            return 1;
        }
        else {
            return base * exponentCalc(base, (exp-1));
        }
    }
}
