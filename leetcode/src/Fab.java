public class Fab {

    public static void main(String[] args) {
        System.out.println(fab(1));
        System.out.println(fab(2));
        System.out.println(fab(3));
        System.out.println(fab(4));
    }
    public static int fab(int N) {
        if ( N < 2) return N;
        int fa = 0 ;
        int fb = 1;
        int i = 2;
        while( i <= N) {
            int temp = fa + fb;
            fa = fb;
            fb = temp;
            i++;
        }
        return fb;
    }
}
