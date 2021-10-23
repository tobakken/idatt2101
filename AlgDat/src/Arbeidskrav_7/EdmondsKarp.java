package Arbeidskrav_7;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;

public class EdmondsKarp {
    public static void main(String[] args) {

        Graph graph = readFile("flytgraf1");
        System.out.println(graph);
        graph.edmKarpVer2(0, 7);

    }

    public static Graph readFile(String filnavn) {
        String line;
        String[] readNum;
        Graph graph = new Graph(0);
        File file = new File("./" + filnavn + ".txt");
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            //Read first line of file and initialize graph
            readNum = br.readLine().trim().split("\\s+");
            graph = new Graph(Integer.parseInt(readNum[0]));
            //Read rest of file and add nodes/edges
            while ((line = br.readLine()) != null) {
                readNum = line.trim().split("\\s+");
                graph.addEdge(Integer.parseInt(readNum[0]), Integer.parseInt(readNum[1]), Integer.parseInt(readNum[2]));
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Problem med å lese fil: " + filnavn);
        }
        return graph;
    }
}

class Node {
    int element;
    Node previous;
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
    int[][] node;

    public Graph(int size) {
        node = new int[size][size];
    }

    public void addEdge(int h, int n, int cap) {
        if (cap > 0) {
            node[h][n] = cap;
        }
    }

    public void edmKarpVer2(int source, int drain) {
        boolean finished = false;
        int countFlow = 0;
        while (!finished){
            boolean check = false;
            boolean[] visited = new boolean[node.length];
            int[] parent = new int[node.length];
            Stack queue = new Stack();
            int increase = 10000000;
            int current = source;
            visited[source] = true;
            queue.push(current);
            while (!queue.empty()) {
                current = queue.next();
                if (current == drain) check = true;
                for (int i = 0; i < node.length; i++) {
                    if (!visited[i] && node[current][i] > 0) {
                        visited[i] = true;
                        queue.push(i);
                        parent[i] = current;
                    }
                }
            }
            if (!check) finished = true;
            String message = "";
            for (int i = drain; i != source; i = parent[i]) {
                if (increase > node[parent[i]][i]) {
                    increase = node[parent[i]][i];
                }
            }
            countFlow += increase;
            System.out.print(increase + "   " + source + " ");

            for (int i = drain; i != source; i = parent[i]) {
                message = i + " " + message + " ";
                node[parent[i]][i] -= increase;
                node[i][parent[i]] += increase;
            }
            System.out.print(message + "\n");
        }
        System.out.println("Max flow: " + countFlow);

    }


    @Override
    public String toString() {
        String msg = "";
        for (int i = 0; i < node.length; i++) {
            msg += i;
            for (int j = 0; j < node.length; j++) {
                msg += " " + node[i][j];
            }
            msg += "\n";
        }
        return msg;
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
