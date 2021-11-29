package Arbeidskrav_2.kotlin

import kotlin.system.measureNanoTime
import kotlin.system.measureTimeMillis


fun main(){
    val xValue = 1.001
    val expValue = 4000

    val e = ExponentCalc()
    var res1 = 0.0
    var res2 = 0.0

    val time1 = measureNanoTime {
        res1 = e.expCalc1(xValue, expValue)
    }

    val time2 = measureNanoTime {
        res2 = e.expCalc2(xValue, expValue)
    }

    println("Nanoseconds for first method: $time1 \nResult: $res1")
    println("Nanoseconds for second method: $time2 \nResult:$res2")
}

class ExponentCalc {
    fun expCalc1(x: Double, exp: Int): Double {
        if (exp == 0) return 1.0
        return x * expCalc1(x, (exp - 1))
    }

    fun expCalc2(x: Double, exp: Int): Double{
        if (exp == 0) return 1.0
        if (exp % 2 != 0) return x * expCalc2(x * x, (exp - 1) / 2)
        return expCalc2(x * x, exp / 2)
    }
}