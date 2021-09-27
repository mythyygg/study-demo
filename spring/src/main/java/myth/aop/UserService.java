package myth.aop;

import myth.entity.User;

public interface UserService {

    User createUser(String firstName, String lastName, int age);

    User queryUser();
}