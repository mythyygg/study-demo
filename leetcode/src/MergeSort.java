public class MergeSort {

  private static int[] arr;

  public static void main(String[] args) {
    int[] a = new int[]{3, 6, 2, 4, 8, 1, 0, 20, 40, 15};
    sort(a);
    System.out.println(a);
  }

  public static void sort(int[] a) {
    arr = new int[a.length];
    Msort(a, 0, a.length - 1);
  }

  public static void Msort(int[] a, int lo, int hi) {
    if (lo < hi) {
      int mid = lo + (hi - lo) / 2;
      Msort(a, lo, mid);
      Msort(a, mid + 1, hi);
      Merge(a, lo, mid, hi);
    }
  }

  public static void Merge(int[] a, int lo, int mid, int hi) {
    for (int i = lo; i <= hi; i++) {
      arr[i] = a[i];
    }
    int i = lo;
    int j = mid + 1;
    for (int k = lo; k <= hi; k++) {
      if (i > mid) {
        a[k] = arr[j++];
      } else if (j > hi) {
        a[k] = arr[i++];
      } else if (arr[i] < arr[j]) {
        a[k] = arr[i++];
      } else {
        a[k] = arr[j++];
      }
    }
  }
}