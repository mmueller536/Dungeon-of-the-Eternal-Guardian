package Game;
import java.util.List;
import java.util.ArrayList;

/**
 * Creates the loot tree for the game
 * This tree our take on a general tree
 * @param <T> the items that can be placed within in the form of a string
 */
public class Tree<T> {
    private Node<T> root;
    /**
     * Creates a tree
     */
    public Tree(){
    	root = new Node<T>(null);
    }
    /**
     * adds root data when creating a tree
     * 
     * @param rootData the roots data to be added
     */
    public Tree(T rootData){
    	root = new Node<T>(rootData);
    }
    /**
     * Allows the setting of data for the root
     * 
     * @param rootData data to be set
     */
    public void setRootData(T rootData){
    	root.setData(rootData);
    }
    /**
     * Allows access of the roots data
     * 
     * @return the data from the root
     */
    public T getRootData(){
    	return root.getData();
    }
    /**
     * Allows addition of children
     * 
     * @param amount1	the tree to be added
     * @param n	the location in the Array list to add the tree
     */
    public void setChild(Tree<T> amount1, int n){
    	root.setChild(amount1, n);
    }
    /**
     * Allows user to get the child of the root
     * 
     * @param n	the location in the array list of the child
     * @return	the child tree
     */
    public Tree<T> getChild(int n){
    	return root.getChild(n);
    }
    /**
     * Allows the user to get the parent of a tree
     * 
     * @return the parent of a tree
     */
    public Tree<T> getParent(){
    	return root.getParent();
    }
    /**
     * Allows setting of the parent of a tree
     * @param parent the parent to be set
     */
    public void setParent(Tree<T> parent){
    	root.setParent(parent);
    }
    
    public class Node<T> {
        private T data;
        private Tree<T> parent;
        private List<Tree<T>> children; //list of children
        
        /**
         * The actual root node
         * 
         * @param data the data of that node
         */
        public Node(T data){
        	this.data = data;
        	children = new ArrayList(3);
        }
        /**
         * A general constructor in case the user does not specify anything
         */
        public Node(){
        	this(null);
        }
        /**
         * Allows the program to get the data of the root node
         * @return
         */
        public T getData(){
        	return data;
        }
        /**
         * Allows the program to set that data found within the root
         * 
         * @param anEntry that data to be added
         */
        public void setData(T anEntry){
        	data = anEntry;
        }
        /**
         * Allows the program to get a child at location n
         * 
         * @param n the location of the child
         * @return the child
         */
        public Tree<T> getChild(int n){
        	return children.get(n);
        }
        /**
         * Allows the program to set a child in the array list
         * 
         * @param newChild the new tree to be added
         * @param n the location of this new tree
         */
        public void setChild(Tree<T> newChild, int n){
        	children.add(n, newChild);
        	
        }
        /**
         * Allows the program to set the parent of the node
         * 
         * @param parent the tree to be set as parent
         */
        public void setParent(Tree<T> parent){
        	this.parent = parent;
        }
        /**
         * Allows the program to get the parent of a tree
         * 
         * @return	the parent tree
         */
        public Tree<T> getParent(){
        	return parent;
        }
    }
}