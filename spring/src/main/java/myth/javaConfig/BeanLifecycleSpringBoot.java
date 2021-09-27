package myth.javaConfig;

import myth.entity.Person;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @description:
 * @author: yuang gang
 * @create: 2019-02-19 15:30
 **/
@SpringBootApplication
public class BeanLifecycleSpringBoot implements CommandLineRunner {

  public static void main(String[] args) {
    SpringApplication.run(BeanLifecycleSpringBoot.class, args);
  }

  public void testXmlConfig() {
    System.out.println("初始化容器");
    ApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");
    System.out.println("容器初始化成功");
    Person person = context.getBean(Person.class);

    System.out.println(person);
    System.out.println("开始关闭容器");
    ((ClassPathXmlApplicationContext) context).registerShutdownHook();
  }

  public void testJavaConfig() {
    System.out.println("初始化容器");
    ApplicationContext context = new AnnotationConfigApplicationContext(Config.class);
    System.out.println("容器初始化成功");
    Person person = context.getBean(Person.class);
    System.out.println(person);
    System.out.println("开始关闭容器");
    ((AnnotationConfigApplicationContext) context).registerShutdownHook();
  }

  @Override
  public void run(String... args) throws Exception {
    testJavaConfig();
  }
}
