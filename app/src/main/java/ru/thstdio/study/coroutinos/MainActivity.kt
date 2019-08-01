package ru.thstdio.study.coroutinos

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.channels.SendChannel
import kotlinx.coroutines.channels.actor
import kotlinx.coroutines.channels.produce
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import ru.thstdio.study.coroutinos.hardwork.BigData


class MainActivity : AppCompatActivity() {

    private lateinit var printChannel: SendChannel<Float>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initChanel()
        btn1.setOnClickListener { ckickBtn1() }

    }

    private fun initChanel() = runBlocking {
        launch {
            printChannel = actor {
                // Переменная channel представляет канал внутри функции
                for (f in channel) {
                    println("actor: $f ${Thread.currentThread().name}")
                    Log.d("Croutine", f.toString())
                    showProgress(1, f)
                }
            }
        }
    }


    var currentProgress = 0
    fun showProgress(stream: Int, progress: Float) {
        when (stream) {
            1 -> {
                if (progress.toInt() > currentProgress) {
                    currentProgress = progress.toInt()
                    progressBar1.setProgress(progress.toInt())
                    Log.d("setProgress", progress.toInt().toString())
                }

            }
        }
    }


    fun ckickBtn1() = runBlocking<Unit> {
        text_out_stream.text = ""
        showProgress(1, 0f)
        produce<String>(Dispatchers.IO) {

            val factor = BigData()
            val j = 10000
            for (i in 1..j) {
                factor.data = factor.multiply(i)
                printChannel.send((100 * i).toFloat() / j)
            }

            //  text_out_stream.text="Факториал  $j!=+${factor.data.length}"
        }
    }
}
