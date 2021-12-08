package Arbeidskrav_3.kotlin

fun main() {

}

class Quicksort {
    fun swap(t: Array<Int>, v: Int, m: Int) {
        val help = t[v]
        t[v] = t[m]
        t[m] = help
    }

    fun median3sort(t: Array<Int>, v:Int, h:Int): Int {
        val m = (v + h) / 2
        if (t[v] > t[m]) swap(t, v, m)
        if (t[m] > t[h]) {
            swap(t, m, h)
            if (t[v] > t[m]) swap(t, v, m)
        }
        return m
    }

    fun quicksort(t: Array<Int>, v: Int, h: Int){
        if (h - v > 2){
            val splitPos = split(t, v, h)
            quicksort(t, v, splitPos - 1)
            quicksort(t, splitPos + 1, h)
        } else median3sort(t, v, h)
    }

    fun split(t: Array<Int>, v: Int, h: Int): Int{
        TODO()
    }

    fun bubblesort(t: Array<Int>, start: Int, stop: Int){
        for (i in (start - 1)..stop){
            for (j in 0..i){
                if (t[j] > t[j + 1]){
                    swap(t, j, j + 1)
                }
            }
        }
    }

    fun insertSort(t: Array<Int>, start: Int, length: Int){
        for (i in start..length) {
            val swap = t[i]
            var j = i - 1
            while (j >= 0 && t[j] > swap) {
                t[j + 1] = t[j]
                j--
            }
            t[j + 1] = swap
        }
    }
}