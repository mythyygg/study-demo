import java.util.*;

public class Solution {

  public static void main(String[] args) {
    int[] a = {10, 9, 8, 7, 6, 5, 1, 2, 3, 4};
    System.out.println(min(a));


  }

  public static int longestConsecutive(int[] nums) {
    Set<Integer> num_set = new HashSet<Integer>();
    for (int num : nums) {
      num_set.add(num);
    }

    int longestStreak = 0;

    for (int num : num_set) {
      if (!num_set.contains(num - 1)) {
        int currentNum = num;
        int currentStreak = 1;

        while (num_set.contains(currentNum + 1)) {
          currentNum += 1;
          currentStreak += 1;
        }

        longestStreak = Math.max(longestStreak, currentStreak);
      }
    }

    return longestStreak;
  }

  public static int min(int[] num) {
    int start = 0, end = num.length - 1;

    while (start < end) {
      if (num[start] < num[end]) {
        return num[start];
      }

      int mid = (start + end) / 2;

      if (num[mid] >= num[start]) {
        start = mid + 1;
      } else {
        end = mid;
      }
    }

    return num[start];
  }

  public List<List<Integer>> pathSum(TreeNode root, int sum) {
    List<List<Integer>> result = new ArrayList<List<Integer>>();
    List<Integer> cur = new ArrayList<>();
    if (root == null) {
      return result;
    }
    pathSum(root, sum, cur, result);
    return result;
  }

  private void pathSum(TreeNode root, int sum, List<Integer> cur, List<List<Integer>> result) {
    if (root == null) {
      return;
    }
    cur.add(root.val);
    if (root.left == null && root.right == null) {
      if (root.val == sum) {
        result.add(new ArrayList<>(cur));
      }
    }
    pathSum(root.left, sum - root.val, cur, result);
    pathSum(root.right, sum - root.val, cur, result);
    cur.remove(cur.size() - 1);
  }

  class TreeNode {

    int val;
    TreeNode left;
    TreeNode right;

    TreeNode(int x) {
      val = x;
    }
  }

  public List<Integer> selfDividingNumbers(int left, int right) {
    List<Integer> list = new ArrayList<Integer>();
    List<Integer> listAll = new ArrayList<Integer>();
    Stack<Integer> stack = new Stack<Integer>();

    int l = right - left + 1;
    for (int i = left; i < l; i++) {
      if (i < 10) {
        list.add(i);
      }
      int temp = i;
      while (temp != 0) {
        stack.push(temp % 10);
        temp /= 10;
      }
      if (stack.contains(0)) {
        break;
      }
      while (!stack.empty()) {
        int k = stack.pop();
        if (i % k != 0) {
          break;
        }
        if (stack.empty()) {
          list.add(i);
        }
      }

    }
    return list;
  }
}

