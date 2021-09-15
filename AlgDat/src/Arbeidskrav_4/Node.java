package Arbeidskrav_4;

public class Node {
    double element;
    Node neste;
    Node forrige;

    public Node(double e){
        this.element = e;
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

    public void setNeste(Node neste) {
        this.neste = neste;
    }

    public void setForrige(Node forrige) {
        this.forrige = forrige;
    }
}
