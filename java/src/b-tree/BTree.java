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
            root = new BNode(this.order);
            root.addKey(value);
        } else {
            recursiveInsert(root, value);
        }
    }

    private void recursiveInsert(BNode node, int value) {
        if(node.isFull()){
                split(node);
                recursiveInsert(node.parent, value);
        } else if(node.isLeaf()){
            node.addKey(value);
            if(node.isFull()){
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

    public void insert(int value) {
        if(isEmpty()){
            root = new BNode(this.order);
            root.addKey(value);
        } else {
            BNode node = root;
            while(!node.isLeaf()){
                if(node.isFull()){
                    split(node);
                    node = node.parent;
                }
                
                int i = 0;
                while(i < node.size && value > node.keys.get(i)) {
                    i++;
                }
                node = node.children.get(i);
            }

            node.addKey(value);
            if(node.isFull()){
                split(node);
            }
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

        return new BNodePosition();
    }

    public BNodePosition search(int value) {
        BNode node = root;
        while(node != null) {
            int i = 0;
            while(i < node.size && value > node.keys.get(i)) {
                i++;
            }
            if(i < node.size && value == node.keys.get(i)) {
                return new BNodePosition(node, i);
            }
            node = node.children.get(i);
        }

        return new BNodePosition();
    }

    public BNodePosition recursiveMax() {
        if(isEmpty()) {
            return new BNodePosition();
        }

        return recursiveMax(root);
    }

    private BNodePosition recursiveMax(BNode node) {
         if(node.isLeaf()) {
            return new BNodePosition(node, node.size-1);
        }

        return recursiveMax(node.children.get(node.children.size()-1));
    }

    public BNodePosition max() {
        if(isEmpty()) {
            return new BNodePosition();
        }

        BNode node = root;
        while(!node.isLeaf()) {
            node = node.children.get(node.children.size()-1);
        }
        return new BNodePosition(node, node.size-1);
    }

    public BNodePosition recursiveMin() {
        if(isEmpty()) {
            return new BNodePosition();
        }

        return recursiveMin(root);
    }

    private BNodePosition recursiveMin(BNode node) {
        if(node.isLeaf()) {
            return new BNodePosition(node, 0);
        }

        return recursiveMin(node.children.get(0));
    }

    public BNodePosition min() {
        if(isEmpty()) {
            return new BNodePosition();
        }

        BNode node = root;
        while(!node.isLeaf()) {
            node = node.children.get(0);
        }
        return new BNodePosition(node, 0);
    }

    //ANTONY
    public void remove(int value){
    }

    public ArrayList<BNode> depthFS(){
        ArrayList<BNode> nodes = new ArrayList<>();
        depthFS(root, nodes);
        return nodes;
    }

    private void depthFS(BNode node, ArrayList<BNode> nodes){
        if(node == null) return;

        nodes.add(node);

        for(BNode child : node.children){
            depthFS(child, nodes);
        }
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
        BNode left = new BNode(this.order);
        //Primeira metade da keys no nó da esquerda
        for(int i = 0; i < (this.order - 1) / 2; i++){
            left.addKey(node.keys.get(i));
        }
        //Primeira metade dos filhos no nó da esquerda
        if(!node.isLeaf()){
            for(int i = 0; i < this.order/2; i++){
                left.children.add(node.children.get(i));
                node.children.get(i).parent = left;
            }
        }

        //Nó da direita
        BNode right = new BNode(this.order);
        //Segunda metade da keys no nó da direita
        for(int i = (this.order - 1) / 2 + 1; i < node.size; i++){
            right.addKey(node.keys.get(i));
        }
        //Segunda metade dos filhos no nó da direita
        if(!node.isLeaf()){
            for(int i = this.order/2; i < node.children.size(); i++){
                right.children.add(node.children.get(i));
                node.children.get(i).parent = right;
            }
        }

        //Se for raiz
        if(node.parent == null){
            node.parent = new BNode(this.order);
            root = node.parent;
        }

        BNode parent = node.parent;

        //Atribui novos filhos
        left.parent = parent;
        right.parent = parent;

        int index = parent.addKey(node.keys.get((this.order - 1) / 2));
    
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
    int order;

    public BNode(int order){
        this.keys = new ArrayList<>();
        this.children = new ArrayList<>();
        this.size = 0;
        this.order = order;
    }

    public int addKey(int key){
        int i = 0;
        while(i < size && key > keys.get(i)) {
            i++;
        }
        
        keys.add(i, key);
        size++;

        return i;
    }

    public boolean isLeaf(){
        return children.isEmpty();
    }

    public boolean isFull(){
        return size == order-1;
    }
}

class BNodePosition{
    
    BNode node;
    int position;

    public BNodePosition(){
        this.node = null;
        this.position = -1;
    }

    public BNodePosition(BNode node, int position){
        this.node = node;
        this.position = position;
    }

    public Integer getValue() {
        if(node == null || position == -1) return null;
        return node.keys.get(position);
    }
}
