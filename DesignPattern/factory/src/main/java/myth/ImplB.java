package myth;

public class ImplB implements A {

  @Override
  public void operation(String s) {
    System.out.println("ImplA s ==" + s);
  }
}
