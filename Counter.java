import java.io.BufferedReader;
import java.io.IOException;


public class Counter {

	
	private boolean RED   = false;
    private boolean BLACK = true;
	
    public String output = "";
    
	private int modCount = 0;
	private int size = 0;
	
	RedBlackTree root = null;

    RedBlackTree nodeFound = null;

    /*Building RBTree in linear time by taking the first argument of the
    test input file as the length of the input and recursively building tree with key value pairs*/

    public void init(BufferedReader fileReader, int numPairs)
            throws IOException {
        int start = 0;
        int end = numPairs - 1;
        int mid = start + (end - start)/2;
        RedBlackTree leftChild = init(0, mid-1, fileReader);
        String line = fileReader.readLine();
        String kvPairs[] = line.split(" ");
        root = new RedBlackTree(Integer.parseInt(kvPairs[0]), Integer.parseInt(kvPairs[1]), null);
        root.left = leftChild;
        if(root.left != null)
            root.left.parent = root;
        root.right = init(mid+1, end, fileReader);
        root.color = BLACK;
        if(root.right != null)
            root.right.parent = root;
        int maxHeight = maxHeight(root, 0);
        //Color only nodes at maxHeight red, color the rest black.
        if(maxHeight > 1)
            colorRedAtHeight(root,1,maxHeight);

    }

    private RedBlackTree init(int start, int end, BufferedReader fileReader)
            throws IOException{
        if(start>end)
            return null;
        int mid = start + (end-start)/2;
        RedBlackTree leftChild = init(start, mid - 1, fileReader);
        String line = fileReader.readLine();
        String kvPairs[] = line.split(" ");
        RedBlackTree subTreeRoot = new RedBlackTree(Integer.parseInt(kvPairs[0]), Integer.parseInt(kvPairs[1]), null);
        subTreeRoot.left = leftChild;
        if(subTreeRoot.left != null)
            subTreeRoot.left.parent = subTreeRoot;
        subTreeRoot.right = init(mid+1, end, fileReader);
        subTreeRoot.color = BLACK;
        if(subTreeRoot.right != null)
            subTreeRoot.right.parent = subTreeRoot;
        return subTreeRoot;
    }

    /*to obtain the maximum height of the tree*/

    public int maxHeight(RedBlackTree root, int height){
        if(root == null)
            return height;
        height++;
        return Math.max(maxHeight(root.left, height), maxHeight(root.right, height));
    }

    /*color the tree recursively starting at root*/

    public void colorRedAtHeight(RedBlackTree node, int height,int maxHeight){
        if(node == null)
            return;
        if(height == maxHeight)
            node.color = RED;
        else {
            colorRedAtHeight(node.left, height + 1, maxHeight);
            colorRedAtHeight(node.right, height + 1, maxHeight);
        }

    }

    /*Increases the count of the event theID by m. If theID is not present, inserts theID. Prints the count
    of theID after the addition*/
		
	public void increase(int theID, int m){
		RedBlackTree node = get(theID);
		if(node == null){
			put(theID, m);
            System.out.println(m);
		}

        else{
			node.count = node.count + m;
			System.out.println(node.count);
			output = output + node.count;
		}
	}

    /*Decreases the count of theID by m. If theIDs count becomes less than or equal to 0, removes
    theID from the counter. Prints the count of theID after the deletion, or 0 if theID is
    removed or not present.*/


	public void reduce(int theID, int m){
		RedBlackTree node = get(theID);
		if(node == null){
			System.out.println("0");
		}else{
			node.count = node.count - m;

			output = output + node.count;
			
			if(node.count <= 0){
				remove(theID);
                System.out.println("0");
			}else{
                System.out.println(node.count);
            }
		}
	}
    /*Prints the count of theID. If not present, prints 0*/
	public void count(int theID){
		RedBlackTree node = get(theID);
		if (node == null){
			System.out.println("0");
			output = output +"0";
		}else{
			System.out.println(node.count);
			output = output + node.count;
		}
		
	}
    /* This function will find the successor of a node in order to find the next node in the tree,
    minVal returns the leftmost child of the root/node or the minimum most value in that subtree*/

