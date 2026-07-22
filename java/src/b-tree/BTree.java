import java.util.ArrayList;
import java.util.Pair;

public class BTree{

    private BNode root;
    private int order;

    public BTree(int order){
        this.root = null;
        this.order = order;
    }

    public BTree(){
        this.root = null;
        this.order = 4;
    }

    //VICTOR
    public void insert(int value){
    }

    public boolean isEmpty(){
        return root == null;
    }

    //ANTONY
    public int height(){
        return 0;
    }

    public BNodePosition search(int value){
        return search(root, value);
    }

    private BNodePosition search(BNode node, int value){
        int i = 0;
        while(i < node.size && value > node.keys.get(i)) {
            i++;
        }
        if(i < node.size && value == node.keys.get(i)) {
            return new BNodePosition(node, i);
        }
        if(!node.isLeaf()) {
            return search(node.children.get(i), value);
        }

        return new BNodePosition(null, null);
    }

    //VICTOR
    public BNode max(){
        return null;
    }

    //VICTOR
    public BNode min(){
        return null;
    }

    //ANTONY
    public void remove(int value){
    }

    //VICTOR
    public ArrayList<BNode> depthFS(){
        return null;
    }

    //ANTONY
    public ArrayList<BNode> breadthFS(){
        return null;
    }

    //ANTONY
    public int size(){
        return 0;
    }

    private void split(BNode node){
        BNode left = new BNode();
        for(int i = 0; i < (order - 1) / 2; i++){
            left.addKey(node.keys.get(i));
        }
        BNode right = new BNode();
        for(int i = (order - 1) / 2 + 1; i < node.keys.size(); i++){
            right.addKey(node.keys.get(i));
        }

        if(node.parent == null) node.parent = new BNode();
        BNode parent = node.parent;

        left.parent = parent;
        right.parent = parent;

        int index = parent.addKey(node.keys.get((order - 1) / 2));
    
        parent.children.remove(node);
        parent.children.add(index, left);
        parent.children.add(index + 1, right);
    }

    //ANTONY
    private BNode getRoot(){
        return root;
    }

}

//VICTOR
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
}
