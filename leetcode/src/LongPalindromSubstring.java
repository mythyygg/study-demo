public class LongPalindromSubstring {

  public static void main(String[] args) {
    String a = "babad";
    System.out.println(longestPalindrome(a));
  }

  public static String longestPalindrome(String s) {
    if (s == null || s.length() == 0) {
      return "";
    }
    int length = s.length();
    int max = 0;
    int pos = 0;
    int[] rl = new int[s.length() * 2 + 1];
    String result = "";
    rl[0] = 1;
    for (int i = 1; i < 2 * length + 1; i++) {
      if (i < max) {
        rl[i] = Math.min(rl[2 * pos - i], max - i + 1);
      } else {
        rl[i] = 1;
      }
      while (i - rl[i] >= 0 && i + rl[i] < rl.length && get(s, i - rl[i]) == get(s, i + rl[i])) {
        rl[i]++;
      }

      if (rl[i] - i + 1 > max) {
        max = rl[i] - i + 1;
        pos = i;
      }
    }
    return s.substring((2 * pos - max) / 2, max / 2);
  }

  private static char get(String s, int i) {
    if (i % 2 == 0) {
      return '#';
    } else {
      return s.charAt(i / 2);
    }
  }
}
