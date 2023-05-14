/** A Huffman Node which is used to represent values within a Huffman Binary Tree. Each Huffman Node contains a value, as well as certain references to adjacent Nodes.
 * @author Henry Wang
*/
public class HuffmanNode<T> {
  private T value;
  private HuffmanNode<T> left, right, parent;
  private int depth = 0;
  private String bitSequence = "";

  /** A constructor which initializes an empty Node, and initializes all parent values to be references to NULL objects. */
  public HuffmanNode() {
    this.left = this.right = this.parent = null;
  }

  /** A constructor which initializes a Huffman Node with a value. 
   * @param v The value of the Huffman Node.
  */
  public HuffmanNode(T v) {
    this.value = v;
    this.left = this.right = this.parent = null;
  }

  /** A constructor which initializes a Huffman Node with a value, as well as references to adjacent children Nodes.
   * @param v The value of the Huffman Node
   * @param l The reference to the left child Huffman Node
   * @param r The reference to the right child Huffman Node
   */
  public HuffmanNode(T v, HuffmanNode<T> l, HuffmanNode<T> r) {
    this.value = v;
    this.setLeft(l);
    this.setRight(r);
    this.parent = null;
  }

  /** A getter method which returns the Huffman Node's reference to the left child Huffman Node.
   * @return The Huffman Node's reference to the left child.
   */
  public HuffmanNode<T> getLeft() {
    return this.left;
  }

  /** A getter method which returns the Huffman Node's reference to the right child Huffman Node.
   * @return The Huffman Node's reference to the right child.
   */
  public HuffmanNode<T> getRight() {
    return this.right;
  }

  /** A getter method which returns the Huffman Node's reference to the parent Huffman Node.
   * @return The Huffan Node's reference to the parent node.
   */
  public HuffmanNode<T> getParent() {
    return this.parent;
  }

  /** A setter method which initializes the parent node of a Huffman Node.
   * @param parent The reference to the parent node of the Huffman Binary Node.
   */
  public void setParent(HuffmanNode<T> parent) {
    this.parent = parent;
    /** If the parent node isn't a null reference, initialize the depth of the child node as one more than the parent node's depth. */
    if (this.parent != null) {
      this.setDepth(this.parent.getDepth()+1);
    }
  }

  /** A setter method which initializes the left child node of a Huffman Node.
   * @param left The reference to the new left child node of a Huffman Binary Node.
   */
  public void setLeft(HuffmanNode<T> left) {
    this.left = left;

    /** If the left node isn't a null reference, initialize the parent of the left child node as the current node.*/
    if (this.left != null) {
      this.left.setParent(this);
    }
  }

  /** A setter method which initializes the right child node of a Huffman Node.
   * @param right The reference to the right node of the Huffman Binary Node.
   */
  public void setRight(HuffmanNode<T> right) {
    this.right = right;

    /** If the left node isn't a null reference, initialize the parent of the right child as the current node. */
    if (this.right != null) {
      this.right.setParent(this);
    }
  }

  /** A setter method which initializes the right child node of a Huffman Node using a given value.
   * @param value The new value of the right child node.
   */
  public void setRight(T value) {
    HuffmanNode<T> node = new HuffmanNode<T>(value);
    this.setRight(node);
  }

  /** A setter method which initializes the left child node of a Huffman Node using a given value.
   * @param value The new value of the left child node.
   */
  public void setLeft(T value) {
    HuffmanNode<T> node = new HuffmanNode<T>(value);
    this.setLeft(node);
  }

  /** A getter method which returns the current value of the Huffman Node.
   * @return The current value of the Huffman Node.
   */
  public T getValue() {
    return this.value;
  }

  /** A setter method which redefines the current value of the Huffman Node.
   * @param value The new value of the Huffman Node.
   */
  public void setValue(T value) {
    this.value = value;
  }

  /** A getter method which returns a String representation of the Bit Sequence used in a Huffman Binary Tree to represent the value of the Huffman Node.  
  * @return The String representation of the Bit Sequence used to represent the current value of a Huffman Node.
  */
  public String getSequence() {
    return this.bitSequence;
  }

  /** A getter method which redefines the String representation of the Bit Sequence used to represent the value of the Huffman Node.
   * @param sequence The new representation of the current Nodes bit sequence.
   */
  public void setSequence(String sequence) {
    this.bitSequence = sequence;
  }

  /** A setter method which redefines the depth of the current Huffman Node.
   * @param depth The depth of the current Huffman Node. 
   */
  public void setDepth(int depth) {
    this.depth = depth;
  }

  /** A getter method which returns the depth of the current Huffman Node.
   * @return The depth of the current Huffman Node.
   */
  public int getDepth() {
    return this.depth;
  }

  /** A setter method which returns the amount of children of the current Huffman Node.
   * @return The amount of children of the current Huffman Node.
   */
  public int children() {
    return (this.right != null ? 1 : 0) + (this.left != null ? 1 : 0);
  }

  /** Returns a boolean value denoting whether or not the Huffman Node is a leaf node or not. 
   * @return A boolean value denoting whether or not the Huffan Node is a leaf node.
  */
  public boolean isLeaf() {
    return (this.children() == 0);
  }

  /** Overrides the built-in toString() method to return a String representation of the current Huffman Node. 
   * @return The String representation of the current Huffman Node.
  */
  @Override
  public String toString() {
    return String.format("{%s}", this.value.toString());
  }
}
