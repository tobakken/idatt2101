package Arbeidskrav_6;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * @Author Jonathhl
 **/

public class AK6OPPG1 {
    public static void main(String[] args) {
        Graph g = null;
        try(BufferedReader br = new BufferedReader(new FileReader("./L7g2.txt"))) {

            /*
            StringTokenizer line = new StringTokenizer(br.readLine());
            int N = Integer.parseInt(line.nextToken());
            g = new Graph(N);
            while(br.ready()) {
                line = new StringTokenizer(br.readLine());
                g.addEdge(Integer.parseInt(line.nextToken()), Integer.parseInt(line.nextToken()));
            }

            Legger ved String tokenizer koden min, den fungerer helt identisk til den under. Jeg vil bare heller
            bruke og øve på regex, da tokenizer er deprecated.
             */

            String line = br.readLine();
            String[] readNum = line.trim().split("\\s+");
            g = new Graph(Integer.parseInt(readNum[0]));
            while ((line = br.readLine()) != null) {
                readNum = line.trim().split("\\s+");
                g.addEdge(Integer.parseInt(readNum[0]), Integer.parseInt(readNum[1]));
            }

        } catch (NumberFormatException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        g.BFS(0); // Startnode
        sortByDist(g.info, 2);
        // System.out.println("Node: " +g.info[21][0] + " Forgj: "+ g.info[21][1] + " Dist "+ g.info[21][2]);
        // Bruker for å søke i Skandinavia grafen.

        System.out.println(g.toString());
        // Printline her funker ikke noe bra når Skandinavia filen leses...


        System.out.println("Node  Forgj  Dist");

        for (int i = 0; i < g.value; i++) {
            if(g.info[i][1] != -1) {
                System.out.println(" " + g.info[i][0] + "     " + g.info[i][1] + "      " + g.info[i][2]);
            } else {
                System.out.println(" " + g.info[i][0] + "            " + g.info[i][2]);
            }
        }
    }

    public static void sortByDist(int[][] arr, int col) {
        Arrays.sort(arr, (o1, o2) -> {
            if(o1[col] > o2[col]) {
                return 1;
            } else {
                return 0;
            }
        });
    }
}

class Graph {

    LinkedList[] adjacent;
    int value;
    Stack s;
    int[][] info;

    public Graph(int v) {
        this.value = v;
        s = new Stack();
        adjacent = new LinkedList[v];
        for (int i = 0; i < v; i++) {
            adjacent[i] = new LinkedList();
        }
        info = new int[v][3];
        for (int i = 0; i < v; i++) {
            info[i][0] = i;
            info[i][1] = -1;
        }
    }

    public void addEdge(int v, int w) {
        if(v <= this.value) {
            adjacent[v].push(w);
        }
    }

    public void BFS(int s) {

        boolean[] visited = new boolean[value];
        Stack queue = new Stack();
        visited[s] = true;
        info[s][0] = s;
        info[s][1] = -1;
        info[s][2] = 0;
        queue.push(s);
        while(!queue.empty()) {
            s = queue.next();
            Node temp = adjacent[s].head;
            while(temp != null) {
                int n = temp.value;
                temp = temp.next;
                if(!visited[n]) {
                    info[n][0] = n;
                    info[n][1] = s;
                    info[n][2] = (info[s][2] + 1);
                    visited[n] = true;
                    queue.push(n);
                }
            }
        }
    }

    public String toString() {
        String s = "";
        for (int i = 0; i < this.value; i++) {
            s += "Node " + i + ": " + adjacent[i].toString() + "\n";
        }
        return s;
    }
}

class LinkedList {

    Node head;
    public LinkedList() {}

    public void push(int n) {
        if(this.head == null) {
            this.head = new Node(n);
            return;
        }
        Node temp = this.head;
        while(temp.next != null) {
            temp = temp.next;
        }
        temp.next = new Node(n);
    }

    public String toString() {
        if(this.head != null) {
            Node temp = this.head;
            String s = temp.value + " ";
            while(temp.next != null) {
                temp = temp.next;
                s += temp.toString();
            }
            return s;
        }
        return null;
    }
}

class Node {
    Node next;
    int value;

    public Node(int v) {
        this.value = v;
    }

    public String toString() {
        return this.value + " ";
    }
}

class Stack {

    ArrayList<Integer> stack;

    public Stack() {
        stack = new ArrayList();
    }

    public void push(int n) {
        stack.add(n);
    }

    public boolean empty() { return stack.size() == 0; }

    public int next() {
        int x = stack.get(0);
        stack.remove(0);
        return x;
    }

    public String toString() {
        return this.stack + " ";
    }
}