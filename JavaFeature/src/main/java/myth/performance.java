package myth;


import org.springframework.util.StopWatch;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class performance {

  public static void main(String[] args) throws FileNotFoundException {
    try {
      File kf = new File("kf");
      boolean exists = kf.exists();

      byte bWrite[] = { 11, 21, 3, 40, 5 };
      OutputStream os = new FileOutputStream("test.txt");

      for (int x = 0; x < bWrite.length; x++) {
        os.write(bWrite[x]); // writes the bytes
      }
      os.close();

      InputStream is = new FileInputStream("test.txt");

      int size = is.available();

      for (int i = 0; i < size; i++) {
        System.out.print((char) is.read() + "  ");
      }
      is.close();
    } catch (IOException e) {
      System.out.print("Exception");
    }
  }
}
