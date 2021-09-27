package myth;

/**
 * Hello world!
 */
public class App {

  public static void main(String[] args) {
    A a = Factory.createA(1);
    a.operation("factory run: ");
    System.out.println("Hello World!");
  }
}
