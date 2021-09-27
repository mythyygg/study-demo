public class Bit {
    //[1,1,2,2,3,4,4,5,5,5] 找出不重复的元素
    public static int noRepeat(int[] arr) {
        int x = 0;
        for(int i : arr){
            x ^= i;
        }
        return x;
    }
}
