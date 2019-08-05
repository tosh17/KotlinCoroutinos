package ru.thstdio.study.coroutinos.example2

import android.animation.AnimatorInflater
import android.animation.AnimatorSet
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_example2.*
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.actor
import kotlinx.coroutines.channels.produce
import ru.thstdio.study.coroutinos.R
import ru.thstdio.study.coroutinos.hardwork.factorial
import ru.thstdio.study.coroutinos.util.countChar
import ru.thstdio.study.coroutinos.util.end
import ru.thstdio.study.coroutinos.util.getDeltaFromCurrentTime
import ru.thstdio.study.coroutinos.util.toFormatedString

class Example2 : AppCompatActivity(), CoroutineScope by MainScope() {
    override fun onDestroy() {
        super.onDestroy()
        cancel() // CoroutineScope.cancel
    }

    private var job: ReceiveChannel<String>? = null
    var printChannel = initActor()
    val finish = 5000
    val processStep = 100
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_example2)
        anim()
        btn_ex2_send.setOnClickListener {
            startTime = System.currentTimeMillis()
            job?.let { it.cancel() }
            job = produce(Dispatchers.IO) {
                val iterator = factorial().iterator()
                for (i in 1..(finish / processStep)) {
                    println("producer1: $i ${Thread.currentThread().name}")
                    //  iterator.next()
                    repeat(processStep - 1) { iterator.next() }
                    printChannel.send(iterator.next())
                }
                println("producer1: finish ${Thread.currentThread().name}")
                printChannel.close()
            }
        }
        btn_ex2_open.setOnClickListener { printChannel = initActor() }
        btn_ex2_close.setOnClickListener { printChannel.close() }
    }

    val listTime: MutableList<Long> = ArrayList()
    var startTime = 0L
    private fun initActor() =
        actor<Pair<String, Int>> {
            // Переменная channel представляет канал внутри функции
            textView_result_ex2_1.text = ""


            for (s in channel) {
                listTime.add(System.currentTimeMillis())
                val str =
                    "${startTime.getDeltaFromCurrentTime()}  ${s.second.toFormatedString(4)} - ${s.first.countChar()}".end()
                textView_result_ex2_1.text = str + textView_result_ex2_1.text
            }
            textView_result_ex2_1.text = analizeDeltatime().end() + textView_result_ex2_1.text
            analizeDeltatime()
        }


    private fun anim() {
        val set = AnimatorInflater.loadAnimator(this, R.animator.start_roteded) as AnimatorSet
        set.setTarget(imageView_ex2)
        set.start()
    }

    fun analizeDeltatime(): String {
        val STEP_GROUP = 10
        val summ = listTime.last() - listTime.first()
        var delta = listTime.first()
        var lastItem = 0
        var lastPersent = 0
        val count = listTime.size
        val step = Math.ceil(count.toDouble() / STEP_GROUP).toInt()


        val str = StringBuffer("")
        for (i in (step - 1) until count step step) {
            val itemNumber = if (i > (count - step)) count - 1 else i
            val persentItem = (itemNumber + 1) * 100 / count
            val personTime = (listTime[itemNumber] - listTime[lastItem]) * 100 / summ
            str.append("from ${lastPersent}% - ${persentItem}% items  -- ${personTime}% time \n")
            lastItem = itemNumber
            lastPersent = persentItem
        }
        return str.toString()
    }
}