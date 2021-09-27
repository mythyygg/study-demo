public class IntegerSolution {
    //
    public int reverse(int x) {
        int y = 0;
        while ( x > 0 ) {
            int tmp = y*10 + x%10;
            if (tmp / 10 != y){
                y = 0;
                break;
            }
            x = x/10;
            y = tmp;
        }
        return y;
    }
}
