package myth.generic;

import java.util.ArrayList;
import java.util.List;


/**
 * @description:
 * @author: yuang gang
 * @create: 2018-12-20 15:38
 **/
public class Box<T> {

  private T u;

  public static <U> void addBox(U u,
      java.util.List<Box<U>> boxes) {
    Box<U> box = new Box<>();
    box.set(u);
    boxes.add(box);
  }

  private void set(T u) {
    this.u = u;
  }

  public static <U> void outputBoxes(List<Box<U>> boxes) {
    int counter = 0;
    for (Box<U> box : boxes) {
      U boxContents = box.get();
      System.out.println("Box #" + counter + " contains [" +
          boxContents.toString() + "]");
      counter++;
    }
  }

  private T get() {
    return u;
  }

  public static void main(String[] args) {
    ArrayList<Box<Integer>> listOfIntegerBoxes = new ArrayList<>();
    Box.addBox(10, listOfIntegerBoxes);
    Box.addBox(20, listOfIntegerBoxes);
    Box.addBox(30, listOfIntegerBoxes);
    Box.outputBoxes(listOfIntegerBoxes);
  }
}
