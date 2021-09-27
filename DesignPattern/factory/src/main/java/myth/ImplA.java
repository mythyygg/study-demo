package myth;

public class ImplA implements A {

  @Override
  public void operation(String s) {
    System.out.println("ImplA s ==" + s);
  }
}
