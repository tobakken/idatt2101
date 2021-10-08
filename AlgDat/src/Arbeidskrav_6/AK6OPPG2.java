package Arbeidskrav_6;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;

public class AK6OPPG2 {
    public static void main(String[] args) {
        Graf graf = new Graf(0);
        String line;
        ArrayList<String[]> splitt = new ArrayList<>();
        File file = new File("./l7g5.txt");
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            while ((line = br.readLine()) != null) {
                String trimmet = line.trim();
                splitt.add(trimmet.split(" "));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        graf = new Graf(Integer.parseInt(splitt.get(0)[0]));
        for (int i = 1; i < splitt.size(); i++) {
            graf.leggTilKanter(Integer.parseInt(splitt.get(i)[0]), Integer.parseInt(splitt.get(i)[1]));
        }

        Node topores = graf.toposortFu();

        for (int i = 0; i < graf.node.length; i++) {
            System.out.print(" " + topores);
            topores = topores.neste;
        }
    }
}

class Node {
    int element;
    ArrayList<Node> kanter;
    boolean funnet;
    Node neste;

    public Node(int n) {
        element = n;
        funnet = false;
        kanter = new ArrayList<>();
    }

    public void setKanter(Node k) {
        if (!kanter.contains(k)) {
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

    private Node toposortFungerer(Node n, Node l) {
        if (n.funnet) return l;
        for (int i = 0; i < n.kanter.size(); i++) {
            Node sjekk = n.kanter.get(i);
            l = toposortFungerer(sjekk, l);
        }
        n.funnet = true;
        n.neste = l;
        return n;
    }

    public Node toposortFu() {
        Node l = null;
        for (int i = node.length; i-- > 0; ) {
            l = toposortFungerer(node[i], l);
        }
        return l;
    }


    public void leggTilKanter(int h, int n) {
        Node hode = node[h];
        Node neste = node[n];
        hode.setKanter(neste);
    }
}
