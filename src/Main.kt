fun main() {
    val sourceList = 1..1_000_000_00
    val maxRounds = 5

    var measuredDurationPlus = 0L
    println("Append with + operator")
    for (i in 1..maxRounds) {
        val myImmutableList = listOf<Long>()
        measuredDurationPlus += measureTimeNanos {
            sourceList.forEach {
                myImmutableList + it
            }
        }
    }

    var measuredDurationAdd = 0L
    println("Append with add function")
    for (i in 1..maxRounds) {
        val myMutableList = mutableListOf<Int>()
        measuredDurationAdd += measureTimeNanos {
            sourceList.forEach {
                myMutableList.add(it)
            }
        }
    }

    val avgDurationPlus = measuredDurationPlus / maxRounds
    val avgDurationAdd = measuredDurationAdd / maxRounds

    println("Average duration plus: ${formatNanoseconds(avgDurationPlus)}")
    println("Average duration add: ${formatNanoseconds(avgDurationAdd)}")
}

fun measureTimeNanos(block: () -> Unit): Long {
    val start = System.nanoTime()
    block()
    return System.nanoTime() - start
}

fun formatNanoseconds(nanos: Long): String {
    val seconds = nanos / 1_000_000_000.0
    val minutes = seconds / 60.0
    return "%.3f seconds".format(seconds)
}