package myth;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * Unit test for simple App.
 */
public class AppTest {

  /**
   * Rigorous Test :-)
   */
  @Test
  public void shouldAnswerWithTrue() {
    assertTrue(true);
  }


  public static void main(String[] args) {
    int[] a = new int[]{2, 6, 5, 4, 8, 10, 9, 15};
    findUnsortedSubarray(a);
  }

  public static int findUnsortedSubarray(int[] arr) {
    if (arr == null || arr.length == 0) {
      return 0;
    }
    int minIndex = -1;
    int maxIndex = -1;
    int min = arr[arr.length - 1];
    int max = arr[0];
    for (int i = arr.length - 2; i >= 0; i--) {
      if (arr[i] > min) {
        minIndex = i;
      } else {
        min = arr[i];
      }
    }
    if (minIndex == -1) {
      return 0;
    }
    for (int i = 1; i < arr.length; i++) {
      if (arr[i] < max) {
        maxIndex = i;
      } else {
        max = arr[i];
      }
    }
    if (maxIndex == -1) {
      return 0;
    }

    return maxIndex - minIndex + 1;
  }
}
