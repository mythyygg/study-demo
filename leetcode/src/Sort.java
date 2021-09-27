import java.util.*;


public class Sort {

  public static void main(String[] args) {
    int[] a = new int[]{3, 6, 2, 4, 8, 1};
    heapSort(a);
    System.out.println(Arrays.asList(a));
  }

  public static void heapSort(int[] a) {
    int n = a.length;
    for (int k = n / 2; k >= 1; k--) {
      sink(a, k, n);
    }
    while (n > 1) {
      exch(a, 1, n--);
      sink(a, 1, n);
    }
  }

  public static void sink(int[] a, int k, int n) {
    while (2 * k <= n) {
      int j = 2 * k;
      if (j < n && a[j - 1] < a[j]) {
        j++;
      }
      if (a[k - 1] < a[j - 1]) {
        exch(a, k, j);
      } else {
        break;
      }
      k = j;
    }
  }

  public static void exch(int[] pq, int i, int j) {
    int swap = pq[i - 1];
    pq[i - 1] = pq[j - 1];
    pq[j - 1] = swap;
  }
}