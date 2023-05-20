/**
 * A Priority Queue Data Structure which is implemented by extending a Double
 * Linked List. Within a Priority Queue, every Node has a value as well as a
 * priority, which denotes it's position within the Queue. A Node with a higher
 * priority will be enqueued closer to the front than Nodes with a lower
 * priority.
 * 
 * @author Henry Wang
 */

/**
 * The Priority Queue Data Structure uses Generics to able to store Nodes of any
 * type.
 */
public class PriorityQueue<T> {
  private int size = 0;
  private PriorityNode<T> head, tail;

  /**
   * A simple Priority Queue constructor which initializes the head and tail of
   * the Queue to be NULL objects. This essentially initializes an empty Priority
   * Queue.
   */
  public PriorityQueue() {
    this.head = this.tail = null;
  }

  /**
   * A simple Priority Queue constructor which initializes the head and tail of
   * the Queue with a singular Node value. By default, the priority of the Node is
   * initialized to 0.
   * 
   * @param value The value of the initial Node within the Queue.
   */
  public PriorityQueue(T value) {
    this.head = this.tail = new PriorityNode<T>(value);

    /**
     * If the value is not a reference to a NULL object, initialize the size of the
     * Queue to be 1.
     */
    if (this.head != null) {
      this.size++;
    }
  }

  /**
   * A simple Priority Queue constructor which initializes the head and tail of
   * the Queue with a singular Node value, along with a priority value
   * 
   * @param value    The value of the initial Node within the Queue.
   * @param priority The priority of the initial Node within the Queue.
   */
  public PriorityQueue(T value, int priority) {
    this.head = this.tail = new PriorityNode<T>(value, priority);

    /**
     * If the value is not a reference to a NULL object, initialize the size of the
     * Queue to be 1.
     */
    if (this.head != null) {
      this.size++;
    }
  }

  /**
   * A method that enqueues a Node within the Priority Queue. The enqueue
   * algorithm works by iterating through the Prioriy Queue until it reaches a
   * Node in which the new Node can be the successor towards. The order of
   * priority of Nodes depends on whether or not the Node was enqueued in reverse
   * order, which means that lower priority Nodes are enqueued first. There exists
   * some special cases that may help improve the efficiency of the algorithm:
   * 1. When the Queue is empty, the Node is automatically assigned to the head of
   * the Queue.
   * 2. If the Node can be enqueued as the head of the Queue, meaning that the
   * priority of the new Node is either less than or more than the head node, once
   * again, depending on whether or not the Node was enqueued in reverse order.
   * 3. If the Node can be enqueued as the tail of the Queue, meaning that the
   * priority of the new Node is either less than or more than the head node,
   * depending on whether or not the Node was enqueued in the reverse order.
   * The algorithm runs in O(n) time complexity, where n is the size of the Queue.
   * 
   * @param node     The node to be enqueued within the Priority Queue.
   * @param reversed A boolean value denoting whether or not the Node is enqueued
   *                 in reverse order.
   */
  public void enqueue(PriorityNode<T> node, boolean reversed) {
    if (node == null) {
      return;
    }

    /**
     * Checks if the Queue is empty, if so, the Node is assigned to the head of the
     * Queue.
     */
    if (this.head == null) {
      this.head = this.tail = node;
      /**
       * If the enqueue is not reversed and the Node has a higher priority than the
       * current head of the Queue or if the enqueue is reversed and the Node has a
       * lower priority than the current head of the Queue, the new Node is assigned
       * as the head of the Queue.
       */
    } else if ((reversed ? this.head.getPriority() > node.getPriority()
        : this.head.getPriority() < node.getPriority())) {
      this.head.setPrev(node);
      this.head = node;
      /**
       * If the enqueue is not reversed and the Node has a lower priority than the
       * tail of the Queue, or if the enqueue is reversed and the Node has a higher
       * priority than the tail of the Queue, the new Node is assigned as the tail of
       * the Queue.
       */
    } else if ((reversed ? this.tail.getPriority() <= node.getPriority()
        : this.tail.getPriority() >= node.getPriority())) {
      this.tail.setNext(node);
      this.tail = node;
      /**
       * If no special cases occur, iterate through the Nodes until the correct index
       * for the new Node is found, and insert the new Node at the index.
       */
    } else {
      PriorityNode<T> current = this.head;
      while (current != null
          && (reversed ? current.getPriority() <= node.getPriority()
              : current.getPriority() >= node.getPriority())) {
        current = current.getNext();
      }
      /**
       * Enqueue Node Example (Proper Enqueue):
       * Inserting {e, 1} to Queue: {d,3} <-> {c,2} <-> {b,1} <-> {a,1}
       * {d,3} <-> {c,2} <-> {n,1} <-> {b,1} <-> {a,1}
       */
      node.setPrev(current.getPrev());
      node.setNext(current);
    }
    this.size++;
  }

  /**
   * A method that overloads the enqueue method which constructs a Node object
   * from
   * given value and priority paramater values. Using these values, a new Node is
   * enqueued properly within the Queue.
   * 
   * @param v The value of the new Node to be enqueued.
   * @param p The priority of the new Node to be enqueued.
   */
  public void enqueue(T v, int p) {
    PriorityNode<T> node = new PriorityNode<T>(v, p);
    this.enqueue(node, false);
  }

