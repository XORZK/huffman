# Huffman Coding 
A Huffman Coding implementation in Java, using built from scratch using mainly:
- Priority Queue
- Binary Tree

Huffman Coding is a method of representation information which is often used within compression, and compression software such as gzip.

## Details

Within Huffman Coding, bytes read from an input file are represented using Nodes within a Binary Tree.

The position of a Node within the Binary Tree depends on the frequency of the Byte represented. If the byte is highly prevalent within the uncompressed file, the respective Node will be closer to the root of the Binary Tree.

Each Node within the Binary Tree can then be represented as a seqeunce of bits based on their respective location within the Binary Tree.

Given these bit representation of Nodes within the Binary Tree, the bit sequence can be written to a compressed output file.

The Huffman Coding algorithm is more efficient for Files with largely repeated bytes, such as text files.