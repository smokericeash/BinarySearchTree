import java.util.*;

public class BinarySearchTree<T extends Comparable<? super T>> implements SearchTreeInterface<T> {
    private BinaryNode<T> root;

    public BinaryNode<T> getRootNode(){
        return root;
    }

    public void setRootNode(BinaryNode<T> rootNode){
        this.root = rootNode;
    }

    private class BinaryNode<T> {
        private T data;
        private BinaryNode<T> leftChild;
        private BinaryNode<T> rightChild;

        //Constructor with only the root node
        private BinaryNode(T data){
            this(data, null, null);
        }

        //Constructor that sets the data, right child, and left child of the tree
        private BinaryNode(T data, BinaryNode<T> left, BinaryNode<T> right){
            this.data = data;
            leftChild = left;
            rightChild = right;
        }

        public BinaryNode<T> getRightChild(){
            return rightChild;
        } //end getRightChild

        public BinaryNode<T> getLeftChild(){
            return leftChild;
        } //end getLeftChild

        public void setRightChild(BinaryNode<T> rightChild){
            this.rightChild = rightChild;
        } //end setRightChild

        public void setLeftChild(BinaryNode<T> leftChild){
            this.leftChild = leftChild;
        } //end setLeftChild

        public boolean hasLeftChild(){
            return leftChild!=null;
        } //end hasLeftChild

        public boolean hasRightChild(){
            return rightChild!=null;
        } //end hasRightChild

        public T getData(){
            return data;
        } //end getData

        public void setData(T data){
            this.data = data;
        } //end setData
        

    }

        
    public T add(T newEntry){
        T result = null;
        
        if(isEmpty()){
            setRootNode(new BinaryNode<T>(newEntry));
        }
        else{
            result = addEntry(getRootNode(), newEntry);
        }
        return result;
    } //end add

    //using add method in recursion
    private T addEntry(BinaryNode<T> rootNode, T newEntry){
        assert rootNode!=null;
        T result = null;
        int comparison = newEntry.compareTo(rootNode.getData());

        //if duplicate is found, return existing entry
        if(comparison==0){
            return rootNode.getData();
        }

        //if newEntry is less than rootNode's data
        else if(comparison < 0){
            if(rootNode.hasLeftChild()){
                result = addEntry(rootNode.getLeftChild(), newEntry);
            }
            //setting in a leaf node
            else{
                rootNode.setLeftChild(new BinaryNode<T>(newEntry));
            }
        }

        //if newEntry is greater than rootNode's data
        else if(comparison > 0 ){
            if(rootNode.hasRightChild()){
                result = addEntry(rootNode.getRightChild(), newEntry);
            }
            //setting in a leaf node
            else{
                rootNode.setRightChild(new BinaryNode<T>(newEntry));
            }
        }
        return result;
    } //end addEntry

    //method done in iteration
    private BinaryNode<T> removeEntry(BinaryNode<T> rootNode, T entry, ReturnObject oldEntry){
        if(rootNode!=null){ //checking for empty node
            BinaryNode<T> currentNode = rootNode;
            BinaryNode<T> parentNode = null;
            boolean found = false;

            //finding the node and the parent of it
            while(!found && currentNode != null){
                T rootData = currentNode.getData();
                int comparison = entry.compareTo(rootData);

                if(comparison==0){
                    found = true;
                }
                else{
                    parentNode = currentNode;
                    //if the entry is less than the root's data
                    if(comparison < 0){
                        currentNode = currentNode.getLeftChild();
                    }
                    else{  //if the entry is greater than root's data
                        currentNode = currentNode.getRightChild();
                    }
                }
            } //end while

            //if it was found, replace and remove with the left subtree's right most node
            if(found){
                oldEntry.set(currentNode.getData());
                
                //if node has left subtree
                if(currentNode.hasLeftChild()){
                    //find right most node of this tree
                    BinaryNode<T> leftSubtree = currentNode.getLeftChild();
                    BinaryNode<T> predecessor = findLargest(leftSubtree);

                    //finding the parent and keeping it
                    BinaryNode<T> parentOfPredecessor = currentNode;
                    BinaryNode<T> scanNode = leftSubtree;

                    while(scanNode!= predecessor){
                        parentOfPredecessor = scanNode;
                        scanNode = scanNode.getRightChild();
                    }

                    //replacing the current node's data with predecessor's data
                    currentNode.setData(predecessor.getData());

                    //remove predecessor
                    if(parentOfPredecessor == currentNode){
                        currentNode.setLeftChild(predecessor.getLeftChild());
                    }
                    else{
                        parentOfPredecessor.setRightChild(predecessor.getLeftChild());
                    }
                }
                else{
                    //no left child(either right child or leaf)
                    BinaryNode<T> rightChild = currentNode.getRightChild();

                    if(parentNode==null){
                        rootNode = rightChild;
                    }
                    else if(parentNode.getLeftChild() == currentNode){
                        parentNode.setLeftChild(rightChild);
                    }
                    //in the case where parentNode's right child is the target and if it is, will set it to null/right child
                    else{
                        parentNode.setRightChild(rightChild);
                    }
                }
            }
        }
        return rootNode;
    } //end removeEntry


