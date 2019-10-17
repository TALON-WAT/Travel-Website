package dao;

import model.User;

/**
 * @author TALON WAT
 * @date 2019-10-17 16:56
 */
public interface UserDao {
    User findUser(String username);
    void saveUser(User user);
}
