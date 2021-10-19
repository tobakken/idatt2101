package Arbeidskrav_7;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

public class EdmondsKarp {
    public static void main(String[] args) {

    }

    public static void readFile(String filnavn) {
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
    boolean visited;
    int distance;


    public Node(int n) {
        element = n;
        edges = new ArrayList<>();
        visited = false;
    }

    public void addEdge(Node neighbour, int flow) {
        edges.add(new Edge(neighbour, flow));
        neighbour.edges.add(new Edge(this, 0));
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

    public void addEdge(int h, int n, int cap) {
        Node head = node[h];
        Node next = node[n];
        head.addEdge(next, cap);
    }

    public void edmKarp(int s, int drain) {
        Stack queue = new Stack();
        int increase = 1000000;
        int dist = 0;
        Node check = node[s];
        queue.push(s);
        check.visited = true;
        while (!queue.empty() && !check.equals(node[drain])) {
            dist++;
            for (int i = check.edges.size(); --i > 0; ) {
                check = check.edges.get(i).end;
                check.distance = dist;
                if (!check.visited) {
                    queue.push(check.element);
                    check.visited = true;
                }
            }
            check = node[queue.next()];
        }
    }
}

class Edge {
    Node end;
    int flow;
    int cap;

    public Edge(Node e, int cap) {
        this.end = e;
        this.cap = cap;
    }

    public int restCap() {
        return cap - flow;
    }
}

class Stack {

    ArrayList<Integer> stack;

    public Stack() {
        stack = new ArrayList<>();
    }

    public void push(int n) {
        stack.add(n);
    }

    public boolean empty() {
        return stack.size() == 0;
    }

    public int next() {
        int x = stack.get(0);
        stack.remove(0);
        return x;
    }

    public String toString() {
        return this.stack + " ";
    }
}
