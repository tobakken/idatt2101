package Arbeidskrav_4.kotlin

fun main() {
    val j = Josephus()
    println("The safe spot is: " + j.josephus(10, 4))
}

class Josephus {
    fun josephus(n: Int, m: Int): Int{
        val first = Node(1)
        var last = first
        for (i in 2..n){
            val new = Node(i)
            new.prev = last
            last.next = new
            last = new
            first.prev = new
        }
        last.next = first

        var current = first.prev

        //If current node links to itself, we must have reached the
        //last person
        while (current != null && current != current.next){
            for (i in 0 until m-1){
                if (current != null) {
                    current = current.next
                }
            }

            if (current != null) {
                current.next = current.next?.next
            }

        }
        if (current != null) {
            return current.value
        }
        return -1
    }
}

class Node<T>(var value: T, var next: Node<T>? = null, var prev: Node<T>? = null)