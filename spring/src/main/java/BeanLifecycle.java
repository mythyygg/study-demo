import myth.entity.Person;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @description:
 * @author: yuang gang
 * @create: 2019-02-19 15:30
 **/
public class BeanLifecycle {

  public static void main(String[] args) {
    System.out.println("初始化容器");
    ApplicationContext context = new ClassPathXmlApplicationContext("classpath:beans.xml");
    System.out.println("容器初始化成功");
    Person person = context.getBean(Person.class);
    System.out.println(person);
    System.out.println("开始关闭容器");
    ((ClassPathXmlApplicationContext) context).registerShutdownHook();
  }

}
