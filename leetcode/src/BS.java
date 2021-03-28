public class BS {
    private static  int[] data = new int[]{1,2,3,4,4,5,6,7,8};
    private static int key = 3;
    public static int b1(int left, int right) {
        int mid = left + (right - left) / 2;
        while (left <= right) {
            if (data[mid] < key) left = mid + 1;
            else if (data[mid] > key) right = mid - 1;
            else return mid;
        }
        return -1;
    }

    //第一次出现的下表
    public static int b2(int left, int right ){
        while (left < right){
            int mid = left + (right-left)/2;
            if(data[mid] < key) left = mid + 1;
            else if(data[mid] > key) right = mid - 1;
            else right = mid;
        }
        if (data[left] == key) return left;
        return -1;
    }

    //最后一次出现的下表
    public static int b3(int[] data ){
        int left = 0;
        int right = data.length-1;
        while (left < right-1){
            int mid = left + (right-left)/2;
            if(data[mid] < key) left = mid + 1;
            else if(data[mid] > key) right = mid - 1;
            else left = mid;
        }
        if (data[right] == key) return right;
        if (data[left] == key) return left;
        return -1;
    }

    //第一次和最后一次位置
    public static int b6(int[] data ){
        int left = 0;
        int right = data.length-1;

        while (left < right){
            int mid = left + (right-left)/2;
            if(data[mid] < key) left = mid + 1;
            else if(data[mid] > key) right = mid-1;
            else left = mid+1;
        }

        if (data[left]  > key) return left;
        return -1;
    }

    //刚好小于key的元素的下标
    public static int b4(int[] data ){
        int left = 0;
        int right = data.length-1;

        while (left <= right){
            int mid = left + (right-left)/2;
            if(data[mid] < key) left = mid + 1;
            else if(data[mid] > key) right = mid - 1;
            else right = mid-1;
        }

        if (data[right] < key) return right;

        return -1;
    }

    //刚好大于key的元素的下标
    public static int b5(int[] data ){
        int left = 0;
        int right = data.length-1;

        while (left < right){
            int mid = left + (right-left)/2;
            if(data[mid] < key) left = mid + 1;
            else if(data[mid] > key) right = mid;
            else left = mid+1;
        }

        if (data[left]  > key) return left;
        return -1;
    }

    //旋转数组最小值
    public static int b7(int[] data ){
        int left = 0;
        int right = data.length-1;

        while (left < right){
            int mid = left + (right-left)/2;
            if(data[mid] < data[right]) right = mid;
            else left = mid+1;
        }

        if (data[left]  > key) return left;
        return -1;
    }

}