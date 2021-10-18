package Arbeidskrav_7;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

public class EdmondsKarp {
    public static void main(String[] args) {

    }
    public static void readFile(String filnavn){
        String line;
        String[] readNum;
        File file = new File("./" + filnavn + ".txt");
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            //Read first line of file and initialize graph
            readNum = br.readLine().trim().split("\\s+");

            //Read rest of file and add nodes/edges
            while ((line = br.readLine()) != null) {
            readNum = br.readLine().trim().split("\\s+");
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Problem med å lese fil: " + filnavn);
        }
    }
}

class Node {
    int element;
    ArrayList<Edge> edges;


    public Node(int n) {
        element = n;
        edges = new ArrayList<>();
    }


    @Override
    public String toString() {
        return "" + element;
    }
}

class Graph {
    Node[] node;

    public Graph(int size) {
        node = new Node[size];
        //Initialises all nodes
        for (int i = 0; i < size; i++) {
            node[i] = new Node(i);
        }
    }

    public void addEdge(int h, int n, int flow) {
        Node head = node[h];
        Node next = node[n];
    }
}

class Edge {
    Node start;
    Node end;
    int flow;

    public Edge(Node s, Node e, int flow){
        this.start = s;
        this.end = e;
        this.flow = flow;
    }


}
