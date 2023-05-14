/** A modified Binary Tree used to represent bits within a File, as a method of Huffman Coding.
 * @author Henry Wang
 */

/** The Huffman Tree uses generics to be able to represent a Binary Tree of any type. */
public class HuffmanTree<T extends Comparable<T>> {
  private HuffmanNode<T> root;

  /** Constructor which initializes an empty Binary Tree. */
  public HuffmanTree() {
    this.root = null;
  }

  /** Constructor which initializes the Binary Tree according to an initial value.
   * @param value The initial value of the Binary Tree.
   */
  public HuffmanTree(T value) {
    this.root = new HuffmanNode<T>(value);
  }

  /** Inserts a new Node into the Binary Tree. 
   * @param node The node to be inserted within the Binary Tree.
   */
  public void add(HuffmanNode<T> node) {
    HuffmanNode<T> current = this.root;
    if (current == null) {
      current = node;
    } else {
      /** Loops until the Node reaches the correct parent Node. At any instance, if there is no branch to traverse, insert the Node as a child of the current Node.*/
      while (true) {
        int comparison = node.getValue().compareTo(current.getValue());
        /** If the current Node's value is larger than or equal to the Nodes value, traverse the left branch. */
        if (comparison <= 0) {
          if (current.getLeft() == null) {
            current.setLeft(node);
            break;
          }
          current = current.getLeft();
          /** If the current Node's value is smaller than the Nodes value, traverse the right branch.*/
        } else {
          if (current.getRight() == null) {
            current.setRight(node);
            break;
          }
          current = current.getRight();
        }
      }
    }
  }

  /** A setter method which redefines the value of the root node within the Binary Tree. 
   * @param value The value of the root node. 
  */
  public void setRoot(T value) {
    this.root = new HuffmanNode<T>(value);
  }

  /** A setter method which redefines the root node within the Binary Tree. 
   * @param node The new root node.
  */
  public void setRoot(HuffmanNode<T> node) {
    this.root = node;
  }

  /** A getter method which returns the root of a Binary Tree.
   * @return The root node of the Binary Tree.
   */
  public HuffmanNode<T> getRoot() {
    return this.root;
  }

  /** A method which initializes depth and bit sequence information for each of the Huffman Nodes within the Huffman Tree. This is done using the BFS algorithm, to efficiently connect each Node with the value of its parent Node. */
  public void setInfo() {
    PriorityQueue<HuffmanNode<T>> queue = new PriorityQueue<HuffmanNode<T>>();
    /** If the tree exists, initialize the root node of the tree with an empty sequence and depth of 0. */
    if (this.root != null) {
      this.root.setDepth(0);
      this.root.setSequence("");
      queue.enqueue(this.root);
    }

    while (!queue.isEmpty()) {
      HuffmanNode<T> node = queue.dequeue();

      /** If the right child of the current node exists, the bit sequence of the right child is the sequence of the parent + '1'.*/
      if (node.getRight() != null) {
        node.getRight().setSequence(node.getSequence() + "1");
        node.getRight().setDepth(node.getDepth()+1);
        queue.enqueue(node.getRight());
      }

      /** If the left child of the current node exists, the bit sequence of the left child is the sequence of the parent + '0'. */
      if (node.getLeft() != null) {
        node.getLeft().setSequence(node.getSequence() + "0");
        node.getLeft().setDepth(node.getDepth()+1);
        queue.enqueue(node.getLeft());
      }
    }
  }

  /** A method which merges two Huffman Binary Trees into a singular, larger Huffman Binary Tree. 
   * @param tree The tree to be merged with the current tree.
   * @param initial The initial value of the root node of the merged tree.
   * @return The merged tree between the current and given tree.
   * */ 
  public HuffmanTree<T> mergeTree(HuffmanTree<T> tree, T initial) {
    if (tree == null) {
      return this;
    }

    /** Initialize a new Huffman Binary Tree with an initial root value. */
    HuffmanTree<T> merged = new HuffmanTree<T>(initial);
    merged.getRoot().setRight(this.getRoot());
    merged.getRoot().setLeft(tree.getRoot());
    return merged;
  }


  /** A method which overrides the built-in toString() method which is used by built-in output methods such as System.out.print(). The method returns a String representation of the Binary Tree, using the BFS algorithm.
   * @return A String representation of the Binary Tree.
   */
  @Override
  public String toString() {
    String rep = "";
    int currentDepth = 0;
    PriorityQueue<HuffmanNode<T>> queue = new PriorityQueue<HuffmanNode<T>>();

    if (this.root != null) {
      queue.enqueue(this.root);
    }

    while (!queue.isEmpty()) {
      HuffmanNode<T> node = queue.dequeue();

      while (currentDepth != node.getDepth()) {
        rep += "\n";
        currentDepth++;
      }

      /** Adds the current Nodes value to the String repersentation. */
      rep += node.toString();

      if (node.getLeft() != null) {
        queue.enqueue(node.getLeft());
      }

      if (node.getRight() != null) {
        queue.enqueue(node.getRight());
      }
    }

    return rep;
  }
}
