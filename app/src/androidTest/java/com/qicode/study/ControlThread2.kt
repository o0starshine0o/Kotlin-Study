package com.qicode.study

import kotlin.concurrent.thread

const val final = 100000

var i = 0
val lock = Object()

fun main() {
//    val start = System.currentTimeMillis()
//    thread { printOdd(start) }
//    thread { printEven(start) }
    val start = System.currentTimeMillis()
    thread { printOddSync(start) }
    thread { printEvenSync(start) }
}

fun printOdd(start:Long) {
    while (i < final) {
        synchronized(lock) {if (i % 2 == 1) println("${Thread.currentThread().name}:${i++}")}
    }
    println("${Thread.currentThread().name} cost time:${System.currentTimeMillis() - start}")
}

fun printEven(start:Long) {
    while (i < final) {
        synchronized(lock) {if (i % 2 == 0) println("${Thread.currentThread().name}:${i++}")}
    }
    println("${Thread.currentThread().name} cost time:${System.currentTimeMillis() - start}")
}

fun printOddSync(start:Long) {
    while (i <= final) {
        synchronized(lock) {
            println("${Thread.currentThread().name}:${i++}")
            lock.notify()
            lock.wait()
        }
    }
    synchronized(lock) {
        lock.notify()
    }
    println("${Thread.currentThread().name} cost time:${System.currentTimeMillis() - start}")
}

fun printEvenSync(start:Long) {
    while (i <= final) {
        synchronized(lock) {
            println("${Thread.currentThread().name}:${i++}")
            lock.notify()
            lock.wait()
        }
    }
    synchronized(lock) {
        lock.notify()
    }
    println("${Thread.currentThread().name} cost time:${System.currentTimeMillis() - start}")
}