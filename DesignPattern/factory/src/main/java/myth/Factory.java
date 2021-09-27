package myth;

public class Factory {

  public static A createA(int condition) {
    A a = null;
    if (condition == 1) {
      a = new ImplA();
    } else if (condition == 2) {
      a = new ImplB();
    }
    return a;
  }
}
