import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.FileOutputStream;

/** A Huffman Coding Data Structure which compresses a File using the Huffman Coding Compression Algorithm.
 * @author Henry Wang
*/

public class HuffmanCoding {
  private String sequence, filename;
  private HuffmanTree<Byte> encodingTree;
  private int padding = 0;
  /** fileBytes: The bytes read from the input file.
   *  byteSequence: The byte sequence for the compressed file.
   */
  private byte[] byteSequence, fileBytes;

  /** A constructor which initializes a new Huffman Coding object. */
  public HuffmanCoding() {
    this.encodingTree = null;
  }

  /** A constructor which reads, and compresses a File using the Huffman Coding Compression Method. 
   * @param fn The name of the file to be compressed
  */
  public HuffmanCoding(String fn) {
    this.compressFile(fn);
  }

  /** A method which compresses a File using the Huffman Coding Compression Method. This is done through several other methods, used to generate the Huffman Binary Tree, and generate the byte sequence.
   * @param fn The name of the file to be compressed.
  */
  public void compressFile(String fn) {
    File f = new File(fn);
    this.filename = fn;
    this.fileBytes = new byte[(int) f.length()];

    /** Generates a frequency table of bytes from the File Input Stream. This frequency table is then used to generate the Binary Tree based on the weighting (prevalence) of different bytes. 
     * Since bytes range from [-128, 127], a shift of 128 is applied to the index.
    */
    try (FileInputStream input = new FileInputStream(f)) {
      input.read(this.fileBytes);
      int[] freq = new int[257];

      /** Generate a frequency table for the bytes within the File.  */
      for (int i = 0; i < this.fileBytes.length; i++) {
        freq[(int) (this.fileBytes[i]) + 128]++;
      }

      this.initializeTree(freq);
      this.bitSequence();
      this.initializeBytes();
    } catch (IOException e)  {
      System.out.println(String.format("Could not read file: %s", fn));
    }

  }

  /** Generates a Huffman Binary Tree of Bytes using a frequency table of Bytes.
   * @param frequencies The frequency table of bytes.
   */
  private void initializeTree(int[] frequencies) {
    /** If the frequency table does not exist, do not generate the Huffman Binary Tree. */
    if (frequencies == null) {
      return;
    }

    /** A Priority Queue is used to merge Binary Trees in order to generate the final Binary Tree.  */
    PriorityQueue<HuffmanTree<Byte>> q = new PriorityQueue<HuffmanTree<Byte>>();


    for (int i = 0; i < frequencies.length; i++) {
      /** If the frequency of a Byte is more than 0, meaning that the Byte is found within the File, add a Binary Tree consisting of a singular Node to the Priority Queue, where the Priority of the Binary Tree is it's frequency. */
      if (frequencies[i] > 0) {
        HuffmanTree<Byte> node = new HuffmanTree<Byte>((byte) (i - 128));
        q.reverse(node, frequencies[i]);
      }
    }

    /** Merge Binary Trees within the Priority Queue until only one Binary Tree remains within the Queue.*/
    while (q.size() > 1) {
      PriorityNode<HuffmanTree<Byte>> f = q.dequeueNode(), s = q.dequeueNode();
      HuffmanTree<Byte> merged = f.getValue().mergeTree(s.getValue(), (byte) 0);
      q.reverse(merged, f.getPriority() + s.getPriority());
      this.encodingTree = merged;
    }

    /** Assign the remaining Binary Trees within the Priority Queue to the Huffman Binary Tree. */
    if (!q.isEmpty()) {
      this.encodingTree = q.dequeue();
    }

    if (this.encodingTree.getRoot().isLeaf()) {
      this.encodingTree.getRoot().setSequence("0");
    } else {
      /** Update information about the depth and byte sequences of each value within the Huffman Binary Tree. */
      this.encodingTree.setInfo();
    }
  }

  /** A method that initializes the bit sequence for the compressed file, using the Huffman Binary Tree generated previously.  */
  private void bitSequence() {
    this.sequence = "";

    /** If the encoding Binary Tree does not exist, don't proceed with the operations. */
    if (this.encodingTree == null) {
      return;
    }

    /** Generate a Queue of Nodes to iterate through, to find the correct Node and bit sequence at every byte within the File.  */
    PriorityQueue<HuffmanNode<Byte>> q = new PriorityQueue<HuffmanNode<Byte>>(), tmp = new PriorityQueue<HuffmanNode<Byte>>();

    if (this.encodingTree.getRoot() != null) {
      tmp.enqueue(this.encodingTree.getRoot());
    }

    while (!tmp.isEmpty()) {
      HuffmanNode<Byte> node = tmp.dequeue();

      if (node.isLeaf()) {
	      q.enqueue(node, 0);
      }

      if (node.getRight() != null) {
        tmp.enqueue(node.getRight());
      }

      if (node.getLeft() != null) {
        tmp.enqueue(node.getLeft());
      }
    }

    /** Iterates through every byte within the original File to find the corresponding bit sequence of the representative Node within the Huffman Binary Tree, and appends that to the bit sequence String.  */
    for (int i = 0; i < fileBytes.length; i++) {
      for (int k = 0; k < q.size(); k++) {
        if (q.get(k).getValue() == fileBytes[i] && q.get(k).isLeaf()) {
          this.sequence += q.get(k).getSequence();
          break;
        }
      }
    }

    /** Determines the padding required to ensure that a whole number of bits is written to the compressed File. */
    this.padding = (8 - (this.sequence.length()%8)) % 8;

    for (int i = 0; i < this.padding; i++) {
      this.sequence += '0';
    }
  }

