import kotlin.random.Random
import java.util.Scanner

//牌类
open class Poker {
    private val pokerSuit: Array<Char> = arrayOf('♦', '♣', '♥', '♠')
    private val pokerNumber: Array<String> = arrayOf("2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K", "A")
    private val standard = mutableListOf<String>()
    val library = mutableListOf<String>()

    init {
        for (number in pokerNumber)
            for (suit in pokerSuit) {
                library.add("$suit$number")
                standard.add("$suit$number")
            }
    }

    //与标准库对比得出数值位置
    fun myGetValue(contrast: String): Int {
        var myTarget = 0
        for ((i, num) in standard.withIndex()) {
            if (num == contrast) {
                myTarget = i
                break
            }
        }
        return myTarget
    }
}

//手牌类
open class HandPoker(cardNumber: Int) : Poker() {
    val handPoker = mutableListOf<String>()

    init {
        for (i in 1..cardNumber) {
            handPoker.add(library.removeAt(Random.nextInt(library.size)))
        }
    }
}

//玩家类
class Player(cardNumber: Int) : HandPoker(cardNumber) {
    var score: Int = 0
    private var card: String = ""
    private val input = Scanner(System.`in`)

    //展示玩家手牌
    fun display() {
        println("序号 手牌")
        for ((i, n) in this.handPoker.withIndex()) {
            println("${i + 1}  $n")
        }
    }

    //玩家出牌
    fun playerOutOfCard() {
        var choices = 0
        var goNext = 'n'

        print("选择一张要出的牌（1~${this.handPoker.size}）:")
        while (goNext == 'n') {
            choices = input.nextInt()
            if (choices <= this.handPoker.size && choices >= 1) {
                goNext = 'y'
            }else{
                print("不按要求不给过！\n又给你一次机会：")
            }
        }
        card = this.handPoker.removeAt(choices - 1)
    }

    //电脑出牌
    fun computerOutOfCard() {
        card = this.handPoker.removeAt(Random.nextInt(this.handPoker.size))
    }

    //类似strcmp的比较函数，大于返回1，小于返回0
    fun pokerCmp(moreGoal: Player) {
        if (this.myGetValue(this.card) > moreGoal.myGetValue(moreGoal.card)) {
            println("${this.card}  ${moreGoal.card}")
            println("${this.card} > ${moreGoal.card}")
            this.score += 1
        } else {
            println("${this.card}  ${moreGoal.card}")
            println("${this.card} < ${moreGoal.card}")
            moreGoal.score += 1
        }
    }
}

fun main() {
    val input = Scanner(System.`in`)
    print("请输入每个人的手牌数：")
    val cardNumber: Int = input.nextInt()
    val player = Player(cardNumber)
    val computer = Player(cardNumber)

    //主运行
    for (i in 1..cardNumber) {
        player.display()
        player.playerOutOfCard()
        computer.computerOutOfCard()
        player.pokerCmp(computer)
    }
    println("\n游戏结束\n比分为：${player.score}  ${computer.score}")
}