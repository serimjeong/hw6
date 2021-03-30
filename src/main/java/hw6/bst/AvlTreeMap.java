package hw6.bst;

import hw6.OrderedMap;
import java.util.Iterator;
import java.util.Stack;

/**
 * Map implemented as an AVL Tree.
 *
 * @param <K> Type for keys.
 * @param <V> Type for values.
 */
public class AvlTreeMap<K extends Comparable<K>, V> implements OrderedMap<K, V> {

  /*** Do not change variable name of 'root'. ***/
  private Node<K, V> root;
  private int size;

  private int getHeight(Node<K,V> node) {
    if (node == null) {
      return -1;
    }
    return node.height;

  }

  private int getBalance(Node<K, V> n) {
    if (n == null) {
      return 0;
    }
    return getHeight(n.left) - getHeight(n.right);

  }

  private Node<K, V> rightRotation(Node<K, V> node) {
    Node<K, V> newParentNode = node.left;
    Node<K, V> nullNode = node.right;

    newParentNode.right = node;
    node.left = nullNode;

    node.height = Math.max(getHeight(node.left), getHeight(node.right));
    newParentNode.height = Math.max(getHeight(newParentNode.left), getHeight(newParentNode.right));

    return newParentNode;
  }

  private Node<K, V> leftRotation(Node<K, V> node) {
    Node<K, V> newParentNode = node.right;
    Node<K, V> nullNode = node.left;

    newParentNode.left = node;
    node.right = nullNode;

    node.height = Math.max(getHeight(node.left), getHeight(node.right));
    newParentNode.height = Math.max(getHeight(newParentNode.left), getHeight(newParentNode.right));

    return newParentNode;
  }

  private Node<K, V> correctImbalance(Node<K, V> node, K key) {
    int balance = getBalance(node);

    if (balance > 1 && key.compareTo(node.key) < 0) {
      return rightRotation(node);
    }

    if (balance < -1 && key.compareTo(node.key) > 0) {
      return leftRotation(node);
    }

    if (balance > 1 && key.compareTo(node.key) > 0) {
      node.left = leftRotation(node.left);
      return rightRotation(node);
    }

    if (balance < -1 && key.compareTo(node.key) < 0) {
      node.right = rightRotation(node.right);
      return leftRotation(node);
    }

    return node;
  }

  private Node<K, V> insert(Node<K, V> n, K k, V v) {
    if (n == null) {
      return new Node<>(k, v);
    }

    int cmp = k.compareTo(n.key);
    if (cmp < 0) {
      n.left = insert(n.left, k, v);
    } else if (cmp > 0) {
      n.right = insert(n.right, k, v);
    } else {
      throw new IllegalArgumentException("duplicate key " + k);
    }

    n.height = Math.max(getHeight(n.left), getHeight(n.right)) + 1;

    return correctImbalance(n, k);
  }

  @Override
  public void insert(K k, V v) throws IllegalArgumentException {
    if (k == null) {
      throw new IllegalArgumentException("cannot handle null key");
    }
    root = insert(root, k, v);
    size++;
  }

  @Override
  public V remove(K k) throws IllegalArgumentException {
    Node<K, V> node = findForSure(k);
    root = remove(root, node);
    size--;
    return node.value;
  }

  private Node<K, V> remove(Node<K, V> subtreeRoot, Node<K, V> toRemove) {
    int cmp = subtreeRoot.key.compareTo(toRemove.key);
    if (cmp == 0) {
      return remove(subtreeRoot);
    } else if (cmp > 0) {
      subtreeRoot.left = remove(subtreeRoot.left, toRemove);
    } else {
      subtreeRoot.right = remove(subtreeRoot.right, toRemove);
    }

    return subtreeRoot;
  }

