package Arbeidskrav_4;

public class Main {
    public static void main(String[] args) {
        System.out.println("Den trygge plassen er: " + josephus(10, 4));
    }

    public static double josephus(int n, int m){
        Node first = new Node(1);
        Node prev = first;
        for (int i = 2; i < n+1; i++) {
            Node ny = new Node(i);
            ny.forrige = prev;
            prev.neste = ny;
            prev = ny;
            first.forrige = ny;
        }
        prev.neste = first;

        Node current = first.forrige;

        //Hvis den nåverende noden linker til seg selv må det bety at det kun er igjen én person.
        while (!current.equals(current.neste)){
            //steger seg gjennom personer som er trygge før den hopper over den som blir drept.
            for (int i = 0; i < m-1; i++) {
                current = current.neste;
            }
            current.neste = current.neste.neste;
        }
        return current.getElement();
    }
}
