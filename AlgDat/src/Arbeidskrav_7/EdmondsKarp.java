package Arbeidskrav_7;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

public class EdmondsKarp {
    public static void main(String[] args) {

        Graph graph = readFile("flytgraf1");
        graph.edmKarp(0, 7);

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

    public void edmKarp(int source, int drain) {
        System.out.println("Maks flyt fra " + source + " til " + drain + " med Edmond-Karp");
        System.out.println("Økning  flytøkende vei");
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
                //Iterates through to see if edges have been visited and if they have capacity
                //Add them to visited and queue if not. Set parent.
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
            if (increase > 0){
                System.out.print(increase + "     " + source + " ");

                for (int i = drain; i != source; i = parent[i]) {
                    message = i + " " + message + " ";
                    node[parent[i]][i] -= increase;
                    node[i][parent[i]] += increase;
                }
                System.out.print(message + "\n");
            }

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
