package Arbeidskrav_3.kotlin

import kotlin.system.measureNanoTime

fun main() {
    val tab = Array<Int>(100){ (0 until 100).random() }
    //println(tab.contentToString())

    val tabQuicksort = tab.copyOf()
    val q = Quicksort()
    val timeQuick = measureNanoTime {
        q.quicksort(tabQuicksort, 0, tab.size - 1)
    }

    val tabBubble = tab.copyOf()
    val timeBubble = measureNanoTime {
        q.bubblesort(tabBubble, 0, tab.size - 1)
    }

    val tabInsert = tab.copyOf()
    val timeInsert = measureNanoTime { q.insertSort(tabInsert, 0, tab.size - 1) }

    println("Time for quicksort: " + timeQuick)
    println("Time for bubblesort: " + timeBubble)
    println("Time for insertsort: " + timeInsert)
    //println(tabQuicksort.contentToString())
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
        val m = median3sort(t,v,h)
        val dv = t[m]
        swap(t, m, h - 1)
        var iv: Int = v
        var ih: Int = h - 1
        while (iv < ih) {
            while (t[++iv] < dv){}
            while (t[--ih] > dv){}
            if (iv < ih) swap(t, iv, ih)
        }
        swap(t, iv, h - 1)
        return iv
    }

    fun bubblesort(t: Array<Int>, start: Int, stop: Int){
        for (i in (start - 1)..stop){
            for (j in 0 until i){
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