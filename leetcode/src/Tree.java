import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode() {}
 *     TreeNode(int val) { this.val = val; }
 *     TreeNode(int val, TreeNode left, TreeNode right) {
 *         this.val = val;
 *         this.left = left;
 *         this.right = right;
 *     }
 * }
 */
class Tree {
    public static void main(String[] args) {
        TreeNode root = new TreeNode();
        root.val = 1;

    }
    public boolean searchMatrix(int[][] matrix, int target) {
        int row = matrix.length;
        int col = matrix[0].length;
        int l = 0;
        int r = row*col-1;
        while(l <= r) {
            int mid = l + (r-l)/2;
            if(matrix[mid/col][mid%col] == target ) return true;
            else if(matrix[mid/col][mid%col] < target) {
                mid = l + 1;
            }
            else  mid = r - 1;
        }
        return false;
    }
    public int myAtoi(String str) {
        if(str == null) return 0;
        int sing = 1;
        int index = 0;
        int len = str.length();
        int sum = 0;
        while(index < len && (str.charAt(index) == ' ')) index++;
        if(index < len ) {
            if (str.charAt(index) == '-') {sing = -1; index++;}
            if (str.charAt(index) == '+') {sing = 1; index++;}
        }
        while(index < len && str.charAt(index) >= '0' && str.charAt(index) <= '9') {
            int tmp = sum * 10 + str.charAt(index) - '0';
            if(tmp / 10 != sum) {
                return sing > 0 ? Integer.MAX_VALUE : Integer.MIN_VALUE;
            }
            sum = tmp;
            index++;
        }
        return sum*sing;
    }
    public List<Integer> rightSideView(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        Queue<TreeNode> q = new LinkedList<>();
        if(root == null) return result;
        q.offer(root);
        while(!q.isEmpty()) {
            int size = q.size();
            for (int i = 0; i < size; i++) {
                TreeNode node= q.poll();

                if(root.left != null) q.offer(root.left);
                if(root.right != null) q.offer(root.right);
                if(i == size-1){
                    result.add(node.val);
                }
            }
        }
        return result;
    }
}