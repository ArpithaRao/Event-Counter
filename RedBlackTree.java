public class RedBlackTree {

	public int id;
    public int count;
    public RedBlackTree left = null;
    public RedBlackTree right = null;
    public RedBlackTree parent;
    public boolean color = true;

    
    public RedBlackTree(int key, int value, RedBlackTree parent) {
        this.id = key;
        this.count = value;
        this.parent = parent;
    }

    
    public int getKey() {
        return id;
    }

    
    public int getValue() {
        return count;
    }

    
    public int setValue(int value) {
        int oldValue = this.count;
        this.count = value;
        return oldValue;
    }
	
}
