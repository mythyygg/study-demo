package myth;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Set;

/**
 * @description: java8 时间日期
 * @author: yuang gang
 * @create: 2018-11-24 18:28
 **/
public class Java8Time {

  public static void main(String[] args) {
    Set<String> zids = ZoneId.getAvailableZoneIds();
    System.out.println(zids);

    Instant instant = Instant.now();
    System.out.println(instant.getEpochSecond());
    System.out.println(instant.getNano());

    //localDate
    LocalDate date = LocalDate.now();
    System.out.println(new Date());
    System.out.println(date);
    LocalTime time = LocalTime.now();
    System.out.println(time);

    LocalDateTime dateTime = LocalDateTime.now();
    System.out.println(dateTime);

    DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;
    DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    String formatDate = formatter1.format(dateTime);
    LocalDateTime dd = LocalDateTime.parse(formatDate, formatter1);
  }
}
