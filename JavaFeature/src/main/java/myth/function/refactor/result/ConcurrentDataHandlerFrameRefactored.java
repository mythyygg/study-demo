package myth.function.refactor.result;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

import myth.function.refactor.CatchUtil;
import myth.function.refactor.ExecutorUtil;
import myth.function.refactor.ForeachUtil;
import myth.function.refactor.StreamUtil;

/**
 * Created by shuqin on 17/6/23.
 */
public class ConcurrentDataHandlerFrameRefactored {

  public static void main(String[] args) {
    List<Integer> allData = getAllData(DataSupplier::getKeys, GetTradeData::getData);

    List<Double> handledData = handleAllData(allData,
        (numbers) -> StreamUtil.map(numbers, (num) -> Math.sqrt(num)));
    consumer(handledData, System.out::println);

    List<Object> objs = StreamUtil.map(DataSupplier.getKeys(), s -> Double.valueOf(s));

    List<Object> handledData2 = Collections.singletonList(
        handleAllData((numbers) -> StreamUtil.map(numbers, (num) -> Math.pow((double) num, 2)))
            .apply(objs));

    consumer(handledData2, System.out::println);

    Function<List<String>, List<Object>> func = (numbers) -> StreamUtil.map(numbers,
        (num) -> Integer.parseInt(num) * 2);
    List<Object> handledData3 = handleAllData(DataSupplier::getKeys).apply(func);
    consumer(handledData3, System.out::println);
    System.out.println("done");
  }

  /**
   * 获取所有业务数据
   * <p>
   * 回调的替换
   */
  public static <T> List<T> getAllData(Supplier<List<String>> getAllKeysFunc,
      Function<List<String>, List<T>> iGetBizDataFunc) {
    return handleAllData(getAllKeysFunc, iGetBizDataFunc);
  }

  public static <T> List<T> getAllData(List<String> allKeys,
      Function<List<String>, List<T>> iGetBizDataFunc) {
    return handleAllData(allKeys, iGetBizDataFunc);
  }

  public static <T, R> List<R> handleAllData(Supplier<List<T>> getAllKeysFunc,
      Function<List<T>, List<R>> handleBizDataFunc) {
    return handleAllData(getAllKeysFunc.get(), handleBizDataFunc);
  }

  /**
   * 传入一个数据处理函数，返回一个可以并发处理数据集的函数, 该函数接受一个指定数据集 Java 模拟柯里化: 函数工厂
   */
  public static <T, R> Function<List<T>, List<R>> handleAllData(
      Function<List<T>, List<R>> handleBizDataFunc) {
    return ts -> handleAllData(ts, handleBizDataFunc);
  }

  /**
   * 传入一个数据提供函数，返回一个可以并发处理获取的数据集的函数, 该函数接受一个数据处理函数 Java 模拟柯里化: 函数工厂
   */
  public static <T, R> Function<Function<List<T>, List<R>>, List<R>> handleAllData(
      Supplier<List<T>> getAllKeysFunc) {
    return handleBizDataFunc -> handleAllData(getAllKeysFunc.get(), handleBizDataFunc);
  }

  public static <T, R> List<R> handleAllData(List<T> allKeys,
      Function<List<T>, List<R>> handleBizDataFunc) {
    return ExecutorUtil.exec(allKeys, handleBizDataFunc);
  }

  public static <T> void consumer(List<T> data, Consumer<T> consumer) {
    data.forEach((t) -> CatchUtil.tryDo(t, consumer));
  }

  public static class DataSupplier {

    public static List<String> getKeys() {
      // foreach code refining
      return ForeachUtil.foreachAddWithReturn(20, (ind -> Arrays.asList(String.valueOf(ind))));
    }
  }

  /**
   * 获取业务数据具体实现
   */
  public static class GetTradeData {

    public static List<Integer> getData(List<String> keys) {
      // maybe xxxService.getData(keys);
      return StreamUtil
          .map(keys, key -> Integer.valueOf(key) % 1000000000); // stream replace foreach
    }

  }

}
