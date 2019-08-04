package ru.thstdio.study.coroutinos.example1

import android.animation.AnimatorInflater
import android.animation.AnimatorSet
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.Editable
import android.text.TextWatcher
import android.widget.ArrayAdapter
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_example1.*
import kotlinx.coroutines.*
import ru.thstdio.study.coroutinos.R
import ru.thstdio.study.coroutinos.hardwork.BigData
import java.io.File
import java.io.FileFilter
import java.util.regex.Pattern


class Example1Activity : AppCompatActivity(), CoroutineScope by MainScope() {
    override fun onDestroy() {
        super.onDestroy()
        cancel() // CoroutineScope.cancel
    }

    val startHardWork = 1
    var finishHardWork = 1500
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_example1)
        initSpiner(getNumCores())
        initFinish()
        btn_test1_start.setOnClickListener { startProgress() }
        btn_example1_hard_work.setOnClickListener {
            val startTime = System.currentTimeMillis()
            val data = hardWork(startHardWork, finishHardWork)
            textView_hard_work_result.append(generateResult(data, startTime).end())
        }
        btn_example1_coroutine_hard_work.setOnClickListener {
            GlobalScope.launch {
                val startTime = System.currentTimeMillis()
                val data = hardWorkCoroutinos(startHardWork, finishHardWork)
                textView_hard_work_result.append(generateResult(data, startTime).end())
            }

        }
        btn_example1_coroutine_hard_work_x_stream.setOnClickListener { hardworkToXCore() }
        btn_example1_coroutine_hard_work_status.setOnClickListener { GlobalScope.launch { hardworkStatus() } }
        anim()
    }


    private fun initFinish() {
        editText_ex1_factorial.setText(finishHardWork.toString())
        editText_ex1_factorial.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {

            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (p0.toString() == "") finishHardWork = 1
                else p0.let { finishHardWork = it.toString().toInt() }
            }
        })
    }

    private fun initSpiner(core: Int) {
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, (1..4 * core).toMutableList())
        // Определяем разметку для использования при выборе элемента
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner_exm1.adapter = adapter

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

    private fun hardWork(start: Int, finish: Int, core: Int = 1): String {
        val factor = BigData()
        for (i in start..finish step core) {
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

    suspend fun hardWorkCoroutinos(start: Int, finish: Int, core: Int = 1): String {
        val deferred = async(Dispatchers.IO) {
            hardWork(start, finish, core)
        }
        return withContext(Dispatchers.Main) {
            val data = deferred.await()
            return@withContext data
        }
    }

    private fun hardworkToXCore() {
        printStart()
        val coreNumber = spinner_exm1.selectedItemPosition + 1
        val startTime = System.currentTimeMillis()
        val result: MutableList<String> = ArrayList()
        val job: MutableList<Job> = ArrayList()

        for (core in 1..coreNumber) {
            job.add(GlobalScope.launch {
                result.add(hardWorkCoroutinos(core, finishHardWork, coreNumber))
            })
        }
        GlobalScope.launch {
            for (core in 0 until coreNumber) job[core].join()

            val factor = BigData(result[0])
            for (core in 1 until coreNumber) factor.data = factor.multiply(result[core])

            val str = generateResult(factor.data, startTime, coreNumber)
            textView_hard_work_result.append(str.end())
        }
    }

    private suspend fun hardworkStatus() {
        printStart()
        val startTime = System.currentTimeMillis()
        val start = startHardWork
        val finish = finishHardWork
        val factor = BigData()

        val deferred = async(Dispatchers.IO) {
            for (i in start..finish) {
                factor.data = factor.multiply(i)
                launch(Dispatchers.Main) {
                    progressBar1.progress = (i*100) / finish
                }
            }
        }
        withContext(Dispatchers.Main) {
            val data = deferred.await()

        }
        val str = generateResult(factor.data, startTime)
        textView_hard_work_result.append(str.end())
    }

    private fun printStart() {
        textView_hard_work_result.append("--> ")
    }

    private fun generateResult(data: String): String = "${finishHardWork}!.lenght = ${data.countChar()}  "
    private fun generateResult(data: String, startTime: Long): String =
        generateResult(data) + "calc time = ${startTime.getDeltaFromCurrentTime()}"

    private fun generateResult(data: String, startTime: Long, coreNumber: Int): String =
        generateResult(data, startTime) + " core=${coreNumber}"

    private fun printToast(msg: String) {
        Toast.makeText(baseContext, msg, Toast.LENGTH_LONG).show()
    }

    private fun getNumCores(): Int {
        //Private Class to display only CPU devices in the directory listing
        class CpuFilter : FileFilter {
            override fun accept(pathname: File): Boolean {
                //Check if filename is "cpu", followed by one or more digits
                return Pattern.matches("cpu[0-9]+", pathname.getName())
            }
        }

        try {
            //Get directory containing CPU info
            val dir = File("/sys/devices/system/cpu/")
            //Filter to only list the devices we care about
            val files = dir.listFiles(CpuFilter())
            //Return the number of cores (virtual CPU devices)
            return files.size
        } catch (e: Exception) {
            //Default to return 1 core
            return 1
        }

    }
}

private fun Long.getDeltaFromCurrentTime(): String {
    val currentTime = (System.currentTimeMillis() - this) / 1000
    val minute = currentTime / 60
    val secunde = currentTime % 60

    return java.lang.String.format("%02d:%02d", minute, secunde)

}

private fun String.countChar(): String {
    return this.length.toString()
}

private fun String.end(): String {
    return this + "\n"
}
