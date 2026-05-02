public interface TreeInterface<T> {
    /** @return The data stored in the root node. */
    public T getRootData();
    
    /** @return The number of levels in the tree. */
    public int getHeight();

    /** @return The total number of nodes in the tree. */
    public int getNumberOfNodes();

    /** @return True if the tree contains no nodes. */
    public boolean isEmpty();

    /** Removes all nodes from the tree. */
    public void clear();
}
