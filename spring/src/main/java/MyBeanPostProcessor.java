
import myth.entity.Person;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

/**
 * @description:
 * @author: yuang gang
 * @create: 2019-02-19 14:42
 **/
public class MyBeanPostProcessor implements BeanPostProcessor {

  public MyBeanPostProcessor() {
    System.out.println("构造器 BeanPostProcessor,初始化");
  }

  @Override
  public Object postProcessBeforeInitialization(Object bean, String beanName)
      throws BeansException {
    if ("person".equals(beanName)) {
      Person person = (Person) bean;
      person.setAge(100);
      System.out.println("BeanPostProcessor接口方法postProcessBeforeInitialization对属性进行更改！");
    }
    return bean;
  }

  @Override
  public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
    if ("person".equals(beanName)) {
      Person person = (Person) bean;
      person.setName("yuangang");
      System.out.println("BeanPostProcessor接口方法postProcessAfterInitialization对属性进行更改!");
    }
    return bean;
  }
}
