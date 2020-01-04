package finalproject;

public class Node {

    Employee data;
    Node left;
    Node right;

    public Node(Employee data) {
        this.data = data;
        left = right = null;

    }
}
