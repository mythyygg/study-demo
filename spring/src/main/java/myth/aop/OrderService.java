package myth.aop;

import myth.entity.Order;

public interface OrderService {

    Order createOrder(String username, String product);

    Order queryOrder(String username);
}