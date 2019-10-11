package com.qicode.study

import java.util.concurrent.CountDownLatch
import java.util.concurrent.CyclicBarrier
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit
import kotlin.concurrent.thread


const val mess = 5
fun main() {
//    print()
//    print2()
    print3()
}

fun print() {
    val barrier = CyclicBarrier(mess) {
        for (i in mess until mess * 2) println("${Thread.currentThread().name}:$i")
    }

    for (i in 0 until mess) {
        thread {
            println("${Thread.currentThread().name}:$i")
            barrier.await()
        }
    }
}

fun print2() {
    val countDownLatch = CountDownLatch(mess)

    for (i in 0 until mess) {
        thread {
            println("${Thread.currentThread().name}:$i")
            countDownLatch.countDown()
        }
    }

    countDownLatch.await()

    for (i in mess until mess * 2) println("${Thread.currentThread().name}:$i")
}

fun print3() {
    val pool = Executors.newFixedThreadPool(mess)
    for (i in 0 until mess) pool.submit { println("${Thread.currentThread().name}:$i") }
    pool.shutdown()

    pool.awaitTermination(3000, TimeUnit.MILLISECONDS)

    while (true){
        if (pool.isTerminated) {
            for (i in mess until mess * 2) println("${Thread.currentThread().name}:$i")
            break
        }
    }
}