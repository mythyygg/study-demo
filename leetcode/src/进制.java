public class 进制 {


    public static void main(String[] args) {

    }

    public String add(String a, String b){
        int carry = 0;
        int i = a.length();
        int j = b.length();
        StringBuilder sb = new StringBuilder();
        while(i >= 0 || j >= 0 || carry > 0){
            int r = 0;
            if(i >= 0){
                r = r + getInt(a.charAt(i));
            }
            if(j >= 0){
                r = r + getInt(b.charAt(j));
            }
            r += carry;
            sb.append(getChar(r%36));
            carry = r / 36;
            i--;
            j--;
        }
        return sb.reverse().toString();
    }

    private int getInt(char c) {
        if (c >= '0' && c <= '9'){
            return c - '0';
        }else {
            return c - 'a' + 10;
        }
    }

    private char getChar(int x) {
        if(x >= 0 && x <= 9) {
            return  (char) (x + '0');
        } else {
            return (char) (x - 10 + 'a');
        }
    }
}
