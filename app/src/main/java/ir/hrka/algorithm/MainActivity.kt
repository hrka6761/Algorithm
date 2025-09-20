package ir.hrka.algorithm

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.enableEdgeToEdge
import ir.hrka.algorithm.compression_algorithms.HuffmanCoding

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        HuffmanCoding compress "HAMIDREZA KARAMI"
    }
}