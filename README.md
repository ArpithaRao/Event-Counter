# Event-Counter
Event Counter using Red-Black Trees in Java.

Compile:
Code is in Java and jdk 1.8.0_66 is the compiler. IDE used is IntelliJ.
makefile: To compile.

Program Structure:

Initialization and building of RedBlackTree:
Input is read from command line, in main method in class bbst.

RedBlackTree is initialized in linear time by taking first argument of the test file as length of the input and
recursively building tree with key value pairs, in Counter class,
init(BufferedReader fileReader, int numPairs) function. RedBlackTree init(int start, int end, BufferedReader
fileReader), is called recursively builds right and left subtree at root which is the middle node. Now we have
obtained a balanced binary search tree.

void colorRedAtHeight(RedBlackTree node, int height,int maxHeight) function recursively colors every node
at maxHeight, red and rest of the nodes, black.
minVal(RedBlackTree node) obtains the minimum most value of the RedBlackTree.
void increase(int theID, int m) Increases the count of the event theID by m. If theID is not present, inserts
theID. Prints the count of theID after the addition. If Node is already present its count is increased by ‘m’. If the
Node is not present int put(int key, int value) function is called which inserts a new node in the bbst, here void
incrementSize() is called to increase size of tree to add a new node, int compare(int k1, int k2) is called to
check for appropriate insertion. And void fixAfterInsertion(RedBlackTree x) is called to fix violations of the
RedBlackTree properties, where colorOf(node) is called to get the color of a node, parentOf(node) is called to
get the parent of a node, leftOf(node) is called to get the left child of a node, rightOf(node) is called to get the
right of a node and setColor is used to assign a node a color, rotateLeft and rotateRight are called to black
height of the tree in every path and thus ensure all have same black height.

void reduce(int theID, int m) If Node is present in the tree, its count is reduced by ‘m’.
If the count becomes 0 or lesser, int remove(int key)function is called which removes the node from the bbst.
Here void deleteEntry(RedBlackTree p) is called to actually delete a node from the RedBlackTree and void
fixAfterDeletion(RedBlackTree x) is called in delete method to self balance the RedBlackTree and recolour it,
which ensures RedBlackTree properties are not violated. Here decrementSize is called to decrease the size of
the tree once a node gets deleted from the tree. Also successor of a node is obtained by calling
successorforNode(node) function. RedBlackTree get(int key) is called to get the node of the id if exists which
internally calls RedBlackTree getEntry(int key) which gives the particular node.

int Count(int key) If Node is not present in the tree, 0 is printed else count is printed to the console.
void inRange(int ID1, int ID2): This method checks for required base case and conditions and calls inRangeUtil
function.

int inRangeUtil(RedBlackTree t, int ID1,int ID2) This method traverses left and right subtree recursively, if
node ‘t’ is between ID1 and ID2(inclusive) and stores sum in variable count, and returns the total count to
inRange method.

void next(int theId) This method iteratively traverses till node id is greater than theId and node id is less than
next and this node isSet and is printed as next node id and its key to the console. For boundary condition if
next node does not exist 0 0 is printed.

void previous(int theId) This method iteratively traverses till node id is less than theId and node id is greater
than previous and this node isSet and is printed as previous node id and its key to the console. For boundary
condition if next node does not exist 0 0 is printed.

Initialization time for various test files is as given below:
test_100.txt 3ms
test_1000000.txt 543ms
test_10000000.txt 10 s
test_100000000.txt 28 s