  /** Initializes the Bytes to be written within the compressed File.  */
  private void initializeBytes() {
    this.byteSequence = new byte[(this.sequence.length()/8)];

    /** Each Byte corresponds to 8 Bits, which is computed within this For Loop. */
    for (int i = 0; i < this.sequence.length(); i++) {
      byteSequence[i/8] = (byte) ((byteSequence[i/8] << 1) + (this.sequence.charAt(i) == '1' ? 1 : 0));
    }
  }

  /** A getter method which returns the bytes written to the compressed File.
   * @return The bytes written to the compressed File.
   */
  public byte[] getBytes() {
    return this.byteSequence;
  }

  /** A getter method that returns the amount of padding within the compressed File.
   * @return The amount of padding within the compressed File.
   */
  public int getPadding() {
    return this.padding;
  }

  /** A getter method which returns a String representation of the bit sequence written to the compressed File.
   * @return The String representation of the bit sequence written to the compressed File.
   */
  public String getSequence() {
    return this.sequence;
  }

  /** A method which computes the bit sequences for each Node within the Huffman Binary Tree, and returns them as a String. 
   * @return A String which depicts the bit sequences for each Huffman Node within the Huffman Binary Tree.
  */
  public String getMappings() {
    String maps = "";

    /** Once again, this algorithm uses BFS to visit each Node within a Binary Tree. */
    PriorityQueue<HuffmanNode<Byte>> q = new PriorityQueue<HuffmanNode<Byte>>();

    if (this.encodingTree.getRoot() != null) {
      q.enqueue(this.encodingTree.getRoot());
    }

    while (!q.isEmpty()) {
      HuffmanNode<Byte> node = q.dequeue();

      /** If the Node is a leaf, meaning that it contains a value, append its value and bit sequence to the String. */
      if (node.isLeaf()) {
        maps += String.format("%d: %s", (int) node.getValue(), node.getSequence());
      }

      if (node.getLeft() != null) {
        q.enqueue(node.getLeft());
      }

      if (node.getRight() != null) {
        q.enqueue(node.getRight());
      }

      /** Checks whether or not add a new line to the String. */
      maps += (node.isLeaf() ? q.isEmpty() ? "" : "\n" : "");
    }

    return maps;
  }

  /** A method that returns a String bracket representation of the Huffman Binary Tree. The method uses a DFS-like recursive algorithm.
   * @return A String representation of the Huffman Binary Tree.
   */
  public String toString(HuffmanNode<Byte> node) {
    if (node == null) {
      return null;
    }

    /** If the current Node has two children, find the String representation of each child Node. */
    if (node.children() == 2) {
      String R = (!node.getRight().isLeaf() ? this.toString(node.getRight()) : Integer.toString((int) node.getRight().getValue() + (node.getRight().getValue() < 0 ? 2 << 7 : 0)));
      String L = (!node.getLeft().isLeaf() ? this.toString(node.getLeft()) : Integer.toString((int) node.getLeft().getValue() + (node.getLeft().getValue() < 0 ? 2 << 7 : 0)));

      return String.format("(%s %s)", L, R);
    /** If the current Node has no children, find the String representation of the child Node. */
    } else if (node.children() == 1) {
      return String.format("(%s)", (node.getLeft() != null ? this.toString(node.getLeft()) : this.toString(node.getRight())));
    }

    return node.toString();
  }

  /** A method that writes to the compressed File, and returns the file name of the compressed file. 
   * @return The file name of the compressed File.
   */
  public String writeToFile() {
    if (this.sequence == null) {
      return "";
    }

    /** Find the name of the compressed file by stripping the current extension and adding .MZIP as a suffix. */
    String fn = this.filename.substring(0, this.filename.lastIndexOf('.')+1) + "MZIP";

    try {
      /** Writes the required information to the compressed File as bytes. */
      FileOutputStream out = new FileOutputStream(fn, false);

      /** Converts the required information (filename, bracket representation, padding) to bytes. */
      out.write((String.format("%s\r\n%s\r\n%d\r\n", this.filename, this.toString(), (byte) (this.padding)).getBytes()));

      for (int i = 0; i < this.byteSequence.length; i++) {
        out.write(this.byteSequence[i]);
      }

      out.close();

      return fn;
    } catch (Exception e) {
      System.out.println(String.format("Could not write to file: %s", fn));
    }

    return "";
  }

  /** A method that returns a String bracket representation of the Huffman Binary Tree. If the Binary Tree isn't defined, the method will return null;
   * @return A String representation of the Huffman Binary Tree.
  */
  public String toString() {
    return (this.encodingTree.getRoot() != null ? this.toString(this.encodingTree.getRoot()) : null);
  }
}
