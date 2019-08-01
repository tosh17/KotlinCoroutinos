package ru.thstdio.study.coroutinos

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import ru.thstdio.study.coroutinos.hardwork.BigData


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btn1.setOnClickListener{
            text_out_stream.text=""
            val factor = BigData()
            val j: Int
            j = 700
            for (i in 1..j) {
                factor.data = factor.multiply(i)
                text_out_stream.text="Current position ${100*i/j}%"
            }
            text_out_stream.text="Факториал  $j!=+${factor.data.length}"
        }
    }

    fun showProgress(stream:Int,progress:Float)=runBlocking<Unit> {

        when(stream){
            1-> progressBar1.setProgress(progress.toInt())
        }

    }
}
