import java.util.*;

public class Arr {

    public static void main(String[] args) {


        int[] a = new int[]{1,2,3,0,0,0};
        int[] b=  new int[]{2,5,6};
        merge(a,3,b,3);
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


    public static void merge(int[] nums1, int j, int[] nums2, int k) {
        int m = j-1;
        int n = k-1;
        for(int i = j+k-1; i >= 0; i--) {
            if ( m >= 0 && n >= 0) {
                if(nums1[m] > nums2[n]) {
                    nums1[i] = nums1[m--];
                }else {
                    nums1[i] = nums2[n--];
                }
            } else if (m < 0) {
                nums1[i] = nums2[n--];
            } else if (n < 0){
                nums1[i] = nums1[m--];
            }
        }
    }

    public static int[] countN(int[] a) {
        for(int i = 0; i < a.length; i++) {
            while(a[i] > 0 ) {
                int index = a[i];
                if (a[index-1] > 0) {
                    a[i] = a[index-1];
                    a[index-1] = -1;
                } else {
                    a[index-1] -= 1;
                    a[i] = 0;
                }
            }
        }
        return a;

    }
}