  private Node<K, V> correctImbalanceOnDelete(Node<K, V> node) {
    int balance = getBalance(node);

    if (balance > 1) {
      if (getBalance(node.left) < 0) {
        node.left = leftRotation(node.left);
      }
      return rightRotation(node);
    }

    if (balance < -1) {
      if (getBalance(node.right) > 0) {
        node.right = rightRotation(node.right);
      }
      return leftRotation(node);
    }

    return node;
  }

  private Node<K, V> remove(Node<K, V> node) {
    // Easy if the node has 0 or 1 child.
    if (node.right == null) {
      return node.left;
    } else if (node.left == null) {
      return node.right;
    }

    // If it has two children, find the predecessor (max in left subtree),
    Node<K, V> toReplaceWith = max(node.left);
    // then copy its data to the given node (value change),
    node.key = toReplaceWith.key;
    node.value = toReplaceWith.value;
    // then remove the predecessor node (structural change).
    remove(node.left, toReplaceWith);

    node.height = Math.max(getHeight(node.left), getHeight(node.right)) + 1;

    return correctImbalanceOnDelete(node);
  }

  private Node<K, V> max(Node<K, V> node) {
    Node<K, V> curr = node.left;
    while (curr.right != null) {
      curr = curr.right;
    }
    return curr;
  }

  @Override
  public void put(K k, V v) {
    Node<K, V> n = findForSure(k);
    n.value = v;
  }

  // Return node for given key,
  // throw an exception if the key is not in the tree.
  private Node<K, V> findForSure(K k) {
    Node<K, V> n = find(k);
    if (n == null) {
      throw new IllegalArgumentException("cannot find key " + k);
    }
    return n;
  }

  @Override
  public V get(K k) {
    Node<K, V> n = findForSure(k);
    return n.value;
  }

  @Override
  public boolean has(K k) {
    if (k == null) {
      return false;
    }
    return find(k) != null;
  }

  @Override
  public int size() {
    return size;
  }

  // Return node for given key.
  private Node<K, V> find(K k) {
    if (k == null) {
      throw new IllegalArgumentException("cannot handle null key");
    }
    Node<K, V> n = root;
    while (n != null) {
      int cmp = k.compareTo(n.key);
      if (cmp < 0) {
        n = n.left;
      } else if (cmp > 0) {
        n = n.right;
      } else {
        return n;
      }
    }
    return null;
  }

  @Override
  public Iterator<K> iterator() {
    return new InorderIterator();
  }

  private class InorderIterator implements Iterator<K> {
    private final Stack<Node<K, V>> stack;

    InorderIterator() {
      stack = new Stack<>();
      pushLeft(root);
    }

    private void pushLeft(Node<K, V> curr) {
      while (curr != null) {
        stack.push(curr);
        curr = curr.left;
      }
    }

    @Override
    public boolean hasNext() {
      return !stack.isEmpty();
    }

    @Override
    public K next() {
      Node<K, V> top = stack.pop();
      pushLeft(top.right);
      return top.key;
    }
  }
  /*** Do not change this function's name or modify its code. ***/

  @Override
  public String toString() {
    return BinaryTreePrinter.printBinaryTree(root);
  }

  /**
   * Feel free to add whatever you want to the Node class (e.g. new fields).
   * Just avoid changing any existing names, deleting any existing variables,
   * or modifying the overriding methods.
   *
   * <p>Inner node class, each holds a key (which is what we sort the
   * BST by) as well as a value. We don't need a parent pointer as
   * long as we use recursive insert/remove helpers.</p>
   **/
  private static class Node<K, V> implements BinaryTreeNode {
    Node<K, V> left;
    Node<K, V> right;
    K key;
    V value;
    int height;

    // Constructor to make node creation easier to read.
    Node(K k, V v) {
      // left and right default to null
      key = k;
      value = v;
    }

    Node(K k, V v, int h) {
      key = k;
      value = v;
      height = h;
    }


    @Override
    public String toString() {
      return key + ":" + value;
    }

    @Override
    public BinaryTreeNode getLeftChild() {
      return left;
    }

    @Override
    public BinaryTreeNode getRightChild() {
      return right;
    }
  }

}
