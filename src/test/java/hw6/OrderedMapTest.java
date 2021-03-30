package hw6;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * In addition to those tests in MapTest, we need to test the "order"
 * in OrderedMap.
 */
@SuppressWarnings("All")
public abstract class OrderedMapTest extends MapTest {

  @Test
  public void insertRandomIterateInOrder() {
    List<String> data = new ArrayList<>(Arrays.asList("A", "B", "C", "D"));

    Collections.shuffle(data);
    for (String key : data) {
      map.insert(key, "value not important");
    }

    Collections.sort(data);
    int index = 0;
    for (String key : map) {
      assertEquals(data.get(index++), key);
    }
  }
}
