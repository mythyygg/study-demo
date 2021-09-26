//import java.util.LinkedList;
//import java.util.List;
//import java.util.Queue;
//
//public class BFS {
//    private int[][] dx = new int[][]{{0, 1},{0, -1},{1, 0},{-1, 0}};
//    private static int[][] nums= {
//            {0,1,0,0,1},
//            {0,0,1,1,1},
//            {1,0,0,0,0},
//            {1,0,1,1,0},
//            {1,0,0,1,0},
//    };
//    public static void main(String[] args) {
//        BFS b = new BFS();
//        int[][] vis = new int[nums.length][nums[0].length];
//        System.out.println(b.dfs(nums, vis,0, 0));
//    }
//
//    boolean bfs(int[][] a) {
//        List<List<Boolean>> visited =
//        Queue<int[]> queue = new LinkedList<>();
//        queue.add(new int[]{0,0});
//        while(!queue.empty()){
//            int x = queue.front().first;
//            int y = queue.front().second;
//            for(int i=0; i<4; ++i){
//                int tx = x + d[i][0];
//                int ty = y + d[i][1];
//                if(tx >= 0 && tx < N && ty >= 0 && ty < M){
//                    if(ground[tx][ty] == '.' && visited[tx][ty] == false){
//                        visited[tx][ty] = true;
//                        que.push(make_pair(tx, ty));
//                    }
//                    if(ground[tx][ty] == 'E')
//                        return true;
//                }
//            }
//            que.pop();
//        }
//        return false;
//    }
//
//    boolean dfs(int[][] a, int[][] vis, int x, int y) {
//        int m = a.length - 1;
//        int n = a[0].length - 1;
//        if (x == m && y == n) {
//            return true;
//        }
//        for (int i = 0; i < 4; i++) {
//            int x1 = x + dx[i][0];
//            int y1 = y + dx[i][1];
//            if (x1 >= 0 && y1 >= 0 && x1 <= m && y1 <= n && vis[x1][y1] == 0 && a[x1][y1] ==0) {
//                vis[x1][y1] = 1;
//                if (dfs(a, vis, x1, y1))
//                    return true;
//                else {
//                    vis[x1][y1] = 0;
//                }
//            }
//        }
//        return false;
//    }
//}
//
