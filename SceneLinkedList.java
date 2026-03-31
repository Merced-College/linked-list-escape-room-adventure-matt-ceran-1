public class SceneLinkedList {
    private Node head;
    private int size;

    public SceneLinkedList() {
        head = null;
        size = 0;
    }

    public void add(Scene scene) {
        Node newNode = new Node(scene);

        if (head == null) {
            head = newNode;
        } else {
            Node current = head;
            while (current.getNext() != null) {
                current = current.getNext();
            }
            current.setNext(newNode);
        }

        size++;
    }

    public Scene findSceneById(int sceneId) {
        Node current = head;

        while (current != null) {
            if (current.getData().getSceneId() == sceneId) {
                return current.getData();
            }
            current = current.getNext();
        }

        return null;
    }

    public boolean isEmpty() {
        return head == null;
    }

    public int size() {
        return size;
    }

    public void displayScenes() {
        Node current = head;
        while (current != null) {
            System.out.println(current.getData());
            current = current.getNext();
        }
    }
}