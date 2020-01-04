package finalproject;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import javax.swing.JOptionPane;

public class EmployeeManager {

    Node root;

    public EmployeeManager() {
        root = null;
    }

    public Node getRoot() {
        return root;
    }

    public Node insertNode(Node node, Employee employeeName) {

        if (node == null) {
            node = new Node(employeeName);
            return node;
        }
        if (employeeName.getSalary() < node.data.getSalary() && (employeeName.getSalary() - node.data.getSalary()) < 3000) {
            node.left = insertNode(node.left, employeeName);
        } else if (employeeName.getSalary() > node.data.getSalary()) {
            node.right = insertNode(node.right, employeeName);
        }
        return node;
    }

    public void insertEmployee(Employee employee) {
        root = insertNode(root, employee);
    }

    public Node search(Node node, Employee employee) {
        if (node == null || node.data.getSalary() == employee.getSalary()) {
            return node;
        }
        if (node.data.getSalary() > employee.getSalary()) {
            return search(node.left, employee);
        }
        return search(node.right, employee);
    }

    public Node remove(Node node, Employee employee) {
        if (node == null) {
            return node;
        }
        if (employee.getSalary() < node.data.getSalary()) {
            node.left = remove(node.left, employee);
        } else if (employee.getSalary() > node.data.getSalary()) {
            node.right = remove(node.right, employee);
        } else {
            if (node.left == null) {
                return node.right;
            } else if (node.right == null) {
                return node.left;
            }
            node.data = getMin(node.right);
            node.right = remove(node.right, node.data);
        }
        return node;
    }

    public Employee getMin(Node node) {
        Employee minEmployee = node.data;
        while (node.left != null) {
            minEmployee = node.left.data;
            node = node.left;
        }
        return minEmployee;
    }

    public void printResult(Node node) {
        if (node != null) {
            printResult(node.left);
            System.out.println(node.data.getName() + " " + node.data.getSalary());
            printResult(node.right);
        }
    }

    public void printResult() {
        printResult(root);
    }

    public static void main(String[] args) throws FileNotFoundException, IOException {
        EmployeeManager EM = new EmployeeManager();

        String income = "";
        String input1 = "";
        String input2 = "";
        String line;
        BufferedReader br ;
        FileReader fr;
        try {
            fr = new FileReader("C:\\Users\\teome\\OneDrive\\Documents\\NetBeansProjects\\finalProject\\src\\finalproject\\EmployeeData");
            br = new BufferedReader(fr);
            //line = br.readLine();
            while ((line = br.readLine()) != null) {
                String[] lines2 = line.split("\\s+");
                if (!line.contains("Salary") && !line.contains("Name")) {
                    String name = lines2[0].trim();
                    String salary = lines2[1].trim();
                    Employee employee = new Employee(name, Integer.parseInt(salary));
                    EM.insertEmployee(employee);
                }
            }

            System.out.println("*** SORTED EMPLOYEES LIST BASED ON THEIR SALARY***");
            EM.printResult();

            do {
                input1 = JOptionPane.showInputDialog("Would You Like To Search Any Employee? \nplease type Yes or No!");

            } while (!(input1.equalsIgnoreCase("yes") || input1.equalsIgnoreCase("No")));
            if (input1.equalsIgnoreCase("yes")) {
                input1 = JOptionPane.showInputDialog("Please type the name of the Employee you would like to search! "
                        + "\nNames available to search are: \nSusan, Joseph, Betty, Bob, Sam, Nathan, Sally, Dilbert and Veronica");
                income = JOptionPane.showInputDialog("Please input " + input1 + "'s Salary!");

            } else if (input1.equalsIgnoreCase("No")) {

                JOptionPane.showMessageDialog(null, "Thank you and GoodBye!");
                System.exit(0);
            }

            Employee employee = new Employee(input1, Double.parseDouble(income));
            Node node = EM.search(EM.getRoot(), employee);

            JOptionPane.showMessageDialog(null, String.format("Employee Found....\nName: " + node.data.getName() + "\nSalary: $ %.2f", node.data.getSalary()));

            do {
                input1 = JOptionPane.showInputDialog("Would You Like To Remove Any Employee? \nplease type Yes or No!");

            } while (!(input1.equalsIgnoreCase("yes") || input1.equalsIgnoreCase("No")));

            if (input1.equalsIgnoreCase("yes")) {

                input1 = JOptionPane.showInputDialog("Please type name of Employee to remove from the list");
                input2 = JOptionPane.showInputDialog("Please type " + input1 + "'s salary");

            } else if (input1.equalsIgnoreCase("No")) {
                System.exit(0);
            }

            employee = new Employee(input1, Double.parseDouble(input2));
            EM.remove(EM.getRoot(), employee);
            System.out.println("\n*** LIST AFTER REMOVING EMPLOYEE... NAME:" + input1 + "\tSALARY:$" + input2 + " ***");
            EM.printResult();

        } catch (FileNotFoundException ex) {

        }

    }
}
