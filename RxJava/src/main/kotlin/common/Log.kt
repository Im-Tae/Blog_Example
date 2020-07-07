package common

class Log {
    companion object {
        fun it(obj: Any) {
            when (CommonUtils.startTime) {
                null -> println("${Thread.currentThread().name} | value = $obj")
                else -> {
                    val time = System.currentTimeMillis() - CommonUtils.startTime!!
                    println("${Thread.currentThread().name} | $time | value = $obj")
                }
            }
        }

        fun d(obj: Any) {
            println("${Thread.currentThread().name} | debug = $obj")
        }
    }
}