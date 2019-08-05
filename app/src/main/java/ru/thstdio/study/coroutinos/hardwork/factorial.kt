package ru.thstdio.study.coroutinos.hardwork

import ru.thstdio.study.coroutinos.hardwork.BigData

fun factorial(): Sequence<Pair<String,Int>> {
    // fibonacci terms
    // 0, 1, 1, 2, 3, 5, 8, 13, 21, 34, 55, 89, 144, 233, 377, 610, 987, 1597, 2584, 4181, 6765, 10946, ...
    return generateSequence(Pair("1", 1), { Pair(BigData(it.first).multiply(it.second+1), it.second+1) })
}