public class Arbeidskrav_5 {
    public static void main(String[] args) {

    }
}

class HashTabell{
    Navn[] tabell;

    public HashTabell(int m){
        this.tabell = new Navn[m];
    }

    public void leggTil(Navn navn){
        tabell[navn.divHash(tabell.length)] = navn;
    }

}

class Navn {
    private final String navn;

    public Navn(String navn){
        this.navn = navn;
    }

    public int vektNavn(){
        int sum = 0;
        for (int i = 0; i < navn.length(); i++) {
            sum += navn.charAt(i)*7 + sum*7;
        }
        return sum;
    }

    public int divHash(int m){
        return vektNavn() % m;
    }
}
