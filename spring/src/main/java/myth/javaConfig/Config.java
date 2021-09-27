package myth.javaConfig;

import myth.entity.Person;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * @description:
 * @author: yuang gang
 * @create: 2019-02-19 18:11
 **/
@Configuration
public class Config {

  @Bean
  public Person person() {
    Person person = new Person();
    person.setAge(10);
    person.setName("hello");
    return person;
  }
}
