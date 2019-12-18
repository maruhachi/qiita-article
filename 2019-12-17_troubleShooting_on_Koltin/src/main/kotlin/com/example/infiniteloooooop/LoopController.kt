package com.example.infiniteloooooop

import kotlinx.coroutines.*
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ResponseBody
import java.io.File
import java.lang.Thread.sleep
import java.time.LocalDateTime

@Controller
class LoopController(
        var mm: MutableMap<String, String>
) {
    companion object {
        @JvmStatic
        var lockerA: IntArray = intArrayOf(1, 2, 3)
        @JvmStatic
        var lockerB: IntArray = intArrayOf(3, 2, 1)
    }

    @ResponseBody
    @GetMapping("/")
    fun hello(): Map<String, String> {
        return mapOf("hello" to "hell")
    }

    @ResponseBody
    @GetMapping("/hell")
    fun hell(): String {
        val res = Oom()
        mm.put(LocalDateTime.now().toString(), res)
        return "ok?"
    }

    fun Oom(): String {
        val inputStream = File("test.txt").inputStream()
        val reader = inputStream.bufferedReader()
        return reader.readText()
    }

    @ResponseBody
    @GetMapping("paJ")
    fun paJ(): String {
        // ジャバジャバしたデッドロック
        val t1: Thread1 = Thread1()
        val t2: Thread2 = Thread2()
        t1.start()
        t2.start()
        return "ジャバジャバ"
    }

    @ResponseBody
    @GetMapping("paK")
    fun paK(): String {
        doing()

        return "コトコト"
    }
    
    fun doing() = runBlocking<Unit>{
        // コトコトしたデッドロック
        val d1 = async {
            fun th1(){
                while (true) {
                    synchronized(LoopController.lockerA) {
                        System.err.println("スレッド1:ロックB取得")
                        synchronized(LoopController.lockerB) {
                            System.err.println("スレッド1:ロックB取得")
                            for (i in 0..2) {
                                LoopController.lockerA[i] += LoopController.lockerB[i]
                                LoopController.lockerB[i] += LoopController.lockerA[i]
                            }
                        }
                        try {
                            sleep(6)
                        } catch (ex: InterruptedException) {
                            // no-op
                        }
                    }
                }
            }
        }
        val d2 = async {
            fun th2(){
                while (true) {
                    synchronized(LoopController.lockerB) {
                        System.err.println("スレッド2:ロックB取得")
                        synchronized(LoopController.lockerA) {
                            System.err.println("スレッド2:ロックA取得")
                            for (i in 0..2) {
                                LoopController.lockerA[i] += LoopController.lockerB[i]
                                LoopController.lockerB[i] += LoopController.lockerA[i]
                            }
                        }
                        try {
                            sleep(5)
                        } catch (ex: InterruptedException) {
                            // no-op
                        }
                    }
                }
            }
        }
        d1.start()
        d2.start()
    }

}

class Thread1 : Thread() {
    override fun run() {
        while (true) {
            synchronized(LoopController.lockerA) {
                System.err.println("スレッド1:ロックA取得")
                synchronized(LoopController.lockerB) {
                    System.err.println("スレッド1:ロックB取得")
                    for (i in 0..2) {
                        LoopController.lockerA[i] += LoopController.lockerB[i]
                        LoopController.lockerB[i] += LoopController.lockerA[i]
                    }
                }
                try {
                    sleep(6)
                } catch (ex: InterruptedException) {
                    // no-op
                }
            }
        }
    }
}

class Thread2 : Thread() {
    override fun run() {
        while (true) {
            synchronized(LoopController.lockerB) {
                System.err.println("スレッド2:ロックB取得")
                synchronized(LoopController.lockerA) {
                    System.err.println("スレッド2:ロックA取得")
                    for (i in 0..2) {
                        LoopController.lockerA[i] += LoopController.lockerB[i]
                        LoopController.lockerB[i] += LoopController.lockerA[i]
                    }
                }
                try {
                    sleep(5)
                } catch (ex: InterruptedException) {
                    // no-op
                }
            }
        }
    }
}