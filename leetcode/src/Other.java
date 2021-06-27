import java.util.ArrayList;
import java.util.List;

public class Other {
    public static void main(String[] args) {
        System.out.println(mySqrt(9.00001));
        int[] a = {2,3,4,5,6};
        List<List<Integer>> result = new ArrayList<>();
        List<Integer> path = new ArrayList<>(10);
        for (int i = 0; i < a.length; i++) {
            helper(result, path, a, 0,i+1);
        }
    }

    public static double mySqrt(double x){
        double epsilon = 1e-7;
        double left = 0 , right = x;
        if(x == 0 || x == 1){
            return x;
        }
        while(left < right){
            double mid = left + (right - left) / 2;
            double tmp = mid * mid*mid;
            if(Math.abs(tmp - x) < epsilon){
                return mid;
            }else if(tmp < x){
                left = mid;
            }else{
                right = mid;
            }
        }
        return left;
    }

    public static void helper(List<List<Integer>> rst, List<Integer> list, int[] nums, int begin, int count) {
        if (list.size() == count) {
            rst.add(new ArrayList<>(list));
            return;
        }
        for (int i = begin; i < nums.length; i++,begin++) {
            if (!list.contains(nums[i]) ) {
                list.add(nums[i]);
                helper(rst, list, nums, begin + 1, count);
                list.remove(list.size() - 1);
            }
        }
    }

}
