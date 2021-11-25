package Arbeidskrav_1.kotlin

fun main(){
    val change = arrayOf(-1, 0, -9, -1, 2, -1, 2, -1,5)
    val result = Arbeidskrav_1_stonks().stonkCheck(change)
    println("Profit = ${result[0]}, best buy day = ${result[1]}, best sell day = ${result[2]}")
}
class Arbeidskrav_1_stonks {


    fun stonkCheck(change: Array<Int>): List<Int>{
        var price = 0
        var lowestPrice = 0
        var bestBuyDay = 0
        var bestSellDay = 0
        var bestProfit = 0
        var profit = 0
        var i = 0;


        for (p in change){
            price += p
            if (lowestPrice > price){
                lowestPrice = price
                bestBuyDay = i
            }
            profit = price - lowestPrice
            if (profit > bestProfit){
                bestProfit = profit
                bestSellDay = i
            }
            i++
        }
        return listOf(profit, bestBuyDay, bestSellDay)
    }
}