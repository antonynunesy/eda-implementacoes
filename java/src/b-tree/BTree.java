import java.util.ArrayList;
import java.util.Queue;
import java.util.LinkedList;

public class BTree{

    private BNode root;
    private int order;

    public BTree(int order) {
        this.root = null;
        this.order = order;
    }

    public BTree() {
        this.root = null;
        this.order = 4;
    }

    public void recursiveInsert(int value) {
        if(isEmpty()){
            root = new BNode();
            root.addKey(value);
        } else {
            recursiveInsert(root, value);
        }
    }

    private void recursiveInsert(BNode node, int value) {
        if(node.size == order-1){
                split(node);
                recursiveInsert(node.parent, value);
        } else if(node.isLeaf()){
            node.addKey(value);
            if(node.size == order-1){
                split(node);
            }
        } else {
            int i = 0;
            while(i < node.size && value > node.keys.get(i)) {
                i++;
            }
            recursiveInsert(node.children.get(i), value);
        }
    }

    public boolean isEmpty() {
        return root == null;
    }

    public int height(){
        if(isEmpty()) return 0;
        return height(root);
    }

    private int height(BNode node){
        if(node.isLeaf()) return 1;
        return 1 + height(node.children.get(0));
    }


    public BNodePosition recursiveSearch(int value) {
        return recursiveSearch(root, value);
    }

    private BNodePosition recursiveSearch(BNode node, int value) {
        int i = 0;
        while(i < node.size && value > node.keys.get(i)) {
            i++;
        }
        if(i < node.size && value == node.keys.get(i)) {
            return new BNodePosition(node, i);
        }
        if(!node.isLeaf()) {
            return recursiveSearch(node.children.get(i), value);
        }

        return new BNodePosition(null, null);
    }

    public BNodePosition recursiveMax() {
        if(isEmpty()) {
            return new BNodePosition(null, null);
        }

        return recursiveMax(root);
    }

    private BNodePosition recursiveMax(BNode node) {
         if(node.isLeaf()) {
            return new BNodePosition(node, node.keys.size()-1);
        }

        return recursiveMax(node.children.get(node.children.size()-1));
    }

    public BNodePosition recursiveMin() {
        if(isEmpty()) {
            return new BNodePosition(null, null);
        }

        return recursiveMin(root);
    }

    private BNodePosition recursiveMin(BNode node) {
        if(node.isLeaf()) {
            return new BNodePosition(node, 0);
        }

        return recursiveMin(node.children.get(0));
    }

    //ANTONY
    public void remove(int value){
    }

    //VICTOR
    public ArrayList<BNode> depthFS(){
        return null;
    }

    public ArrayList<BNode> breadthFS(){
        ArrayList<BNode> result = new ArrayList<>();

        if(isEmpty()) return result;

        Queue<BNode> queue = new LinkedList<>();
        queue.add(root);

        while(!queue.isEmpty()){
            BNode current = queue.poll();
            result.add(current);

            if(!current.isLeaf()){
                for(BNode child : current.children){
                    queue.add(child);
                }
            }
        }
        return result;
    }        


    //ANTONY
    public int size(){
        return 0;
    }

    private void split(BNode node){
        //Nó da esquerda
        BNode left = new BNode();
        //Primeira metade da keys no nó da esquerda
        for(int i = 0; i < (order - 1) / 2; i++){
            left.addKey(node.keys.get(i));
        }
        //Primeira metade dos filhos no nó da esquerda
        if(!node.isLeaf()){
            for(int i = 0; i < order/2; i++){
                left.children.add(node.children.get(i));
                node.children.get(i).parent = left;
            }
        }

        //Nó da direita
        BNode right = new BNode();
        //Segunda metade da keys no nó da direita
        for(int i = (order - 1) / 2 + 1; i < node.keys.size(); i++){
            right.addKey(node.keys.get(i));
        }
        //Segunda metade dos filhos no nó da direita
        if(!node.isLeaf()){
            for(int i = order/2; i < node.children.size(); i++){
                right.children.add(node.children.get(i));
                node.children.get(i).parent = right;
            }
        }

        //Se for raiz
        if(node.parent == null){
            node.parent = new BNode();
            root = node.parent;
        }

        BNode parent = node.parent;

        //Atribui novos filhos
        left.parent = parent;
        right.parent = parent;

        int index = parent.addKey(node.keys.get((order - 1) / 2));
    
        parent.children.remove(node);
        parent.children.add(index, left);
        parent.children.add(index + 1, right);
    }

    public BNode getRoot(){
        return root;
    }

}

class BNode{
    
    BNode parent;
    ArrayList<Integer> keys;
    ArrayList<BNode> children;
    int size;

    public BNode(){
        this.keys = new ArrayList<>();
        this.children = new ArrayList<>();
        this.size = 0;
    }

    public int addKey(int key){
        int i = 0;
        while(i < keys.size() && key > keys.get(i)) {
            i++;
        }
        
        keys.add(i, key);
        size++;

        return i;
    }

    public boolean isLeaf(){
        return children.isEmpty();
    }
}

class BNodePosition{
    
    BNode node;
    Integer position;

    public BNodePosition(BNode node, Integer position){
        this.node = node;
        this.position = position;
    }

    public Integer getValue() {
        if(node == null || position == null) return null;
        return node.keys.get(position);
    }
}
