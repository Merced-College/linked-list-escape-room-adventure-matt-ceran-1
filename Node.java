public class Node {
    private Scene data;
    private Node next;

    public Node(Scene data) {
        this.data = data;
        this.next = null;
    }

    public Scene getData() {
        return data;
    }

    public Node getNext() {
        return next;
    }

    public void setNext(Node next) {
        this.next = next;
    }
}
