package myth.xmlConfig;

import myth.entity.Person;
import org.springframework.beans.BeansException;
import org.springframework.beans.PropertyValues;
import org.springframework.beans.factory.config.InstantiationAwareBeanPostProcessorAdapter;

import java.beans.PropertyDescriptor;

/**
 * @description:
 * @author: yuang gang
 * @create: 2019-02-19 14:43
 **/
public class MyInstantiationAwareBeanPostProcessor extends
    InstantiationAwareBeanPostProcessorAdapter {

  public MyInstantiationAwareBeanPostProcessor() {
    super();
    System.out.println("构造器 InstantiationAwareBeanPostProcessor,初始化");
  }

  // 接口方法、实例化Bean之前调用
  @Override
  public Object postProcessBeforeInstantiation(Class beanClass,
      String beanName) throws BeansException {
    if ("person".equals(beanName)) {
      System.out.println("InstantiationAwareBeanPostProcessor 调用 postProcessBeforeInstantiation方法");
    }
    return null;
  }

  @Override
  public boolean postProcessAfterInstantiation(Object bean, String beanName) throws BeansException {
    if ("person".equals(beanName)) {
      Person person = (Person) bean;
      person.setName("world");
      System.out.println("InstantiationAwareBeanPostProcessor 调用 postProcessAfterInstantiation方法");
    }
    return super.postProcessAfterInstantiation(bean, beanName);
  }

  // 接口方法、实例化Bean之后调用
  @Override
  public Object postProcessAfterInitialization(Object bean, String beanName)
      throws BeansException {
    if ("person".equals(beanName)) {
      System.out.println("InstantiationAwareBeanPostProcessor 调用 postProcessAfterInitialization方法");
    }
    return bean;
  }

  @Override
  public Object postProcessBeforeInitialization(Object bean, String beanName)
      throws BeansException {
    if ("person".equals(beanName)) {

      System.out
          .println("InstantiationAwareBeanPostProcessor 调用 postProcessBeforeInitialization方法");
    }
    return super.postProcessBeforeInitialization(bean, beanName);
  }

  // 接口方法、设置某个属性时调用
  @Override
  public PropertyValues postProcessPropertyValues(PropertyValues pvs, PropertyDescriptor[] pds,
      Object bean, String beanName)
      throws BeansException {
    if ("person".equals(beanName)) {
      System.out.println("InstantiationAwareBeanPostProcessor 调用 postProcessPropertyValues方法");
    }
    return pvs;
  }


}
