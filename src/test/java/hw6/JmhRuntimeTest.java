package hw6;

import hw6.bst.AvlTreeMap;
import hw6.bst.BinarySearchTreeMap;
import hw6.bst.TreapMap;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Level;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Warmup;
import org.openjdk.jmh.infra.Blackhole;

import java.io.FileNotFoundException;
import java.util.concurrent.TimeUnit;

@State(Scope.Benchmark)
public class JmhRuntimeTest {

  @Setup(Level.Invocation)
  public void setUp() {
    Experiment.VERBOSE = false;
  }

  @Benchmark
  @Fork(value = 1, warmups = 1)
  @Warmup(iterations = 1)
  @Measurement(iterations = 2)
  @OutputTimeUnit(TimeUnit.MILLISECONDS)
  @BenchmarkMode(Mode.AverageTime)
  public void arrayMap(Blackhole blackhole) {
    Map<String, Integer> map = new ArrayMap<>();
    try {
      Experiment.run(map);
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
    blackhole.consume(map);
  }

  @Benchmark
  @Fork(value = 1, warmups = 1)
  @Warmup(iterations = 1)
  @Measurement(iterations = 2)
  @OutputTimeUnit(TimeUnit.MILLISECONDS)
  @BenchmarkMode(Mode.AverageTime)
  public void bstMap(Blackhole blackhole) {
    Map<String, Integer> map = new BinarySearchTreeMap<>();
    try {
      Experiment.run(map);
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
    blackhole.consume(map);
  }

  @Benchmark
  @Fork(value = 1, warmups = 1)
  @Warmup(iterations = 1)
  @Measurement(iterations = 2)
  @OutputTimeUnit(TimeUnit.MILLISECONDS)
  @BenchmarkMode(Mode.AverageTime)
  public void avlTreeMap(Blackhole blackhole) {
    Map<String, Integer> map = new AvlTreeMap<>();
    try {
      Experiment.run(map);
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
    blackhole.consume(map);
  }

  @Benchmark
  @Fork(value = 1, warmups = 1)
  @Warmup(iterations = 1)
  @Measurement(iterations = 2)
  @OutputTimeUnit(TimeUnit.MILLISECONDS)
  @BenchmarkMode(Mode.AverageTime)
  public void treapMap(Blackhole blackhole) {
    Map<String, Integer> map = new TreapMap<>();
    try {
      Experiment.run(map);
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
    blackhole.consume(map);
  }
}
