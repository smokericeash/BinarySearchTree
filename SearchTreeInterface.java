import java.util.*;

public interface SearchTreeInterface<T extends Comparable<? super T>> extends TreeInterface<T> {
    /** @return True if anEntry is found in the tree. */
    public boolean contains(T anEntry);

    /** @return The object in the tree that matches the entry, or null if not found. */
    public T getEntry(T entry);

    /** 
     * Adds a new entry to the tree. 
     * Note: Project 4 requires this to be in recursion.
     * @return Null if successful, or the existing entry if it was a duplicate. 
     */
    public T add(T newEntry);

    /** 
     * Removes an entry from the tree.
     * Note: Project 4 requires this to be iteration.
     * @return The removed entry, or null if not found. 
     */
    public T remove(T entry);

    /** @return An iterator that traverses the tree in sorted (ascending) order. */
    public Iterator<T> getInorderIterator();

    /** @return An iterator that visits the root last (Left-Right-Root). */
    public Iterator<T> getPostorderIterator();

    /** @return An iterator that visits the root first (Root-Left-Right). */
    public Iterator<T> getPreorderIterator();

    /** @return The value that appears immediately before this entry  */
    public T getPredecessor(T entry);

    /** @return The value that appears immediately after this entry  */
    public T getSuccessor(T entry);
}
