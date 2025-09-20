package ir.hrka.algorithm.compression_algorithms

import android.util.Log
import java.util.PriorityQueue

/**
 * Huffman coding is a lossless data compression algorithm.The idea is to assign variable-length codes to input characters, lengths of the assigned codes are based on the frequencies of corresponding characters.The variable-length codes assigned to input characters are Prefix Codes,means the codes (bit sequences) are assigned in such a way that the code assigned to one character is not the prefix of code assigned to any other character.
 *
 * Applications of Huffman Coding:
 *  * They are used for transmitting fax and text.
 *  * They are used by conventional compression formats like PKZIP, GZIP, etc.
 *  * Multimedia codecs like JPEG, PNG, and MP3 use Huffman encoding(to be more precise the prefix codes).
 *
 * It is useful in cases where there is a series of frequently occurring characters.
 *
 * * Time complexity: O(nlogn) where n is the number of unique characters
 * * Space complexity :- O(n)
 */

object HuffmanCoding {

    const val HUFFMAN_CODING_TAG = "Huffman Coding"


    private fun buildHuffmanTree(text: String): Node {
        Log.i(HUFFMAN_CODING_TAG, "Text: $text")
        // Create map of characters and their frequencies. Map<Char, Int>
        val charMap = text.groupingBy { it }.eachCount()
        Log.i(HUFFMAN_CODING_TAG, "charMap: $charMap")
        // Create queue of above maps.
        // Order based on the frequency. compareBy { it.frequency }
        val charQueue = PriorityQueue<Node>(compareBy { it.frequency })
        for ((char, frequency) in charMap) {
            charQueue.add(Node.Leaf(char, frequency))
        }
        Log.i(
            HUFFMAN_CODING_TAG,
            "charPriorityQueue: ${charQueue.map { it.name + "=" + it.frequency }}"
        )

        Log.i(
            HUFFMAN_CODING_TAG,
            "Iterate and create internal nodes: ${charQueue.map { it.name + " = " + it.frequency + if (it is Node.InternalNode) " (LeftNode = " + it.leftNode.name + "->" + it.leftNode.frequency + " ,RightNode = " + it.rightNode.name + "->" + it.rightNode.frequency + ")" else "" }}"
        )
        while (charQueue.size > 1) {
            val leftNode = charQueue.poll()!!
            val rightNode = charQueue.poll()!!

            charQueue.add(Node.InternalNode(leftNode, rightNode))

            Log.i(
                HUFFMAN_CODING_TAG,
                "Iterate and create internal nodes: ${charQueue.map { it.name + " = " + it.frequency + if (it is Node.InternalNode) " (LeftNode = " + it.leftNode.name + "->" + it.leftNode.frequency + " ,RightNode = " + it.rightNode.name + "->" + it.rightNode.frequency + ")" else "" }}"
            )
        }

        return charQueue.poll()!!
    }

    private fun generateCharactersCodes(
        node: Node,
        prefix: String = "",
        map: MutableMap<String, String> = mutableMapOf()
    ): Map<String, String> {
        when (node) {
            is Node.Leaf -> {
                Log.i(HUFFMAN_CODING_TAG, "generated code for ${node.name} = $prefix")
                map[node.name] = prefix
            }

            is Node.InternalNode -> {
                generateCharactersCodes(node.leftNode, prefix + "0", map)
                generateCharactersCodes(node.rightNode, prefix + "1", map)
            }
        }

        return map
    }

    infix fun compress(text: String) {
        val tree = buildHuffmanTree(text)
        val codesMap = generateCharactersCodes(tree)

        val totalGeneratedCodes = text.map { codesMap[it.toString()] }.joinToString("")

        Log.i(HUFFMAN_CODING_TAG, "total generated code for $text = $totalGeneratedCodes")
    }
}


sealed class Node(val name: String, val frequency: Int) {
    class InternalNode(val leftNode: Node, val rightNode: Node) :
        Node("Internal Node", leftNode.frequency + rightNode.frequency)

    class Leaf(char: Char, freq: Int) : Node(char.toString(), freq)
}