    //helper method for finding right most node of a tree
    private BinaryNode<T> findLargest(BinaryNode<T> rootNode){
        BinaryNode<T> currentNode = rootNode;
        while(currentNode.hasRightChild()){
            currentNode = currentNode.getRightChild();
        }
        return currentNode;
    } //end findLargest

    //returnObject class
    private class ReturnObject{
        private T item;

        private ReturnObject(T entry){
            item = entry;
        }

        public T getData(){
            return item;
        }

        public void set(T entry){
            item = entry;
        }
    } //end ReturnObject


    /** @return True if anEntry is found in the tree. */
    public boolean contains(T anEntry){
        return findEntry(getRootNode(), anEntry) !=null;
    } //end contains

    /** @return The object in the tree that matches the entry, or null if not found. */
    public T getEntry(T entry){
        return findEntry(getRootNode(), entry);
    } //end getEntry

    /** @return entry given from a node and it's target entry */
    private T findEntry(BinaryNode<T> rootNode, T entry){
        T result = null;
        if(rootNode!=null){
            T rootEntry = rootNode.getData();
            int compare = entry.compareTo(rootEntry);
            if(compare == 0){
                result = rootEntry;
            }
            else if(compare < 0){
                result = findEntry(rootNode.getLeftChild(), entry);
            }
            else if(compare > 0){
                result = findEntry(rootNode.getRightChild(), entry);
            }
        }
        return result;
    } //end findEntry


    /** 
     * Removes an entry from the tree.
     * Note: Project 4 requires this to be iteration.
     * @return The removed entry, or null if not found. 
     */
    public T remove(T entry){
        //creating placeholder for data we find
        ReturnObject oldEntry = new ReturnObject(null);

        //calling iterative helper and updating root
        BinaryNode<T> newRoot = removeEntry(getRootNode(), entry, oldEntry);
        setRootNode(newRoot);

        //returning the data removed
        return oldEntry.getData();
    } //end remove

    /** @return An iterator that traverses the tree in sorted (ascending) order. */
    public Iterator<T> getInorderIterator(){
        return new InorderIterator();
    } //end getInorderIterator

    /** @return An iterator that visits the root last (Left-Right-Root). */
    public Iterator<T> getPostorderIterator(){
        return new PostorderIterator();
    } //end getPostorderIterator

    /** @return An iterator that visits the root first (Root-Left-Right). */
    public Iterator<T> getPreorderIterator(){
        return new PreorderIterator();
    } //end getPreorderIterator

    /** @return The value that appears immediately before this entry  */
    public T getPredecessor(T entry){
        Iterator<T> iter = getInorderIterator();
        T prev = null;
        while(iter.hasNext()){
            T current = iter.next();
            if(current.compareTo(entry)==0){
                return prev; //returns item visited before the match
            }
            prev = current;
        }
        return null; //entry not found or smallest item with no predecessor
    } //end getPredecessor

    /** @return The value that appears immediately after this entry  */
    public T getSuccessor(T entry){
        Iterator<T> iter = getInorderIterator();
        
        while(iter.hasNext()){
            T current = iter.next();
            if(current.compareTo(entry)==0){
                if(iter.hasNext()){
                    return iter.next();
                }
                break;
            }
        }
        return null; //if not found or it's largest item with no sucessor
    } //end getSuccessor