    RedBlackTree successorforNode(RedBlackTree node){
        RedBlackTree curr = node;
        RedBlackTree parent ;
        if(curr.right == null){
            parent=curr.parent;
            while(parent != root && curr == parent.right){
                curr = parent;
                parent = parent.parent;
            }
            return parent;
        }else{
            return minVal(curr.right);
        }
    }

     /* Prints the ID and the count of the event with the lowest ID that is greater that theID. Prints
    0 0, if there is no next ID.*/

    public void next(int theId){
        RedBlackTree p = root;
        int k = theId;
        while(p != null){
            if(k < p.getKey()){
                //k == p.getKey() we move to the right child because we need to find the number less than given Id
                if(p.left != null)
                    p = p.left;
                else break;
            } else {
                if(p.right != null)
                    p = p.right;
                else break;
            }
        }
        int next = Integer.MAX_VALUE;
        boolean isSet = false;
        do{
            if(p.getKey() > k && next > p.getKey()) {
                next = p.getKey();
                isSet = true;
            }
            p = p.parent;
        }while(p!=null);

        if(isSet){
            RedBlackTree nextNode = get(next);
            System.out.println(nextNode.getKey() + " "+ nextNode.getValue());
        } else System.out.println("0 0");
    }

    /*Prints the ID and the count of the event with the greatest key that is less than theID. Prints
    0 0, if there is no previous ID.*/

    public void previous(int theId){
        RedBlackTree p = root;
        int k = theId;
        while(p != null){
            if(k > p.getKey()){
                //k == p.getKey() we move to the left child because we need to find the number less than given Id
                if(p.right != null)
                    p = p.right;
                else break;
            } else {
                if(p.left != null)
                    p = p.left;
                else break;
            }
        }
        int previous = Integer.MIN_VALUE;
        boolean isSet = false;
        do{
            if(p.getKey() < k && previous < p.getKey()) {
                previous = p.getKey();
                isSet = true;
            }
            p = p.parent;
        }while(p!=null);

        if(isSet){
            RedBlackTree prev = get(previous);
            System.out.println(prev.getKey() + " "+ prev.getValue());
        } else System.out.println("0 0");


    }

    /*This method finds the leftmost node from a node*/

    RedBlackTree minVal(RedBlackTree node){

        if(node == null) return node;

        while(node!= null){
            node = node.left;
        }
        return node;
    }



   /* Prints the total count for IDs between ID1 and ID2 inclusively and ID1 <= ID2 in O(log n + s),
   checking for base condition and calling inRangeUtil method*/

    void inRange(int ID1, int ID2)
    {
        int totalCount =0;
        if(ID1 <= ID2)
            totalCount = inRangeUtil(this.root, ID1, ID2);

        System.out.println(totalCount);
    }


    /*This method returns the total count of nodes between ID1 and ID2 inclusive to the function inRange,
    by recursively traversing left and right nodes from a node if the condition is satisfied */

    int inRangeUtil(RedBlackTree t, int ID1,int ID2)
    {
        if(t==null)
            return 0;
        else
        {
            if(t.id >= ID1 && ID2 >= t.id)
            {
                return  t.count + inRangeUtil(t.left, ID1, ID2) + inRangeUtil(t.right, ID1, ID2);
            }

            else if(t.id <= ID1)
            {
                return inRangeUtil(t.right, ID1, ID2);
            }

            else
                return inRangeUtil(t.left, ID1, ID2);
        }
    }

    /*removes the node of the particular id from the tree, checking for base case*/
	public int remove(int key) {
        RedBlackTree p = getEntry(key);
        if (p == null)
            return 0;

        int oldValue = p.count;
        deleteEntry(p);
        return oldValue;
    }

    /* deletes the particular node, finds replacement and balances the binary tree once a node is deleted and recolouring id done,
    by checking for base condition and other valid and invalid conditions*/
	private void deleteEntry(RedBlackTree p) {
        decrementSize();

        if (p.left != null && p.right != null) {
            RedBlackTree s = successorforNode(p);
            p.id = s.id;
            p.count = s.count;
            p = s;
        } 
        RedBlackTree replacement = (p.left != null ? p.left : p.right);

        if (replacement != null) {
           
            replacement.parent = p.parent;
            if (p.parent == null)
                root = replacement;
            else if (p == p.parent.left)
                p.parent.left  = replacement;
            else
                p.parent.right = replacement;

           
            p.left = p.right = p.parent = null;

            
            if (p.color == BLACK)
                fixAfterDeletion(replacement);
        } else if (p.parent == null) { 
            root = null;
        } else { 
            if (p.color == BLACK)
                fixAfterDeletion(p);

            if (p.parent != null) {
                if (p == p.parent.left)
                    p.parent.left = null;
                else if (p == p.parent.right)
                    p.parent.right = null;
                p.parent = null;
            }
        }
    }

