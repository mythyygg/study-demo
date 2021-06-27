import java.util.List;

public class Question {

    public static void main(String[] args) {
        int arr[] = new Methods().sort(new int[]{10, 5, 8, 7, 1, 9, 6},"down");

        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i] + " ");
        }
        List<Integer> list = new Methods().insert(arr, 6);
        System.out.println("插入数字6后的arr降序数组：");
        for (int a : list) {
            System.out.print(a + " ");
        }
        List<Integer> list1 = new Methods().insert(arr, 0);
        System.out.println("插入数字0后的降序数组：");
        for (int a : list1) {
            System.out.print(a + " ");
        }
        List<Integer> list2 = new Methods().insert(new int[]{1,2,3,4,5}, 10);
        System.out.println("有序数组插入数字0后形成的数组：");
        for (int a : list2) {
            System.out.print(a + " ");
        }
    }
}