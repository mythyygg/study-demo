import java.util.Arrays;
import java.util.Collections;
import java.util.List;

class Methods {
    /**
     * 排序方法
     *
     * @param array
     * @return
     */
    public int[] sort(int[] array, String updown) {//updown为up时表示升序，为down时表示降序
        Integer[] integers = Arrays.stream(array).boxed().toArray(Integer[]::new);
        if (updown.equals("up")) {
            Arrays.sort(integers);
        } else if (updown.equals("down")) {
            Arrays.sort(integers, Collections.reverseOrder());
        }
        return Arrays.stream(integers).mapToInt(Integer::valueOf).toArray();
    }

    /**
     * 有序数组插入数字依然为有序数组
     *
     * @param nums
     * @param target
     * @return
     */
    public List<Integer> insert(int[] nums, int target) {//nums为有序数组，target为要插入的数字
        return null;
    }
}

