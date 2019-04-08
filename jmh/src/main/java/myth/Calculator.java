package myth;

/**
 * @description:
 * @author: yuang gang
 * @create: 2018-11-18 23:17
 **/
public interface Calculator {
    /**
     * calculate sum of an integer array
     * @param numbers
     * @return
     */
    public long sum(int[] numbers);

    /**
     * shutdown pool or reclaim any related resources
     */
    public void shutdown();
}