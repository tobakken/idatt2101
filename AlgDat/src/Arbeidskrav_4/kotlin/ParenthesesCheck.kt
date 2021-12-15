package Arbeidskrav_4.kotlin

import java.io.BufferedReader
import java.io.FileReader
import java.io.InputStream
import java.util.*
import kotlin.collections.ArrayList

fun main() {
    val p = ParenthesesCheck()
    val code = p.readFile("./TestFile.txt")

    p.openingParentheses(code)
}

class ParenthesesCheck {
    private val opening = arrayOf('(', '[', '{')
    private val closing = arrayOf(')', ']', '}')

    fun openingParentheses(code: String){
        val stack = Stack()
        var i = 0

        while (i < code.length){
            if (code[i] in opening) {
                stack.push(code[i])
                i = closingparentheses(code, i + 1, stack)
            } else if (code[i] in closing) println("Error closing before")
            i++
        }
    }

    fun closingparentheses(code: String, start: Int, stack: Stack): Int{
        var i = start
        while (i < code.length && code[i] !in closing){
            if (code[i] in opening) {
                stack.push(code[i])
                i = closingparentheses(code, i + 1, stack)
            }
            i++
        }
        if (i <= code.lastIndex && closing.indexOf(code[i]) == opening.indexOf(stack.pop())){
            return i
        }

        println("Error mismatch")
        return i + 1
    }

    fun readFile(path: String): String {
        val code = BufferedReader(FileReader(path))
        var string = ""

        while (code.ready()){
            string += code.readLine()
        }

        return string
    }
}

class Stack{
    val stack: ArrayList<Char> = arrayListOf()

    fun empty(): Boolean {
        return stack.isEmpty()
    }

    fun push(c: Char){
        stack.add(c)
    }

    fun pop(): Char? {
        if (!empty()){
            val c = stack[stack.size - 1]
            stack.removeAt(stack.size - 1)
            return c
        }
        return null
    }

    fun getStack(): String{
        return stack.toString()
    }
}

