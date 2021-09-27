package myth.entity;

/**
 * @description:
 * @author: yuang gang
 * @create: 2019-12-05
 **/
public class Order {
    private String username;
    private String product;

    public Order() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String userame) {
        this.username = userame;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }
}
