package Arbeidskrav_6;

import java.util.ArrayList;
import java.util.Collections;

public class AK6OPPG2 {
    public static void main(String[] args) {
        Graf graf = new Graf(3);
        graf.leggTilKanter(0, 2);
        graf.leggTilKanter(2, 1);

        ArrayList<Node> topores = graf.topologisort();

        System.out.println(topores.toString());
    }
}

class Node {
    int element;
    ArrayList<Node> kanter;
    boolean funnet;

    public Node(int n) {
        element = n;
        funnet = false;
        kanter = new ArrayList<>();
    }

    public void setKanter(Node k) {
        if (!kanter.contains(k)){
            kanter.add(k);
        }
    }

    @Override
    public String toString() {
        return "" + element;
    }
}

class Graf {
    Node[] node;

    public Graf(int size) {
        node = new Node[size];
        //Initialiserer med alle noder
        for (int i = 0; i < size; i++) {
            node[i] = new Node(i);
        }
    }

    private void toposort(Node n, ArrayList<Node> list, int j){
        n.funnet = true;
        for (int i = 0; i < n.kanter.size(); i++) {
            Node sjekk = n.kanter.get(i);
            if (!sjekk.funnet){
                toposort(sjekk, list, j+1);
                sjekk.funnet = true;
            }
        }
        if (!list.contains(n)) list.add(n);
    }

    public ArrayList<Node> topologisort() {
        ArrayList<Node> sortert = new ArrayList<>();
        for (int i = node.length; i-- >0; ){
            toposort(node[i], sortert, 0);
        }
        Collections.reverse(sortert);
        return sortert;
    }

    public void leggTilKanter(int h, int n) {
        Node hode = node[h];
        Node neste = node[n];
        hode.setKanter(neste);
    }
}
