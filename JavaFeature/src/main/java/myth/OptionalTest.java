package myth;

import com.google.common.collect.Lists;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.concurrent.CountDownLatch;

/**
 * @description: 扥啊
 * @author: yuang gang
 * @create: 2018-11-25 15:23
 **/
public class OptionalTest {
    public  void excute() {
        Optional<String> maybeString = Optional.of("fas");
        List newString = maybeString.map(this::runIfExist).orElse(runIfNotExist());
        System.out.println(newString);
    }

    public   List runIfExist(String str) {
        System.out.println("run only if exist");
        return Lists.newArrayList();
    }

    public   List runIfNotExist() {
        System.out.println("run only if not exist");
        return Lists.newArrayList();
    }

    public static void main(String[] args) {
        OptionalTest optionalTest = new OptionalTest();
        optionalTest.excute();

        List<String> listString = Lists.newArrayList("1Hello world ", "1Hit the world, hah", "world", "God");
        Optional<String> longest = listString.stream()
                                    .filter(name -> name.startsWith("H"))
                                    .findFirst();
        longest.ifPresent(name -> {
            String s = name.toUpperCase();
            System.out.println(s);
        });

        String result1 = longest.map(String::toUpperCase).orElse("no result found");
        System.out.println(result1);

        String result2 = longest.map(String::toUpperCase).orElseGet(() -> "HEEE");
        System.out.println(result2);


        User user = new User();
        user.setName("zhangsan");

        User x = Optional.ofNullable(user)
            .filter(u -> "zhangsan".equals(u.getName()))
            .orElseGet(() -> {
                User user1 = new User();
                user1.setName("zhangsan");
                return user1;
            });
        System.out.println(x.getName());


        String nullValue = null;
        String optional = Optional.ofNullable(nullValue).orElse("Su");
        System.out.println(optional);
        String optionalGet = Optional.ofNullable(nullValue).orElseGet(() -> "Xiao");
        System.out.println(optionalGet);
        String nonNullOptional = Optional.ofNullable("Susan").orElse("Su");
        System.out.println(nonNullOptional);
        String nonNullOptionalGet = Optional.ofNullable("Molly").orElseGet(() -> "Xiao");
        System.out.println(nonNullOptionalGet);
        System.out.println(Optional.of("A").orElse(B()));
        System.out.println("------");
        System.out.println(Optional.of("A").orElseGet(() -> B()));

        System.out.println();
    }
    static String B() {
        System.out.println("B()...");
        return "B";
    }
}

class User {
    private String name;
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
