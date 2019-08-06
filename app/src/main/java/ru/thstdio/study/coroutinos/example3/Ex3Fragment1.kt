package ru.thstdio.study.coroutinos.example3

import android.view.View
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.launch
import ru.thstdio.study.coroutinos.example3.ui.main.PlaceholderFragment
import ru.thstdio.study.coroutinos.hardwork.factorial
import ru.thstdio.study.coroutinos.util.countChar
import ru.thstdio.study.coroutinos.util.toFormatedString

class Ex3Fragment1 : PlaceholderFragment() {
    var channel = Channel<String>()
    override fun btnSendClick() {
        GlobalScope.launch(Dispatchers.IO) {
            for (x in factorial()) channel.send("${x.second.toFormatedString(5)}! size = ${x.first.countChar()}")
            // channel.close()
        }
    }

    override fun initView(view: View) {
        GlobalScope.launch {
            for (y in channel) {
                //delay(200)
                printMes(y)
            }
        }
    }
}