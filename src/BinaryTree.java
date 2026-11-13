
import java.util.*;

public class BinaryTree {

    TreeNode root;

    public BinaryTree() {
        this.root = null;
    }

    public BinaryTree(TreeNode root) {
        this.root = root;
    }

    // ==========================================
    // 1. CONSTRUCCION DEL ARBOL
    // ==========================================

    public void insertRoot(Object data) {
        if (root == null) {
            root = new TreeNode(data);
        }
    }

    public TreeNode search(Object data) {
        return searchRecursive(root, data);
    }

    private TreeNode searchRecursive(TreeNode node, Object data) {

        if (node == null) {
            return null;
        }

        if (Objects.equals(node.data, data)) {
            return node;
        }

        TreeNode found = searchRecursive(node.left, data);

        if (found != null) {
            return found;
        }

        return searchRecursive(node.right, data);
    }

    public boolean insertLeft(Object parent, Object data) {

        TreeNode node = search(parent);

        if (node == null || node.left != null) {
            return false;
        }

        node.left = new TreeNode(data);
        return true;
    }

    public boolean insertRight(Object parent, Object data) {

        TreeNode node = search(parent);

        if (node == null || node.right != null) {
            return false;
        }

        node.right = new TreeNode(data);
        return true;
    }

    public void buildTree(Scanner scanner) {

        if (root != null) {
            System.out.println("El arbol ya tiene una raiz.");
            return;
        }

        System.out.print("Raiz: ");
        String value = scanner.nextLine().trim();

        if (value.isEmpty() || value.equals("-")) {
            System.out.println("No se ingreso una raiz.");
            return;
        }

        insertRoot(value);

        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root);

        while (!queue.isEmpty()) {

            TreeNode current = queue.poll();

            System.out.print("Hijo izquierdo de "
                    + current.data + ": ");

            String left = scanner.nextLine().trim();

            if (!left.isEmpty() && !left.equals("-")) {
                insertLeft(current.data, left);
                queue.add(current.left);
            }

            System.out.print("Hijo derecho de "
                    + current.data + ": ");

            String right = scanner.nextLine().trim();

            if (!right.isEmpty() && !right.equals("-")) {
                insertRight(current.data, right);
                queue.add(current.right);
            }
        }

