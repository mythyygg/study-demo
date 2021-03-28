import java.util.*;

public class Arr {

    public static void main(String[] args) {
        int[] a = new int[]{2,7,11,15};
        System.out.println(twoSum2(a, 9));
    }

    public static int[] twoSum(int[] nums, int target){
        int[] res = new int[2];
        if (nums.length < 2) return res;
        Map<Integer, Integer> map = new HashMap<>(nums.length);
        for (int i = 0; i < nums.length; i++) {
            map.put(nums[i], i);
        }
        for(int i = 0; i < nums.length; i++){
            int gap = target - nums[i];
            if(map.containsKey(gap) && map.get(gap) > i) {
                res[0] = i;
                res[1] = map.get(gap);
                break;
            }
         }
        return res;
    }

    //有序
    public static int[] twoSum2(int[] nums, int target){

        int[] res = new int[2];
        if (nums.length < 2) return res;
        Arrays.sort(nums);
        int left = 0;
        int right = nums.length-1;
        while(left < right) {
            int l = nums[left];
            int r = nums[right];
            if (l + r == target) {
                res[0] = left;
                res[1] = right;
                break;
            }  else if (l+r < target){
                left++;
            } else {
                right--;
            }
        }
        return res;
    }

    public static List<List<Integer>> threeSum(int[] nums, int target) {
        List<List<Integer>> res = new ArrayList<>();
        if (nums.length < 3) return res;
        Arrays.sort(nums);
        for (int i = 0; i < nums.length - 2; i++) {
            //提前退出
            if (nums[i] >= target) break;
            //跳过重复的数；
            if (i > 0 && nums[i] == nums[i-1]) continue;
            int j = i+1;
            int k = nums.length - 1;
            while(j < k) {
                int a = nums[i];
                int b = nums[j];
                int c = nums[k];
                if(a+b+c < target) {
                    j++;
                    while(j<k && nums[j-1] == nums[j]) ++j;
                }else if(a+b+c > target) {
                    k--;
                    while(j<k && nums[k+1] == nums[k]) --k;
                } else {
                    res.add(Arrays.asList(a,b,c));
                    j++;
                    k--;
                    while(j < k && nums[j] == nums[j-1]) ++j;
                }
            }
        }
        return res;

    }
}
