package ru.thstdio.study.coroutinos.example1

import android.animation.AnimatorInflater
import android.animation.AnimatorSet
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_example1.*
import kotlinx.coroutines.*
import ru.thstdio.study.coroutinos.R
import ru.thstdio.study.coroutinos.hardwork.BigData

class Example1Activity : AppCompatActivity(), CoroutineScope by MainScope() {
    override fun onDestroy() {
        super.onDestroy()
        cancel() // CoroutineScope.cancel
    }

    val startHardWork = 1
    val finishHardWork = 1500
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_example1)
        btn_test1_start.setOnClickListener { startProgress() }
        btn_example1_hard_work.setOnClickListener {
            textView_hard_work_result.text = ""
            val data = hardWork(startHardWork, finishHardWork).countChar()
            textView_hard_work_result.text = data
        }
        btn_example1_coroutine_hard_work.setOnClickListener {
            textView_hard_work_result.text = ""
            GlobalScope.launch {
                val data =hardWorkCoroutinos(startHardWork, finishHardWork).countChar()
                textView_hard_work_result.text = data
            }

        }
        btn_example1_coroutine_hard_work_4_stream.setOnClickListener {
            textView_hard_work_result.text = ""
            hardworkTo4Stream()
        }
        anim()
    }

    private fun anim() {

        val set = AnimatorInflater.loadAnimator(this, R.animator.start_roteded) as AnimatorSet
        set.setTarget(imageView_ex1_star)
        set.start()
    }

    private fun startProgress() {
        val job = GlobalScope.launch(Dispatchers.Main) {
            // launch coroutine in the main thread
            progressBar1.setProgressTintList(ColorStateList.valueOf(Color.RED))
            for (i in 1..100) {
                progressBar1.progress = i
                delay(32)
            }
            progressBar1.progressTintList = ColorStateList.valueOf(Color.GREEN)
        }
        btn_test1_stop.setOnClickListener { job.cancel() }
    }

    private fun hardWork(start: Int, finish: Int): String {
        val factor = BigData()
        for (i in start..finish) {
            factor.data = factor.multiply(i)
        }
        return factor.data

    }

    suspend fun hardWorkCoroutinos(start: Int, finish: Int): String {
        val deferred = async(Dispatchers.IO) {
            hardWork(start, finish)
        }
        return withContext(Dispatchers.Main) {
            val data = deferred.await()
            return@withContext data
        }

    }

    private fun hardworkTo4Stream() {
        val step = (finishHardWork / 4)
        var result1 = "0"
        var result2 = "0"
        var result3 = "0"
        var result4 = "0"
        val job1 = GlobalScope.launch {
            result1 = hardWorkCoroutinos(startHardWork, step - 1)
        }
        val job2 = GlobalScope.launch {
            result2 = hardWorkCoroutinos(step, 2 * step - 1)
        }
        val job3 = GlobalScope.launch {
            result3 = hardWorkCoroutinos(step * 2, 3 * step - 1)
        }
        val job4 = GlobalScope.launch {
            result4 = hardWorkCoroutinos(3 * step, finishHardWork)
        }
        GlobalScope.launch {
            job1.join()
            job2.join()
            job3.join()
            job4.join()
            val factor = BigData(result1)
            factor.data = factor.multiply(result2)
            factor.data = factor.multiply(result3)
            factor.data = factor.multiply(result4)
            textView_hard_work_result.text = factor.data.countChar()
        }
    }

    private fun printToast(msg: String) {
        Toast.makeText(baseContext, msg, Toast.LENGTH_LONG).show()
    }
}

private fun String.countChar(): String {
    return this.length.toString()
}
