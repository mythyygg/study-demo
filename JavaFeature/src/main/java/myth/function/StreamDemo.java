package myth.function;

import java.util.Random;
import java.util.function.Supplier;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class StreamDemo {

  public static void main(String[] args) {
    Random seed = new Random();
    Supplier<Integer> random = seed::nextInt;
    Stream.generate(random).limit(20).map(x -> x % 100).forEach(System.out::println);

    IntStream.generate(() -> (int) (System.nanoTime() % 100)).
        limit(10).forEach(System.out::println);
  }
}
