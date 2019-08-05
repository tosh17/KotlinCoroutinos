package ru.thstdio.study.coroutinos

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.runBlocking
import ru.thstdio.study.coroutinos.example1.Example1Activity
import ru.thstdio.study.coroutinos.example2.Example2
import ru.thstdio.study.coroutinos.hardwork.BigData


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        startExample(1)
        setContentView(R.layout.activity_main)

    }

    private fun startExample(number: Int) {
        val next = Intent(
            this,
            when (number) {
                1 -> Example1Activity::class.java
                2 -> Example2::class.java
                else -> Example1Activity::class.java
            }
        )
        startActivity(next)
        finish()

    }

}
