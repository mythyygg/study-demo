public class quickSort {

  public static void main(String[] args) {
    int[] a = new int[]{3, 6, 2, 4, 8, 1, 0, 20, 40, 15};
    sort(a);
    System.out.println(a);
  }

  public static void sort(int[] a) {
    sort(a, 0, a.length - 1);
  }

  public static void sort(int[] a, int lo, int hi) {
    if (hi - lo >= 3) {
      int i = partion(a, lo, hi);
      sort(a, lo, i - 1);
      sort(a, i + 1, hi);
    } else {
      insertSort(a, lo, hi);
    }
  }

  public static int partion(int[] a, int lo, int hi) {
    int paviot = Median3(a, lo, hi);
    int i = lo;
    int j = hi - 1;

    while (true && i < j) {
      while (a[++i] < paviot) {
        ;
      }
      while (a[--j] > paviot) {
        ;
      }
      if (i < j) {
        exch(a, i, j);
      } else {
        break;
      }
    }
    exch(a, i, hi - 1);
    return i;

  }

  public static void insertSort(int[] a) {

  }

  public static void insertSort(int[] a, int lo, int hi) {
    int i, j, insertNote;// 要插入的数据
    for (i = lo + 1; i < hi + 1; i++) {// 从数组的第二个元素开始循环将数组中的元素插入
      insertNote = a[i];// 设置数组中的第2个元素为第一次循环要插入的数据
      j = i - 1;
      while (j >= 0 && insertNote < a[j]) {
        a[j + 1] = a[j];// 如果要插入的元素小于第j个元素,就将第j个元素向后移动
        j--;
      }
      a[j + 1] = insertNote;// 直到要插入的元素不小于第j个元素,将insertNote插入到数组中
    }
  }

  private static int Median3(int a[], int lo, int hi) {
    int mid = lo + (hi - lo) / 2;
    if (a[lo] > a[mid]) {
      exch(a, lo, mid);
    }
    if (a[lo] > a[hi]) {
      exch(a, lo, hi);
    }
    if (a[mid] > a[hi]) {
      exch(a, mid, hi);
    }
    exch(a, mid, hi - 1);
    return a[hi - 1];
  }

  public static void exch(int[] pq, int i, int j) {
    int swap = pq[i];
    pq[i] = pq[j];
    pq[j] = swap;
  }
}