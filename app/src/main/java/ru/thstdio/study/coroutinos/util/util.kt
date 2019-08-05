package ru.thstdio.study.coroutinos.util

fun Long.getDeltaFromCurrentTime(): String {
    val currentTime = (System.currentTimeMillis() - this) / 1000
    val minute = currentTime / 60
    val secunde = currentTime % 60

    return java.lang.String.format("%02d:%02d", minute, secunde)

}

fun String.countChar(): String {
    return this.length.toString()
}

fun String.end(): String {
    return this + "\n"
}

fun Number.toFormatedString(count: Int) :String {
    val format = "%0${count}d"
    return java.lang.String.format(format, this)
}