    /* This method is written to obtain node with the required id by calling getEntry method*/
	public RedBlackTree get(int key) {
        RedBlackTree p = getEntry(key);
        return (p==null ? null : p);
    }
	
	
	private RedBlackTree getEntry(int key) {
        RedBlackTree p = root;
	    int k = key;
        while (p != null) {
            int cmp = compare(k, p.id);
            if (cmp == 0)
                return p;
            else if (cmp < 0)
                p = p.left;
            else
                p = p.right;
        }
        return null;
    }
	/* In this method we put key and value to the nodes appropriately, checking for valid conditions, incrementing size accordingly
	* comparing for the setValues, and rebalancing the tree*/

	public int put(int key, int value) {
        RedBlackTree t = root;

        if(value == 0){
        	return 0;
        }
        
        if (t == null) {
            incrementSize();
            root = new RedBlackTree(key, value, null);
            return 0;
       }

        while (true) {
            int cmp = compare(key, t.id);
            if (cmp == 0) {
                return t.setValue(value);
            } else if (cmp < 0) {
                if (t.left != null) {
                    t = t.left;
                } else {
                    incrementSize();
                    t.left = new RedBlackTree(key, value, t);
                    fixAfterInsertion(t.left);
                    return 0;
                }
            } else {
                if (t.right != null) {
                    t = t.right;
                } else {
                    incrementSize();
                    t.right = new RedBlackTree(key, value, t);
                    fixAfterInsertion(t.right);
                    return 0;
                }
            }
        }
    }
	/*This method is to increment the size of the tree to add new node and put value to the newly added node*/
	private void incrementSize()   
	{ 
		modCount++; 
		size++; 
	}
    /*This method is to decrement the size of the tree after deleting node*/
	private void decrementSize()   
	{ 
		modCount++; 
		size--; 
	}
	/*This function compared for two values and returns comparison result as an int*/
	private int compare(int k1, int k2) {
		
		int result = 0 ;
		
		if(k1 > k2){
			result = 1;
		}else if(k1 < k2){
			result = -1;
		}else if(k1 == k2){
			result = 0;
		}
		
        return result;
    }
	/* this method is to rebalance tree after deleting a node, and recoloring is done
	after checking for necessary base conditions, checking for the color of the current node,
	parent node, sibling node and changing it if needed and rotating the tree to balance its
	height and make sure that ensures RedBlackTree properties are not violated*/

	private void fixAfterDeletion(RedBlackTree x) {
        while (x != root && colorOf(x) == BLACK) {
            if (x == leftOf(parentOf(x))) {
                RedBlackTree sib = rightOf(parentOf(x));

                if (colorOf(sib) == RED) {
                    setColor(sib, BLACK);
                    setColor(parentOf(x), RED);
                    rotateLeft(parentOf(x));
                    sib = rightOf(parentOf(x));
                }

                if (colorOf(leftOf(sib))  == BLACK &&
                    colorOf(rightOf(sib)) == BLACK) {
                    setColor(sib,  RED);
                    x = parentOf(x);
                } else {
                    if (colorOf(rightOf(sib)) == BLACK) {
                        setColor(leftOf(sib), BLACK);
                        setColor(sib, RED);
                        rotateRight(sib);
                        sib = rightOf(parentOf(x));
                    }
                    setColor(sib, colorOf(parentOf(x)));
                    setColor(parentOf(x), BLACK);
                    setColor(rightOf(sib), BLACK);
                    rotateLeft(parentOf(x));
                    x = root;
                }
            } else { 
                RedBlackTree sib = leftOf(parentOf(x));

                if (colorOf(sib) == RED) {
                    setColor(sib, BLACK);
                    setColor(parentOf(x), RED);
                    rotateRight(parentOf(x));
                    sib = leftOf(parentOf(x));
                }

                if (colorOf(rightOf(sib)) == BLACK &&
                    colorOf(leftOf(sib)) == BLACK) {
                    setColor(sib,  RED);
                    x = parentOf(x);
                } else {
                    if (colorOf(leftOf(sib)) == BLACK) {
                        setColor(rightOf(sib), BLACK);
                        setColor(sib, RED);
                        rotateLeft(sib);
                        sib = leftOf(parentOf(x));
                    }
                    setColor(sib, colorOf(parentOf(x)));
                    setColor(parentOf(x), BLACK);
                    setColor(leftOf(sib), BLACK);
                    rotateRight(parentOf(x));
                    x = root;
                }
            }
        }

        setColor(x, BLACK);
    }
	/*this is to rebalance the tree with color fixing when a node is inserted,
	by checking for base conditions and also color of the node, its parent, its
	sibling by using function colorOf, parentOf, leftOf, rightOf. If the color
	needs to be changed setColor is used to recolor the node*/

