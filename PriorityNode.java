/**
 * A Node class which holds a singular value, a priority value, as well as two
 * references to neighbouring Nodes (previous and next Nodes). These references
 * to neighbouring Nodes are especially useful in the creation of Doubly Linked
 * Lists, as well as other Data Structures which inherit the traits of Double
 * Linked Lists such as Queues and Stacks.
 *
 * @author Henry Wang
 */

/** The Priority Node uses generics to store values of any type. */
public class PriorityNode<T> {
  private T value;
  private int priority = 0;
  private PriorityNode<T> prev, next;

  /**
   * Constructor which initializes an empty Node object. Within the constructor,
   * references to both previous and next Nodes are initialized as references to
   * null objects. The priority of the Node is, by default, initialized as 0.
   */
  public PriorityNode() {
    this.prev = this.next = null;
  }

  /**
   * A simple constructor which initializes the Priority Node object with a value.
   * Within the constructor, references to both previous and next Nodes are
   * initialized as references to null objects. The priority of the Node is, by
   * default, initialized as 0.
   *
   * @param v The value of the Priority Node.
   */
  public PriorityNode(T v) {
    this.value = v;
    this.prev = this.next = null;
  }

  /**
   * A simple constructor which initializes the Priority Node object with its
   * value and priority. Within the constructor, references to both previous and
   * next Nodes are initialized as references to null objects.
   *
   * @param v The value of the Priority Node.
   * @param p The priority of the Priority Node.
   */
  public PriorityNode(T v, int p) {
    this.value = v;
    this.priority = p;
    this.prev = this.next = null;
  }

  /**
   * A simple constructor which initializes the Priority Node object along with
   * its value, and references to both previous and next Nodes.
   *
   * @param v The value of the Priority Node.
   * @param p The reference to the previous Priority Node object.
   * @param n The reference to the next Priority Node object.
   */
  public PriorityNode(T v, PriorityNode<T> p, PriorityNode<T> n) {
    this.value = v;
    this.setPrev(p);
    this.setNext(n);
  }

  /**
   * A getter method which returns the Node reference to its previous Node. If the
   * previous Node is not defined, the method will return a null object.
   *
   * @return The reference to the previous Priority Node object.
   */
  public PriorityNode<T> getPrev() {
    return this.prev;
  }

  /**
   * A getter method which returns the Node reference to its successive Node. If
   * the successive Node is not defined, the method will return a null object.
   *
   * @return The reference to the successive Priority Node object.
   */
  public PriorityNode<T> getNext() {
    return this.next;
  }

  /**
   * A setter method that either initializes, or redefines, the reference to the
   * previous Priority Node.
   *
   * @param p The reference to the new previous Priority Node.
   */
  public void setPrev(PriorityNode<T> p) {
    this.prev = p;
    /**
     * If the successive reference of the previous Node is not pointed towards the
     * current Object, change the reference.
     */
    if (this.prev != null && this.prev.getNext() != this) {
      this.prev.setNext(this);
    }
  }

  /**
   * A setter method that either initializes, or redefines, the reference to the
   * successive Priority Node.
   *
   * @param n The reference to the new successive Priority Node.
   */
  public void setNext(PriorityNode<T> n) {
    this.next = n;
    /**
     * If the previous reference of the successive Node is not pointed towards the
     * current Object, change the reference.
     */
    if (this.next != null && this.next.getPrev() != this) {
      this.next.setPrev(this);
    }
  }

  /**
   * A method that overloads the setPrev() method, which redefines the reference
   * to the previous Priority Node.
   *
   * @param v The value of the new previous Priority Node.
   */
  public void setPrev(T v) {
    PriorityNode<T> node = new PriorityNode<T>(v);
    this.setPrev(node);
  }

  /**
   * A method that overloads the setNext() method, which redefines the reference
   * to the successive Priority Node.
   *
   * @param v The value of the new next Priority Node.
   */
  public void setNext(T v) {
    PriorityNode<T> node = new PriorityNode<T>(v);
    this.setNext(node);
  }

  /**
   * A getter method which returns the priority of the Priority Node. If the
   * priority of the Node was not explicitly declared, the priority of the Node
   * has a default value of 0. The priority of a Node is used to decide the
   * location of insertion within a Priority Queue.
   *
   * @return The priority of the Priority Node.
   */
  public int getPriority() {
    return this.priority;
  }

  /**
   * A setter method which redefines the priority of a Prioirity Node.
   *
   * @param p The new priority of the Priority Node.
   */
  public void setPriority(int p) {
    this.priority = p;
  }

  /**
   * A getter method which returns the Nodes current value.
   *
   * @return The Nodes current value.
   */
  public T getValue() {
    return this.value;
  }

  /**
   * A setter method which redefines the Nodes current value
   *
   * @param v The new value for the Node.
   *            .
   */
  public void setValue(T v) {
    this.value = v;
  }

  /**
   * Overrides the toString() method, which is called on default by output methods
   * such as System.out.println(). The method returns a String representation of
   * the current Node, depicting it's value and priority.
   *
   * @return A String representation of the current Node.
   */
  @Override
  public String toString() {
    return String.format("{%s, %d}", this.value.toString(), this.priority);
  }
}
