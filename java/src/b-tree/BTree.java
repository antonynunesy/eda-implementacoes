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

    /**
     * Insere um valor na árvore de forma recursiva.
     *
     * @param value valor a ser inserido
     */
    public void recursiveInsert(int value) {
        if(isEmpty()){
            root = new BNode(this.order);
            root.addKey(value);
        } else {
            if(root.isFull()){
                split(root);
            }
            recursiveInsert(root, value);
        }
    }

    /**
     * Auxilia a inserção recursiva, percorrendo os nós da árvore até encontrar uma folha.
     * A decisão para qual filho seguir é feita por busca binária.
     *
     * @param node nó atual da recursão
     * @param value valor a ser inserido
     */
    private void recursiveInsert(BNode node, int value) {
        if(node.isLeaf()){
            node.addKey(value);
        } else {
            int idx = buscaBinaria(node, value);
            BNode child = node.children.get(idx);
            if(child.isFull()) {
                split(child);
                if(value > node.keys.get(idx)) {
                    idx++;
                }
            }
            recursiveInsert(node.children.get(idx), value);
        }
    }

    /**
     * Insere um valor na árvore B de forma iterativa.
     * A decisão para qual filho seguir é feita por busca linear.
     *
     * @param value valor a ser inserido
     */
    public void insert(int value) {
        if(isEmpty()){
            root = new BNode(this.order);
            root.addKey(value);
        } else {
            if(root.isFull()){
                split(root);
            }

            BNode node = root;
            while(!node.isLeaf()){
                int idx = buscaLinear(node, value);
                BNode child = node.children.get(idx);

                if(child.isFull()) {
                    split(child);
                    if(value > node.keys.get(idx)) {
                        idx++;
                    }
                }
                node = node.children.get(idx);
            }

            node.addKey(value);
        }
    }

    /**
     * Verifica se a árvore está vazia.
     *
     * @return true se a árvore não possuir raiz; false caso contrário
     */
    public boolean isEmpty() {
        return root == null;
    }

    /**
     * Retorna a altura da árvore B.
     *
     * @return número de níveis da árvore
     */
    public int height(){
        if(isEmpty()) return 0;
        return height(root);
    }

    /**
     * Calcula a altura da subárvore a partir de um nó.
     *
     * @param node raiz da subárvore
     * @return altura da subárvore
     */
    private int height(BNode node){
        if(node.isLeaf()) return 1;
        return 1 + height(node.children.get(0));
    }

    /**
     * Realiza uma busca recursiva por um valor.
     *
     * @param value valor procurado
     * @return posição do nó e da chave, ou uma posição vazia caso não seja encontrado
     */
    public BNodePosition recursiveSearch(int value) {
        return recursiveSearch(root, value);
    }

    /**
     * Auxilia a busca recursiva percorrendo a árvore até encontrar a chave ou uma folha.
     * A decisão para qual filho seguir é feita por busca binária.
     *
     * @param node nó atual da recursão
     * @param value valor procurado
     * @return posição do nó e da chave, ou uma posição vazia caso não seja encontrado
     */
    private BNodePosition recursiveSearch(BNode node, int value) {
        int idx = buscaBinaria(node, value);

        if(idx < node.size && value == node.keys.get(idx)) {
            return new BNodePosition(node, idx);
        }
        if(!node.isLeaf()) {
            return recursiveSearch(node.children.get(idx), value);
        }

        return new BNodePosition();
    }

    /**
     * Realiza uma busca iterativa por um valor.
     * A decisão para qual filho seguir é feita por busca linear.
     *
     * @param value valor procurado
     * @return posição do nó e da chave, ou uma posição vazia caso não seja encontrado
     */
    public BNodePosition search(int value) {
        BNode node = root;
        while(node != null) {
            int idx = buscaLinear(node, value);
            if(idx < node.size && value == node.keys.get(idx)) {
                return new BNodePosition(node, idx);
            }
            node = node.children.get(idx);
        }

        return new BNodePosition();
    }

    /**
     * Retorna a posição da maior chave da árvore de forma recursiva.
     *
     * @return posição da maior chave ou uma posição vazia se a árvore estiver vazia
     */
    public BNodePosition recursiveMax() {
        if(isEmpty()) {
            return new BNodePosition();
        }

        return recursiveMax(root);
    }

    /**
     * Auxilia a busca do maior valor de forma recursiva.
     *
     * @param node nó atual da recursão
     * @return posição da maior chave da subárvore
     */
    private BNodePosition recursiveMax(BNode node) {
         if(node.isLeaf()) {
            return new BNodePosition(node, node.size-1);
        }

        return recursiveMax(node.children.get(node.children.size()-1));
    }

    /**
     * Retorna a posição da maior chave da árvore de forma iterativa.
     *
     * @return posição da maior chave ou uma posição vazia se a árvore estiver vazia
     */
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

    /**
     * Retorna a posição da menor chave da árvore de forma recursiva.
     *
     * @return posição da menor chave ou uma posição vazia se a árvore estiver vazia
     */
    public BNodePosition recursiveMin() {
        if(isEmpty()) {
            return new BNodePosition();
        }

        return recursiveMin(root);
    }

    /**
     * Auxilia a busca do menor valor de forma recursiva.
     *
     * @param node nó atual da recursão
     * @return posição da menor chave da subárvore
     */
    private BNodePosition recursiveMin(BNode node) {
        if(node.isLeaf()) {
            return new BNodePosition(node, 0);
        }

        return recursiveMin(node.children.get(0));
    }

    /**
     * Retorna a posição da menor chave da árvore de forma iterativa.
     *
     * @return posição da menor chave ou uma posição vazia se a árvore estiver vazia
     */
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

    /**
     * Remove um valor da árvore.
     *
     * @param value valor a ser removido
     */
    //ANTONY
    public void remove(int value){
    }

    /**
     * Retorna os nós da árvore em ordem de profundidade.
     *
     * @return lista com os nós visitados em profundidade
     */
    public ArrayList<BNode> depthFS(){
        ArrayList<BNode> nodes = new ArrayList<>();
        depthFS(root, nodes);
        return nodes;
    }

    /**
     * Percorre a árvore em profundidade adicionando os nós visitados em uma lista.
     *
     * @param node nó atual da travessia
     * @param nodes lista que armazena os nós visitados
     */
    private void depthFS(BNode node, ArrayList<BNode> nodes){
        if(node == null) return;

        nodes.add(node);

        for(BNode child : node.children){
            depthFS(child, nodes);
        }
    }

    /**
     * Retorna os nós da árvore em ordem de largura.
     *
     * @return lista com os nós visitados em largura
     */
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

    /**
     * Retorna o número de elementos armazenados na árvore.
     *
     * @return quantidade de chaves presentes na árvore
     */
    public int size(){
        return 0;
    }

    /**
     * Divide um nó cheio em dois nós filhos e promove a chave do meio para o pai.
     *
     * @param node nó cheio a ser dividido
     */
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

    /**
     * Retorna a raiz da árvore B.
     *
     * @return nó raiz da árvore
     */
    public BNode getRoot(){
        return root;
    }

    /**
     * Realiza uma busca binária entre as chaves de um nó.
     *
     * @param node nó onde será realizada a busca
     * @param value valor procurado
     * @return índice da posição do filho no qual seguir a busca
     */
    private int buscaBinaria(BNode node, int value) {
        int init = 0, end = node.size-1, idx = 0;
        while(init <= end) {
            int mid = (init + end) / 2;
            if(node.keys.get(mid) <= value) {
                idx = mid+1;
                init = mid+1;
            } else {
                end = mid-1;
            }
        }
        return idx;
    }

    /**
     * Realiza uma busca linear entre as chaves de um nó.
     *
     * @param node nó onde será realizada a busca
     * @param value valor procurado
     * @return índice da posição do filho no qual seguir a busca
     */
    private int buscaLinear(BNode node, int value) {
        int idx = 0;
        while(idx < node.size && value > node.keys.get(idx)) {
            idx++;
        }
        return idx;
    }

}

