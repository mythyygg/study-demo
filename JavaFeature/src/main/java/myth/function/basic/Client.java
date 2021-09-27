package myth.function.basic;

import java.util.Arrays;
import java.util.List;

/**
 * Created by shuqin on 17/3/31.
 */
public class Client {

  public static void main(String[] args) {
    myth.function.basic.Report.report(Arrays.asList("Id", "Name"), getPersons());
    myth.function.basic.Report.report(Arrays.asList("Name", "Able"), getPersons());
  }

  private static List<myth.function.basic.Person> getPersons() {
    myth.function.basic.Person s1 = new myth.function.basic.Student("s1", "liming", "Study");
    myth.function.basic.Person s2 = new myth.function.basic.Student("s2", "xueying", "Piano");
    myth.function.basic.Person t1 = new myth.function.basic.Teacher("t1", "Mr.Q", "Swim");
    myth.function.basic.Person t2 = new myth.function.basic.Teacher("t2", "Mrs.L", "Dance");
    return Arrays.asList(s1, s2, t1, t2);
  }

}
