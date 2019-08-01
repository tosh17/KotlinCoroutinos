package ru.thstdio.study.coroutinos.hardwork

class BigData {

    var data: String = "1"

    constructor()
    constructor(data: String) {
        this.data = data
    }

    constructor(data: Int) {
        this.data = data.toString()
    }

    fun multiply(x: Int): String {
        val rezult = Integer.toString(x)
        return multiply(rezult)
    }

    fun multiply(x: String): String {
        var rezult = ""
        var xlenght = x.length
        val data_l = data.length
        var n = 0

        for (i in xlenght - 1 downTo 1) {
            if (Character.getNumericValue(x[i]) == 0)
                n++
            else
                break
        }

        xlenght -= n
        val xn = x


        val int_rezult = IntArray(xlenght + data_l)
        var tx: Int
        var ty: Int
        var temp: Int
        var xy: Int
        var xy1: Int
        var xy2: Int
        for (i in 0 until xlenght) {
            for (j in 0 until data_l) {
                tx = xlenght - i - 1
                ty = data_l - j - 1
                //        System.out.println(x.charAt(tx)+"  "+y.charAt(ty));
                val xi = Character.getNumericValue(x[tx])
                val yj = Character.getNumericValue(data[ty])
                xy = xi * yj
                //       System.out.println("Произведение X|"+tx+'|'+'*'+"Y|"+ty+"|=" +xi +"*"+yj+"="+xy);
                if (xy > 9) {
                    xy2 = xy % 10
                    xy1 = (xy - xy2) / 10
                } else {
                    xy1 = 0
                    xy2 = xy
                }
                int_rezult[tx + ty + 1] += xy2
                int_rezult[tx + ty] += xy1
            }

            for (i2 in xlenght + data_l - 1 downTo 1) {
                if (int_rezult[i2] > 9) {
                    temp = int_rezult[i2] % 10
                    int_rezult[i2 - 1] += (int_rezult[i2] - temp) / 10
                    int_rezult[i2] = temp
                }
            }
        }

        temp = 0
        for (i in 0 until xlenght + data_l) {
            if (int_rezult[i] > 0) temp = 1
            if (temp > 0) rezult += int_rezult[i]
        }
        if (n > 0) for (i in 0 until n) rezult += 0
        return rezult
    }

}