    /** @return The data stored in the root node. */
    public T getRootData(){
        return root.data;
    } //end getRootData
    
    /** @return The number of levels in the tree. */
    public int getHeight(){
        return getHeight(root);
    } //end getHeight

    /** @return The number of levels in the tree at a specific node using recursion. */
    private int getHeight(BinaryNode<T> node){
        if(node==null){
            return 0;
        }
        else{ //return the height of whichever subtree has the greatest height
            return 1 + Math.max(getHeight(node.getLeftChild()), getHeight(node.getRightChild()));
        }
    } //end getHeight

    /** @return The total number of nodes in the tree. */
    public int getNumberOfNodes(){
        return getNumberOfNodes(root);
    } //end getNumberOfNodes

    /** @return The total number of nodes in a subtree/tree from a specific node. */
    public int getNumberOfNodes(BinaryNode<T> node){
        if(node==null){
            return 0;
        }
        else{
            return 1 + getNumberOfNodes(node.getLeftChild()) + getNumberOfNodes(node.getRightChild());
        }
    } //end getNumberOfnodes

    /** @return True if the tree contains no nodes. */
    public boolean isEmpty(){
        return root==null;
    } //end isEmpty

    /** Removes all nodes from the tree. */
    public void clear(){
        root = null;
    } //end clear

    private class InorderIterator implements Iterator<T>{
        private Stack<BinaryNode<T>> nodeStack;
        private BinaryNode<T> currentNode;

        public InorderIterator(){
            nodeStack = new Stack<>();
            currentNode = root;
        }

        @Override
        public boolean hasNext(){
            return !nodeStack.isEmpty() || currentNode!=null;
        }

        @Override
        public T next(){
            //go as far left from the currentNode
            while(currentNode!=null){
                nodeStack.push(currentNode);
                currentNode = currentNode.getLeftChild();
            }

            //next node in order is at the top of the stack
            if(!nodeStack.isEmpty()){
                BinaryNode<T> nextNode = nodeStack.pop();
                T result = (T) nextNode.getData();

                //move right child for next call
                currentNode = nextNode.getRightChild();
                return result;
            }
            else{
                throw new NoSuchElementException();
            }
        }

    } //end InorderIterator class

    private class PreorderIterator implements Iterator<T>{
        private Stack<BinaryNode<T>> nodeStack;

        public PreorderIterator(){
            nodeStack = new Stack<>();
            if(root!=null){
                nodeStack.push(root);
            }
        }

        @Override
        public boolean hasNext(){
            return !nodeStack.isEmpty();
        }

        @Override
        public T next(){
            //go as far left from the currentNode
            if(!nodeStack.isEmpty()){
                BinaryNode<T> nextNode = nodeStack.pop();
                T result = (T) nextNode.getData();

                //push right then left, since left will be on top and popped first
                if(nextNode.hasRightChild()){
                    nodeStack.push(nextNode.getRightChild());
                }
                if(nextNode.hasLeftChild()){
                    nodeStack.push(nextNode.getLeftChild());
                }

                return result;
            }
            else{
                throw new NoSuchElementException();
            }
        }

    } //end PreorderIterator class

    private class PostorderIterator implements Iterator<T>{
        private Stack<BinaryNode<T>> nodeStack;
        private BinaryNode<T> currentNode;

        public PostorderIterator(){
            nodeStack = new Stack<>();
            currentNode = root;
        }

        @Override
        public boolean hasNext(){
            return !nodeStack.isEmpty() || currentNode!=null;
        }

        @Override
        public T next(){
            //go as far left from the currentNode
            while(currentNode!=null){
                nodeStack.push(currentNode);
                if(currentNode.hasLeftChild()){
                    currentNode = currentNode.getLeftChild();
                }
                else{
                    currentNode = currentNode.getRightChild();
                }
            }

            if(nodeStack.isEmpty()){
                throw new NoSuchElementException();
            }

            BinaryNode<T> nextNode = nodeStack.pop();

            //peek at parent to see if we need to go to the right child
            if(!nodeStack.isEmpty() && nodeStack.peek().getLeftChild() == nextNode){
                currentNode = nodeStack.peek().getRightChild();
            }
            else{
                currentNode = null; //forces next call to pop from stack
            }

            return nextNode.getData();
        }

    } //end PostorderIterator class


}
