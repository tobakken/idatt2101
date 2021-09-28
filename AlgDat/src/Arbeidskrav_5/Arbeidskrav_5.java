package Arbeidskrav_5;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;


public class Arbeidskrav_5 {
    public static void main(String[] args) {
        int count = 0;
        int m = 128;
        HashTabell ht = new HashTabell(m);
        String line;
        File file = new File("src/Arbeidskrav_5/navn.txt");
        try (BufferedReader br = new BufferedReader(new FileReader(file))){
            while ((line = br.readLine()) != null){
                Navn navn = new Navn(line);
                ht.leggTil(navn);
                count++;
            }
        } catch (Exception e){
            e.printStackTrace();
        }

        //Søker på et navn jeg vet har kollisjon
        Navn sok = ht.finnNavn("Oskar Langås Eidem");
        if (sok != null){
            System.out.println(sok.toString());
        } else {
            System.out.println("Søk matchet ingen lagrede navn");
        }

        //Prøver å legge til meg selv på nytt for å se at den kolliderer
        String tor = "Tor-Øyvind Paulsrud Bakken";
        Navn tpb = new Navn(tor);
        ht.leggTil(tpb);
        count++;

        System.out.println("\nAntall personer: " + count);
        System.out.println("Antall kollisjoner: " + ht.kollisjoner);
        float v = count;
        System.out.println("Kollisjoner pr person: " + (ht.kollisjoner/v));
        System.out.println("Lastfaktoren er " + (v/m));
        System.out.println(tpb.hash(m));
    }
}

class HashTabell{
    Navn[] tabell;
    int kollisjoner = 0;

    public HashTabell(int m){
        this.tabell = new Navn[m];
    }

    public void leggTil(Navn navn){
        int index = navn.hash(tabell.length);
        if (tabell[index] != null){
            kollisjoner++;
            System.out.println(navn.toString() + " Kolliderte med: " + tabell[index].toString());
            navn.linkNeste(tabell[index]);
            tabell[index] = navn;
        } else {
            tabell[index] = navn;
        }
    }

    public Navn finnNavn(String sokNavn){
        Hash hash = new Hash();
        int hashVerdiNavn = hash.multhash(sokNavn, 7, tabell.length);
        if (tabell[hashVerdiNavn] == null){
            return null;
        } else{
            if (tabell[hashVerdiNavn].getNavn().equals(sokNavn)){
                return tabell[hashVerdiNavn];
            } else {
                Navn sjekk = tabell[hashVerdiNavn];
                while (sjekk.neste != null){
                    System.out.println("Søket kolliderte med: " + sjekk.toString());
                    if (sjekk.neste.getNavn().equals(sokNavn)){
                        return sjekk.neste;
                    } else {
                        sjekk = sjekk.neste;
                    }
                }
            }
        }
        return null;
    }
}

class Navn {
    private final String navn;
    private final Hash hash = new Hash();
    Navn neste;

    public Navn(String navn){
        this.navn = navn;
    }

    public String getNavn() {
        return navn;
    }

    public int hash(int m){
        return hash.multhash(this.navn,7, m);
    }

    public void linkNeste(Navn neste){
        this.neste = neste;
    }

    @Override
    public String toString() {
        return this.navn;
    }
}

class Hash{
    int A = 1327217885;

    public int vektString(String navn){
        int sum = 0;
        for (int i = 0; i < navn.length(); i++) {
            int c = navn.charAt(i);
            sum += (i*i+1)*c;
        }
        return sum;
    }

    public int divHash(String navn, int m){
        return (vektString(navn) % m);
    }

    public int multhash(String navn, int x, int m){
        return vektString(navn) * A >> (32-x) & (m - 1);
    }
}
