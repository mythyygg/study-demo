import java.util.ArrayList;
import java.util.List;

public class matrix {


  public static void main(String[] args) {
    int[][] matrix = {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};
    System.out.println(spiralOrder(matrix));

  }

  public static List<Integer> spiralOrder(int[][] matrix) {
    if (matrix.length == 0 || matrix[0].length == 0) {
      return new ArrayList<>();
    }
    int row = matrix.length;
    int col = matrix[0].length;
    int len = row * col;
    List<Integer> result = new ArrayList<>(len);
    int di = 0, dj = 1, i = 0, j = 0;
    int temp;
    for (int n = 0; n < len; n++) {
      result.add(matrix[i][j]);
      matrix[i][j] = 0;
      if (matrix[(i + di + row) % row][(j + dj + col) % col] == 0) {
        temp = di;
        di = dj;
        dj = -temp;
      }
      i += di;
      j += dj;
    }
    return result;
  }
}
