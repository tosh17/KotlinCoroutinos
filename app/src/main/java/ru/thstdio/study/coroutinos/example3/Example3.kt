package ru.thstdio.study.coroutinos.example3

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_example3.*
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import ru.thstdio.study.coroutinos.R
import ru.thstdio.study.coroutinos.hardwork.factorial
import ru.thstdio.study.coroutinos.hardwork.fibonacci
import ru.thstdio.study.coroutinos.util.countChar
import ru.thstdio.study.coroutinos.util.end
import ru.thstdio.study.coroutinos.util.toFormatedString

class Example3 : AppCompatActivity() {
    //https://github.com/Kotlin/kotlinx.coroutines/blob/master/docs/channels.md#channels
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_example3)
        val channel = Channel<String>()
        btn_ex3_send.setOnClickListener {
            GlobalScope.launch(Dispatchers.IO) {
                for (x in factorial()) channel.send("${x.second.toFormatedString(5)}! size = ${x.first.countChar()}")
                // channel.close()
            }
        }
        GlobalScope.launch {
            for (y in channel) {
                //delay(200)
                printMes(y)
            }
        }
    }


    fun printMes(str: String) {
        textView_ex3_result.text = str.end() + textView_ex3_result.text
    }
}
