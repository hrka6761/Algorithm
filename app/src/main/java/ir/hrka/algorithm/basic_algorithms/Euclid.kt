package ir.hrka.algorithm.basic_algorithms

import android.util.Log

/**
 * The Euclidean algorithm is a way to find the greatest common divisor of two positive integers. GCD of two numbers is the largest number that divides both of them.
 */

const val EUCLID_TAG = "euclid GCD"


infix fun Int.gcdWith(int: Int) {

    var smaller: Int
    var bigger = if (int > this) {
        smaller = this
        int
    } else {
        smaller = int
        this
    }

    Log.i(EUCLID_TAG, "bigger: $bigger")
    Log.i(EUCLID_TAG, "smaller: $smaller")

    if (smaller == 0) {
        Log.i(EUCLID_TAG, "GCD: $smaller")
        return
    }

    var remaining: Int = smaller

    while (remaining != 0) {
        remaining = bigger % smaller
        bigger = smaller
        if (remaining != 0) smaller = remaining
    }

    Log.i(EUCLID_TAG, "euclidGCDWith: $smaller")
}