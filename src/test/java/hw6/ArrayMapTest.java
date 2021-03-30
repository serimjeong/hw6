package hw6;

public class ArrayMapTest extends MapTest {
  @Override
  protected Map<String, String> createMap() {
    return new ArrayMap<>();
  }
}