	private void fixAfterInsertion(RedBlackTree x) {
        x.color = RED;

        while (x != null && x != root && x.parent.color == RED) {
            if (parentOf(x) == leftOf(parentOf(parentOf(x)))) {
                RedBlackTree y = rightOf(parentOf(parentOf(x)));
                if (colorOf(y) == RED) {
                    setColor(parentOf(x), BLACK);
                    setColor(y, BLACK);
                    setColor(parentOf(parentOf(x)), RED);
                    x = parentOf(parentOf(x));
                } else {
                    if (x == rightOf(parentOf(x))) {
                        x = parentOf(x);
                        rotateLeft(x);
                    }
                    setColor(parentOf(x), BLACK);
                    setColor(parentOf(parentOf(x)), RED);
                    if (parentOf(parentOf(x)) != null)
                        rotateRight(parentOf(parentOf(x)));
                }
            } else {
                RedBlackTree y = leftOf(parentOf(parentOf(x)));
                if (colorOf(y) == RED) {
                    setColor(parentOf(x), BLACK);
                    setColor(y, BLACK);
                    setColor(parentOf(parentOf(x)), RED);
                    x = parentOf(parentOf(x));
                } else {
                    if (x == leftOf(parentOf(x))) {
                        x = parentOf(x);
                        rotateRight(x);
                    }
                    setColor(parentOf(x),  BLACK);
                    setColor(parentOf(parentOf(x)), RED);
                    if (parentOf(parentOf(x)) != null)
                        rotateLeft(parentOf(parentOf(x)));
                }
            }
        }
        root.color = BLACK;
    }
	
	private boolean colorOf(RedBlackTree p) {
        return (p == null ? BLACK : p.color);
    }

    private RedBlackTree parentOf(RedBlackTree p) {
        return (p == null ? null: p.parent);
    }

    private void setColor(RedBlackTree p, boolean c) {
        if (p != null)
	    p.color = c;
    }

    private RedBlackTree leftOf(RedBlackTree p) {
        return (p == null) ? null: p.left;
    }

    private RedBlackTree rightOf(RedBlackTree p) {
        return (p == null) ? null: p.right;
    }

    /*rotateLeft and rotateRight methods are used in balancing rb trees by rotating the tree to
    maintain the height of the tree which should have same number of black nodes in everypath or both subtrees of root */
    private void rotateLeft(RedBlackTree p) {
        RedBlackTree r = p.right;
        p.right = r.left;
        if (r.left != null)
            r.left.parent = p;
        r.parent = p.parent;
        if (p.parent == null)
            root = r;
        else if (p.parent.left == p)
            p.parent.left = r;
        else
            p.parent.right = r;
        r.left = p;
        p.parent = r;
    }

    
    private void rotateRight(RedBlackTree p) {
        RedBlackTree l = p.left;
        p.left = l.right;
        if (l.right != null) l.right.parent = p;
        l.parent = p.parent;
        if (p.parent == null)
            root = l;
        else if (p.parent.right == p)
            p.parent.right = l;
        else p.parent.left = l;
        l.right = p;
        p.parent = l;
    }
	
}
