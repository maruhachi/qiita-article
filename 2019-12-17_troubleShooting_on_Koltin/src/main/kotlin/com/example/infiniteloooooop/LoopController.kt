package com.example.infiniteloooooop;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import java.io.File
import java.time.LocalDateTime

@Controller
class LoopController(
        var mm: MutableMap<String, String>
) {

    @ResponseBody
    @GetMapping("/")
    fun hello(): Map<String, String>{
        return mapOf("hello" to "hell")
    }

    @ResponseBody
    @GetMapping("/hell")
    fun hell(): String{
        val res = Oom()
        mm.put(LocalDateTime.now().toString(), res)
        return "ok?"
    }

    fun Oom(): String{
        val inputStream = File("/Users/ykonno/test.txt").inputStream()
        val reader = inputStream.bufferedReader()
        return reader.readText()
    }
}