/**
 * Representa um nó de uma árvore B.
 */
class BNode{
    
    BNode parent;
    ArrayList<Integer> keys;
    ArrayList<BNode> children;
    int size;
    int order;

    /**
     * Cria um nó da árvore B com uma ordem específica.
     *
     * @param order ordem do nó
     */
    public BNode(int order){
        this.keys = new ArrayList<>();
        this.children = new ArrayList<>();
        this.size = 0;
        this.order = order;
    }

    /**
     * Adiciona uma chave ao nó, mantendo a ordem crescente.
     *
     * @param key chave a ser adicionada
     * @return índice em que a chave foi inserida
     */
    public int addKey(int key){
        int i = 0;
        while(i < size && key > keys.get(i)) {
            i++;
        }
        
        keys.add(i, key);
        size++;

        return i;
    }

    /**
     * Verifica se o nó é uma folha.
     *
     * @return true se o nó não possuir filhos; false caso contrário
     */
    public boolean isLeaf(){
        return children.isEmpty();
    }

    /**
     * Verifica se o nó está cheio.
     *
     * @return true se o nó atingir o limite de chaves permitidas
     */
    public boolean isFull(){
        return size == order-1;
    }
}

/**
 * Representa uma posição de uma chave dentro de um nó da árvore B.
 */
class BNodePosition{
    
    BNode node;
    int position;

    /**
     * Cria uma posição vazia, indicando que nenhum nó foi encontrado.
     */
    public BNodePosition(){
        this.node = null;
        this.position = -1;
    }

    /**
     * Cria uma posição apontando para um nó e uma chave específica.
     *
     * @param node nó onde a chave está localizada
     * @param position índice da chave dentro do nó
     */
    public BNodePosition(BNode node, int position){
        this.node = node;
        this.position = position;
    }

    /**
     * Retorna o valor armazenado na posição atual.
     *
     * @return valor da chave ou null se a posição for inválida
     */
    public Integer getValue() {
        if(node == null || position == -1) return null;
        return node.keys.get(position);
    }
}