        System.out.println("Arbol construido correctamente.");
    }

    // ==========================================
    // 2. VISUALIZACION
    // ==========================================

    public void printTree() {

        if (root == null) {
            System.out.println("El arbol esta vacio.");
            return;
        }

        System.out.println("\nARbol binario:");
        printTreeRecursive(root, "", true);
    }

    private void printTreeRecursive(
            TreeNode node, String prefix, boolean isTail) {

        if (node == null) {
            return;
        }

        System.out.println(prefix
                + (isTail ? "└── " : "├── ")
                + node.data);

        String newPrefix = prefix
                + (isTail ? "    " : "│   ");

        if (node.left != null && node.right != null) {

            printTreeRecursive(node.left, newPrefix, false);
            printTreeRecursive(node.right, newPrefix, true);

        } else if (node.left != null) {

            printTreeRecursive(node.left, newPrefix, true);

        } else if (node.right != null) {

            printTreeRecursive(node.right, newPrefix, true);
        }
    }

    // ==========================================
    // 3. RECORRIDOS RECURSIVOS
    // ==========================================

    public String preorder() {

        StringBuilder result = new StringBuilder();

        preorderRecursive(root, result);

        return result.toString().trim();
    }

    private void preorderRecursive(
            TreeNode node, StringBuilder result) {

        if (node == null) {
            return;
        }

        result.append(node.data).append(" ");

        preorderRecursive(node.left, result);
        preorderRecursive(node.right, result);
    }

    public String inorder() {

        StringBuilder result = new StringBuilder();

        inorderRecursive(root, result);

        return result.toString().trim();
    }

    private void inorderRecursive(
            TreeNode node, StringBuilder result) {

        if (node == null) {
            return;
        }

        inorderRecursive(node.left, result);

        result.append(node.data).append(" ");

        inorderRecursive(node.right, result);
    }

    public String postorder() {

        StringBuilder result = new StringBuilder();

        postorderRecursive(root, result);

        return result.toString().trim();
    }

    private void postorderRecursive(
            TreeNode node, StringBuilder result) {

        if (node == null) {
            return;
        }

        postorderRecursive(node.left, result);
        postorderRecursive(node.right, result);

        result.append(node.data).append(" ");
    }

    // ==========================================
    // 4. FACTORES DE EQUILIBRIO
    // ==========================================

    public int height(TreeNode node) {

        if (node == null) {
            return 0;
        }

        int leftHeight = height(node.left);
        int rightHeight = height(node.right);

        return 1 + Math.max(leftHeight, rightHeight);
    }

    public int balanceFactor(TreeNode node) {

        if (node == null) {
            return 0;
        }

        return height(node.right) - height(node.left);
    }

    public boolean isBalanced() {

        return isBalancedRecursive(root);
    }

    private boolean isBalancedRecursive(TreeNode node) {

        if (node == null) {
            return true;
        }

        int factor = balanceFactor(node);

        if (factor < -1 || factor > 1) {
            return false;
        }

        return isBalancedRecursive(node.left)
                && isBalancedRecursive(node.right);
    }

    public List<Object> unbalancedNodes() {

        List<Object> result = new ArrayList<>();

        findUnbalanced(root, result);

        return result;
    }

    private void findUnbalanced(
            TreeNode node, List<Object> result) {

        if (node == null) {
            return;
        }

        int factor = balanceFactor(node);

        if (factor < -1 || factor > 1) {
            result.add(node.data);
        }

        findUnbalanced(node.left, result);
        findUnbalanced(node.right, result);
    }

    public void printBalanceFactors() {

        if (root == null) {
            System.out.println("El arbol esta vacio.");
            return;
        }

        System.out.println("\nArbol con factores de equilibrio:");

        printFactorsRecursive(root, "", true);
    }

    private void printFactorsRecursive(
            TreeNode node, String prefix, boolean isTail) {

        if (node == null) {
            return;
        }

        System.out.println(prefix
                + (isTail ? "└── " : "├── ")
                + node.data
                + " (FE: "
                + balanceFactor(node) + ")");

        String newPrefix = prefix
                + (isTail ? "    " : "│   ");

        if (node.left != null && node.right != null) {

            printFactorsRecursive(node.left, newPrefix, false);
            printFactorsRecursive(node.right, newPrefix, true);

        } else if (node.left != null) {

            printFactorsRecursive(node.left, newPrefix, true);

        } else if (node.right != null) {

            printFactorsRecursive(node.right, newPrefix, true);
        }
    }

    // ==========================================
    // 5. ROTACIONES AVL
    // ==========================================

    // Rotacion simple derecha (LL)

    public TreeNode rotateRight(TreeNode node) {

        if (node == null || node.left == null) {
            return node;
        }

        TreeNode newRoot = node.left;
        TreeNode temp = newRoot.right;

        newRoot.right = node;
        node.left = temp;

        return newRoot;
    }

    // Rotacion simple izquierda (RR)

    public TreeNode rotateLeft(TreeNode node) {

        if (node == null || node.right == null) {
            return node;
        }

        TreeNode newRoot = node.right;
        TreeNode temp = newRoot.left;

        newRoot.left = node;
        node.right = temp;

        return newRoot;
    }

    // Rotacion doble izquierda-derecha (LR)

    public TreeNode rotateLeftRight(TreeNode node) {

        if (node == null || node.left == null) {
            return node;
        }

        node.left = rotateLeft(node.left);

        return rotateRight(node);
    }

    // Rotacion doble derecha-izquierda (RL)

    public TreeNode rotateRightLeft(TreeNode node) {

        if (node == null || node.right == null) {
            return node;
        }

        node.right = rotateRight(node.right);

        return rotateLeft(node);
    }

    // ==========================================
    // BALANCEO COMPLETO AVL
    // ==========================================

    public void balance() {

        if (root == null) {
            System.out.println("El arbol esta vacio.");
            return;
        }

        System.out.println("\n===== ANTES DEL BALANCEO =====");

        printBalanceFactors();

        System.out.println("\nNodos que requieren balanceo:");

        List<Object> unbalanced = unbalancedNodes();

        if (unbalanced.isEmpty()) {
            System.out.println("Ninguno. El arbol ya esta balanceado.");
        } else {
            System.out.println(unbalanced);
        }

        // Se repiten las rotaciones hasta conseguir
        // que todos los nodos esten balanceados.

        while (!isBalanced()) {
            root = rebalanceRecursive(root);
        }

        System.out.println("\n===== DESPUES DEL BALANCEO =====");

        printBalanceFactors();

        System.out.println("\nArbol balanceado: " + isBalanced());
    }

    private TreeNode rebalanceRecursive(TreeNode node) {

        if (node == null) {
            return null;
        }

        // Primero se balancean los hijos.

        node.left = rebalanceRecursive(node.left);
        node.right = rebalanceRecursive(node.right);

        int factor = balanceFactor(node);

        // Caso LL o LR.

        if (factor < -1) {

            if (balanceFactor(node.left) > 0) {
                return rotateLeftRight(node);
            }

            return rotateRight(node);
        }

        // Caso RR o RL.

        if (factor > 1) {

            if (balanceFactor(node.right) < 0) {
                return rotateRightLeft(node);
            }

            return rotateLeft(node);
        }

        return node;
    }

    // ==========================================
    // 6. MENU PRINCIPAL
    // ==========================================

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        BinaryTree tree = new BinaryTree();

        int option;

        do {

            System.out.println("\n========== MENU ==========");
            System.out.println("1. Construir arbol");
            System.out.println("2. Mostrar arbol");
            System.out.println("3. Recorrido Preorden");
            System.out.println("4. Recorrido Inorden");
            System.out.println("5. Recorrido Posorden");
            System.out.println("6. Mostrar factores de equilibrio");
            System.out.println("7. Balancear arbol (AVL)");
            System.out.println("8. Salir");
            System.out.println("==========================");

            System.out.print("Seleccione una opcion: ");

            try {
                option = Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                option = 0;
            }

            switch (option) {

                case 1:
                    tree = new BinaryTree();
                    tree.buildTree(scanner);
                    break;

                case 2:
                    tree.printTree();
                    break;

                case 3:
                    System.out.println("Preorden: "
                            + tree.preorder());
                    break;

                case 4:
                    System.out.println("Inorden: "
                            + tree.inorder());
                    break;

                case 5:
                    System.out.println("Posorden: "
                            + tree.postorder());
                    break;

                case 6:
                    tree.printBalanceFactors();
                    break;

                case 7:
                    tree.balance();
                    break;

                case 8:
                    System.out.println("Programa finalizado.");
                    break;

                default:
                    System.out.println("Opcion no valida.");
            }

        } while (option != 8);

        scanner.close();
    }
}
