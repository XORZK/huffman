public class PriorityNode<T> {
  private T value;
  private int priority = 0;
  private PriorityNode<T> prev, next;

  public PriorityNode() {
    this.prev = this.next = null;
  }

  public PriorityNode(T value) {
    this.value = value;
    this.prev = this.next = null;
  }

  public PriorityNode(T value, int priority) { 
    this.value = value;
    this.priority = priority;
    this.prev = this.next = null;
  }

  public PriorityNode(T value, PriorityNode<T> prev, PriorityNode<T> next) {
    this.value = value;
    this.setPrev(prev);
    this.setNext(next);
  }

  public PriorityNode(T value, int priority, PriorityNode<T> prev, PriorityNode<T> next) {
    this.value = value;
    this.priority = priority;
    this.setPrev(prev);
    this.setNext(next);
  }

  public PriorityNode<T> getPrev() {
    return this.prev;
  }

  public PriorityNode<T> getNext() {
    return this.next;
  }

  public void setPrev(PriorityNode<T> prev) {
    this.prev = prev;
    if (this.prev != null && this.prev.getNext() != this) {
      this.prev.setNext(this);
    }
  }

  public void setNext(PriorityNode<T> next) {
    this.next = next;
    if (this.next != null && this.next.getPrev() != this) {
      this.next.setPrev(this);
    }
  }

  public void setPrev(T value) {
    this.setPrev(new PriorityNode<T>(value));
  }

  public void setNext(T value) {
    this.setNext(new PriorityNode<T>(value));
  }

  public int getPriority() {
    return this.priority;
  }


  public void setPriority(int priority) {
    this.priority = priority;
  }

  public T getValue() {
    return this.value;
  }

  public void setValue(T value) {
    this.value = value;
  }

  @Override
  public String toString() {
    return String.format("{%s}", this.value.toString());
  }
}