package hw6;

import hw6.bst.BinarySearchTreeMap;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class BinarySearchTreeMapTest extends OrderedMapTest {

  private List<String> inorder;

  private void setUpTree() {
    inorder = new ArrayList<String>(Arrays.asList(
        "D", "A", "H", "B", "C", "J", "F", "K", "E", "I", "G"));

    for (String key : inorder) {
      map.insert(key, null);
    }

    /*
      The tree:
                D
           /         \
         A            H
         \          /  \
          B       F      J
           \    /  \   /  \
            C  E   G  I   K

      All the tests below are tied to this arrangement!
     */

    Collections.sort(inorder);
  }

  @Override
  protected Map<String, String> createMap() {
    return new BinarySearchTreeMap<>();
  }

  @Test
  public void removeLeaf() {
    setUpTree();
    map.remove("C");
    inorder.remove("C");

    assertTrue(orderIsPreserved());
  }

  private boolean orderIsPreserved() {
    int index = 0;
    for (String key : map) {
      if (!inorder.get(index++).equals(key)) {
        return false;
      }
    }
    return true;
  }

  @Test
  public void removeNodeWithOneChild() {
    setUpTree();
    map.remove("A");
    inorder.remove("A");
    assertTrue(orderIsPreserved());
  }

  @Test
  public void removeNodeWithTwoChild() {
    setUpTree();
    map.remove("H");
    inorder.remove("H");

    assertTrue(orderIsPreserved());
  }

  @Test
  public void removeMultipleNodes() {
    setUpTree();
    map.remove("G");
    inorder.remove("G");

    map.remove("I");
    inorder.remove("I");

    map.remove("H");
    inorder.remove("H");

    map.remove("D");
    inorder.remove("D");

    assertTrue(orderIsPreserved());
  }
}