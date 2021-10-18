package Arbeidskrav_6;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

/**
 * @Author tobakken
 */
public class AK6OPPG2 {
    public static void main(String[] args) {
        GraphOppg2 graf;
        ArrayList<String []> split = readFile("L7g5");
        graf = new GraphOppg2(Integer.parseInt(split.get(0)[0]));
        for (int i = 1; i < split.size(); i++) {
            graf.addEdge(Integer.parseInt(split.get(i)[0]), Integer.parseInt(split.get(i)[1]));
        }

        NodeOppg2 topores = graf.toposort();

        for (int i = 0; i < graf.node.length; i++) {
            System.out.print(" " + topores);
            topores = topores.next;
        }
    }

    public static ArrayList<String[]> readFile(String filnavn){
        String line;
        ArrayList<String[]> split = new ArrayList<>();
        File file = new File("./" + filnavn + ".txt");
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            while ((line = br.readLine()) != null) {
                String trimmet = line.trim();
                split.add(trimmet.split("\\s+"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return split;
    }
}

class NodeOppg2 {
    int element;
    ArrayList<NodeOppg2> neighbours;
    boolean found;
    NodeOppg2 next;

    public NodeOppg2(int n) {
        element = n;
        found = false;
        neighbours = new ArrayList<>();
    }

    public void setNeighbours(NodeOppg2 k) {
        if (!neighbours.contains(k)) {
            neighbours.add(k);
        }
    }

    @Override
    public String toString() {
        return "" + element;
    }
}

class GraphOppg2 {
    NodeOppg2[] node;

    public GraphOppg2(int size) {
        node = new NodeOppg2[size];
        //Initialises all nodes
        for (int i = 0; i < size; i++) {
            node[i] = new NodeOppg2(i);
        }
    }

    private NodeOppg2 toposortHelp(NodeOppg2 n, NodeOppg2 l) {
        if (n.found) return l;
        for (int i = 0; i < n.neighbours.size(); i++) {
            NodeOppg2 sjekk = n.neighbours.get(i);
            l = toposortHelp(sjekk, l);
        }
        n.found = true;
        n.next = l;
        return n;
    }

    public NodeOppg2 toposort() {
        NodeOppg2 l = null;
        for (int i = node.length; i-- > 0; ) {
            l = toposortHelp(node[i], l);
        }
        return l;
    }


    public void addEdge(int h, int n) {
        NodeOppg2 head = node[h];
        NodeOppg2 next = node[n];
        head.setNeighbours(next);
    }
}