  /**
   * A method that overloads the enqueue method which constructs a Node object
   * from a
   * given value parameter value. Using the value parameter, a new Node with
   * priority of 0 is enqueued properly within the Queue. If all Nodes within the
   * Queue have a priority of 0, the Priority Queue is congruent to a regular
   * Queue.
   * 
   * @param v The value of the new Node to be enqueued.
   */
  public void enqueue(T v) {
    this.enqueue(v, 0);
  }

  /**
   * Enqueues a new Node, with value v and priority p, in reverse order. Reverse
   * order implies that Nodes with priorities of lower magnitude are enqueued
   * first within the Priority Queue.
   * 
   * @param v The value of the new Node to be enqueued in reverse order.
   * @parma p The priority of the new Node to be enqueued in the reverse order.
   */
  public void reverse(T v, int p) {
    PriorityNode<T> node = new PriorityNode<T>(v, p);
    this.enqueue(node, true);
  }

  /**
   * A method that enqueues a new Node, with value v, in reverse order. The
   * priority of the Node is, by default, 0.
   * 
   * @param v The value of the new Node to be enqueued in reverse order.
   */
  public void reverse(T v) {
    this.reverse(v, 0);
  }

  /**
   * A method that returns the value of a Node at a specific index within the
   * Queue. This is done by iterating through each element within the Queue until
   * the correct index is found, and then returns the value of the Node at that
   * index. The method will return a NULL object if the index is out of bounds.
   * The indexing algorithm runs in O(n) time complexity, where n is the size of
   * the Queue.
   * 
   * @param index The index of the Node in which the value is required.
   * @return The value of the Node at the specified index.
   */
  public T get(int index) {
    if (index < 0 || index >= this.size) {
      return null;
    }

    PriorityNode<T> current = this.head;

    for (int i = 0; i < index; i++) {
      current = current.getNext();
    }

    return (current != null ? current.getValue() : null);
  }

  /**
   * A dequeue method that removes the first Node within the Queue, and returns
   * it's value. Dequeuing from a Queue involves redefining the head Node as the
   * next Node within the Queue. If the Queue is empty, the method will return a
   * null object.
   * 
   * @return The value of the dequeue'd Node.
   */
  public T dequeue() {
    if (this.head == null) {
      return null;
    }

    T value = this.head.getValue();
    this.head = this.head.getNext();

    /**
     * If the Queue is now empty, create references from both the tail and head
     * Nodes to NULL objects.
     */
    if (--this.size == 0) {
      this.tail = this.head;
    }

    return value;
  }

  /**
   * A dequeue method that removes the first Node within the Queue, and returns
   * the Node. Dequeuing from a Queue involves redefining the head Node as the
   * next Node within the Queue. If the Queue is empty, the method will return a
   * null object.
   * 
   * @return The past head node of the Queue.
   */
  public PriorityNode<T> dequeueNode() {
    if (this.head == null) {
      return null;
    }

    PriorityNode<T> node = this.head;

    /** Sets the head of the Queue to be the second element within the Queue. */
    this.head = this.head.getNext();

    if (--this.size == 0) {
      this.tail = this.head;
    }

    return node;
  }

  /**
   * A method that returns the value of the head of the Queue. If the Queue is
   * empty, the method will return a NULL object.
   * 
   * @return The value of the head of the Queue.
   */
  public T front() {
    return (this.head != null ? this.head.getValue() : null);
  }

  /**
   * A method that returns the value of the tail of the Queue. If the Queue is
   * empty, the method will return a NULL object.
   * 
   * @return The value of the tail of the Queue.
   */
  public T back() {
    return (this.tail != null ? this.tail.getValue() : null);
  }

  /**
   * A method that returns the head of the Queue. If the Queue is empty, the
   * method will return a NULL object.
   * 
   * @return The head node of the Queue.
   */
  public PriorityNode<T> getHead() {
    return this.head;
  }

  /**
   * A method that returns the tail of the Queue. If the Queue is empty, the
   * method will return a NULL object.
   * 
   * @return The tail of the Queue.
   */
  public PriorityNode<T> getTail() {
    return this.tail;
  }

  /**
   * A method which returns a boolean value denoting whether or not the Queue is
   * empty. This is done by checking whether or not the head of the Queue is a
   * reference to a non-null object.
   * 
   * @return A boolean value denoting whether or not the Queue is empty.
   */
  public boolean isEmpty() {
    return (this.head == null);
  }

  /**
   * A getter method which returns the size of the Queue. Within the
   * implementation of the Priority Queue Data Structure, the size of the Queue is
   * maintained as a property of the Queue, as is updated with every enqueue and
   * dequeue operation. This reduces the need for the time complexity of the sie
   * method to be O(n), and decreases the time complexity to be O(1).
   * 
   * @return The size of the Priority Queue.
   */
  public int size() {
    return this.size;
  }

  /**
   * Overrides the built-in toString() method, which is called on default by
   * output methods such as System.out.print(). The method returns a String
   * representation of the Nodes that are found within the Queue. Each Node is
   * represented using a value and priority tuple: {value, priority}. The
   * algorithm runs in O(n) time complexity, where n is the size of the Queue.
   * 
   * @return A String representation of the Priority Queue.
   */
  @Override
  public String toString() {
    String representation = "";
    PriorityNode<T> current = this.head;

    /** Iterates through every element within the Priority Queue. */
    for (int i = 0; i < this.size; i++) {
      representation += String.format("{%s, %d}", current.toString(), current.getPriority())
          + (i != this.size - 1 ? "<-" : "");
      current = current.getNext();
    }

    return representation;
  }
}