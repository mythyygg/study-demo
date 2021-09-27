import java.util.Random;

class QuickSortTop {
    private static Random random = new Random();
    private static void swap(int[] nums, int i, int j){
        int tmp = nums[i];
        nums[i] =nums[j];
        nums[j] = tmp;
    }

    public static int findKthLargest(int[] a, int k) {
        if(a == null || a.length == 0) return 0;
        int pos = -1;
        int start = 0;
        int end = a.length-1;
        k = a.length - k + 1;
        while(pos != k-1) {
            pos = partition(a,start, end);
            if(pos < k-1) {
                start = pos+1;
            } else if(pos > k-1){
                end = pos -1;
            } else {
                break;
            }
        }
        return a[pos];
    }

    public static int partition(int[] nums, int left, int right) {
        int randomIndex = left + random.nextInt(right - left+1);
        swap(nums, left, randomIndex);
        int pivot = nums[left];
        int lt = left + 1;
        int rt  = right;
        while (true) {
            // 循环不变量：
            // all in [left + 1, lt) <= pivot
            // all in (gt, right] >= pivot
            while (lt <= rt && nums[lt] < pivot) {
                lt++;
            }
            while (lt <= rt && nums[rt] > pivot) {
                rt--;
            }

            //说明在区间内相遇
            if (lt > rt) {
                break;
            }

            //lt <= rt, 遇到一个等于pivot
            swap(nums, lt, rt);
            lt++;
            rt--;
        }
        swap(nums, left, rt);
        return rt;
    }

    public static void main(String[] args) {
        int[] a = new int[]{1,4,9,9,9,8,7};
        System.out.println(findKthLargest(a, 1));
        System.out.println(findKthLargest(a, 2));
        System.out.println(findKthLargest(a, 3));
        System.out.println(findKthLargest(a, 4));
    }
}
