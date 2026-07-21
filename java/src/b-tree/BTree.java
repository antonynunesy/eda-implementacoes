import java.util.ArrayList;

public class BTree{

    private BNode root;

    public BTree(){
        this.root = null;
    }

    //VICTOR
    public void insert(int value){
    }

    //VICTOR
    public boolean isEmpty(){
        return root == null;
    }

    //ANTONY
    public int height(){
        return 0;
    }

    //ANTONY
    public BNode search(int value){
        return null;
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
    
    //VICTOR
    private void split(BNode node){
    }

    //ANTONY
    private BNode getRoot(){
        return root;
    }

}

//VICTOR
class BNode{
    
    private BNode parent;
    private ArrayList<Integer> keys;
    private ArrayList<BNode> children;
    
    public BNode(BNode parent){
        this.keys = new ArrayList<>();
        this.children = new ArrayList<>();
    }
}
