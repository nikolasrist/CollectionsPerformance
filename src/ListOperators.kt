fun main() {
    val sourceList = 1..1_000_000_00
    val maxRounds = 5
    val sourceListShort = 1..1_000_000

    analyseAppending(sourceList, maxRounds)
    analyseRemoving(sourceListShort, maxRounds)
}

fun analyseAppending(
    sourceList: IntRange,
    maxRounds: Int,
) {
    var measuredDurationPlus = 0L
    println("Append with + operator")
    for (i in 1..maxRounds) {
        val myImmutableList = listOf<Long>()
        measuredDurationPlus +=
            measureTimeNanos {
                sourceList.forEach {
                    myImmutableList + it
                }
            }
    }

    var measuredDurationAdd = 0L
    println("Append with add function")
    for (i in 1..maxRounds) {
        val myMutableList = mutableListOf<Int>()
        measuredDurationAdd +=
            measureTimeNanos {
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

fun analyseRemoving(
    sourceList: IntRange,
    maxRounds: Int,
) {
    var measuredDurationMinus = 0L
    println("Remove with - operator")
    for (i in 1..maxRounds) {
        val myImmutableList = sourceList.toList()
        measuredDurationMinus +=
            measureTimeNanos {
                sourceList.forEach {
                    myImmutableList - it
                }
            }
    }

    var measuredDurationRemove = 0L
    println("Remove with remove function")
    for (i in 1..maxRounds) {
        val myMutableList = sourceList.toMutableList()
        measuredDurationRemove +=
            measureTimeNanos {
                sourceList.forEach {
                    myMutableList.remove(it)
                }
            }
    }

    println("Remove with - on revers list")
    var measuredDurationMinusReverse = 0L
    for (i in 1..maxRounds) {
        val myImmutableList = sourceList.toList()
        measuredDurationMinusReverse +=
            measureTimeNanos {
                sourceList.reversed().forEach {
                    myImmutableList - it
                }
            }
    }

    println("Remove with remove on revers list")
    var measuredDurationRemoveReverse = 0L
    for (i in 1..maxRounds) {
        val myMutableList = sourceList.toMutableList()
        measuredDurationRemoveReverse +=
            measureTimeNanos {
                sourceList.reversed().forEach {
                    myMutableList.remove(it)
                }
            }
    }

    val avgDurationMinus = measuredDurationMinus / maxRounds
    val avgDurationRemove = measuredDurationRemove / maxRounds
    val avgDurationMinusReverse = measuredDurationMinusReverse / maxRounds
    val avgDurationRemoveReverse = measuredDurationRemoveReverse / maxRounds

    println("Average duration minus: ${formatNanoseconds(avgDurationMinus)}")
    println("Average duration minus reverse: ${formatNanoseconds(avgDurationMinusReverse)}")
    println("Average duration remove: ${formatNanoseconds(avgDurationRemove)}")
    println("Average duration remove reverse: ${formatNanoseconds(avgDurationRemoveReverse)}")
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
