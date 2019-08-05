package ru.thstdio.study.coroutinos.util

import java.io.File
import java.io.FileFilter
import java.util.regex.Pattern

fun getNumCores(): Int {
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