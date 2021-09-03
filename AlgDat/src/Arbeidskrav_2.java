import java.util.Date;

public class Arbeidskrav_2 {
    public static void main(String[] args) {
        double xValue = 1.001;
        int nValue = 4000;

        Date start = new Date();
        int runder = 0;
        double tid;
        Date slutt;
        do {
            Double r = exponentCalc(xValue, nValue);
            slutt = new Date();
            ++runder;
        } while (slutt.getTime()-start.getTime() < 1000);
        tid = (double)
                (slutt.getTime()-start.getTime()) / runder;
        System.out.printf("Millisekund pr. runde: %.7f%n", tid);
        System.out.printf(xValue + "^" + nValue + " = %.7f%n%n", exponentCalc(xValue, nValue));
    }

    //Algoritme oppg 2.1-1
    public static double exponentCalc(double x, int exp) {
        if (exp == 0) {
            return 1;
        } else {
            return x * exponentCalc(x, (exp - 1));
        }
    }

    //Algoritme oppg 2.2-3
    public static double exponentCalc2(double x, int exp) {
        if (exp == 0) {
            return 1;
        }
        if (exp % 2 != 0) {
            return x * exponentCalc2(x * x, ((exp - 1) / 2));
        } else {
            return exponentCalc2(x * x, exp / 2);
        }
    }
}
