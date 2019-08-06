package ru.thstdio.study.coroutinos.example3

import android.view.View
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.BroadcastChannel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.channels.consumeEach
import ru.thstdio.study.coroutinos.example3.ui.main.PlaceholderFragment
import ru.thstdio.study.coroutinos.hardwork.factorial
import ru.thstdio.study.coroutinos.hardwork.fibonacci
import ru.thstdio.study.coroutinos.util.countChar

class Ex3Fragment2 : PlaceholderFragment() {

    var channel = Channel<String>()
    override fun btnSendClick() {
        GlobalScope.launch {

            try {
                val broadcast = BroadcastChannel<String>(1)
                broadcast.offer("one")
                broadcast.offer("two")
                // now launch a coroutine to print the most recent update
                launch(Dispatchers.Main) {
                    // use the context of the main thread for a coroutine
                    broadcast.consumeEach { printMes(it) }
                }

                launch(Dispatchers.IO) { for (f in factorial()) broadcast.send("offer  " + f.first.countChar()) }

                launch(Dispatchers.IO) {
                    for (f in factorial().take(10000)) {
                        delay(500)
                        broadcast.send("send  " + f.first.countChar())}
                }
                broadcast.offer("three")
                broadcast.offer("four")
                //      yield() // yield the main thread to the launched coroutine
                //   broadcast.close() // now close the broadcast channel to cancel the consumer, too
            } catch (e: Exception) {
                printMes(e.toString())
            }
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