package service.impl;

import dao.UserDao;
import dao.impl.UserDaoImpl;
import model.User;
import service.UserService;

/**
 * @author TALON WAT
 * @date 2019-10-17 16:55
 */
public class UserServiceImpl implements UserService {
    private UserDao userDao = new UserDaoImpl();

    @Override
    public boolean doRegister(User user) {
        //1用户查询
        //2注册用户
        User u = userDao.findUser(user.getUsername());
        System.out.println(user.getUsername());
        System.out.println(u);
        if (u != null) {
            //register filed
            return false;
        }
        userDao.saveUser(user);
        return true;

    }
}
