public class Arbeidskrav_4 {
    public static void main(String[] args) {

    }


}

class Node {
    double element;
    Node neste;
    Node forrige;

    public Node(double e, Node n, Node f){
        this.element = e;
        this.neste = n;
        this.forrige = f;
    }

    public double getElement() {
        return element;
    }

    public Node getNeste() {
        return neste;
    }

    public Node getForrige() {
        return forrige;
    }
}

class SirkulaerListe {
    private Node hode = null;
    private Node hale = null;
    private int antElementer = 0;
    public int getAntElementer() {
        return antElementer;
    }

    public void leggTilNode(double verdi){

    }

    public void settInnFremst (double verdi){
        hode = new Node(verdi, hode, null);
        if (hode == null) hale = hode;
        else hode.neste.forrige = hode;
        ++antElementer;
    }

    public void settInnBakerst(double verdi){
        Node ny = new Node(verdi, null, hale);
        if (hale != null) hale.neste = ny;
        else {
            hode = ny;
            hale = ny;
        }

        ++antElementer;
    }
}
