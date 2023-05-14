public class PriorityQueue<T> {
  private int size = 0;
  private PriorityNode<T> head, tail;

  public PriorityQueue() {
    this.head = this.tail = null;
  }

  public PriorityQueue(T value) {
    this.head = this.tail = new PriorityNode<T>(value);
    if (this.head != null) {
      this.size++;
    }
  }


  public void enqueue(PriorityNode<T> node, boolean reverse) {
    if (this.head == null) {
      this.head = this.tail = node;
    } else if ((reverse ? 
                this.head.getPriority() > node.getPriority() : 
                this.head.getPriority() < node.getPriority())) {
      this.head.setPrev(node);
      this.head = node;
    } else if ((reverse 
                ? this.tail.getPriority() <= node.getPriority() 
                : this.tail.getPriority() >= node.getPriority())) {
      this.tail.setNext(node);
      this.tail = node;
    } else {
      PriorityNode<T> current = this.head;
      while (current != null && (reverse 
                                  ? current.getPriority() <= node.getPriority() 
                                  : current.getPriority() >= node.getPriority())) {
        current = current.getNext();
      }
      node.setPrev(current.getPrev());
      node.setNext(current);
    }
    this.size++;
  }  

  public void enqueue(T value, int priority) {
    PriorityNode<T> node = new PriorityNode<T>(value, priority);
    this.enqueue(node, false);
  }

  public void enqueue(T value) {
    PriorityNode<T> node = new PriorityNode<T>(value, 0);
    this.enqueue(node, false);
  }

  public void reverse(T value, int priority) {
    PriorityNode<T> node = new PriorityNode<T>(value, priority);
    this.enqueue(node, true);
  }

  public void reverse(T value) {
    PriorityNode<T> node = new PriorityNode<T>(value, 0);
    this.enqueue(node, true);
  }

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

  public T dequeue() {
    if (this.head == null) {
      return null;
    }

    T value = this.head.getValue();
    this.head = this.head.getNext();

    if (--this.size == 0) {
      this.tail = this.head;
    }

    return value;
  }

  public PriorityNode<T> dequeueNode() {
    if (this.head == null) {
      return null;
    }

    PriorityNode<T> node = this.head;
    this.head = this.head.getNext();

    if (--this.size == 0) {
      this.tail = this.head;
    }

    return node;
  }

  public T front() {
    return (this.head != null ? this.head.getValue() : null);
  }

  public T back() {
    return (this.tail != null ? this.tail.getValue() : null);
  }

  public PriorityNode<T> getHead() {
    return this.head;
  }

  public PriorityNode<T> getTail() {
    return this.tail;
  }

  public boolean isEmpty() {
    return (this.head == null);
  }

  public int size() {
    return (this.size);
  }

  @Override
  public String toString() {
    String rep = "";
    PriorityNode<T> current = this.head;

    for (int i = 0; i < this.size; i++) {
      rep += String.format("{%s, %d}", current.toString(), current.getPriority())  + (i != this.size-1 ? "<-" : "");
      current = current.getNext();
    }

    return rep;
  }
}