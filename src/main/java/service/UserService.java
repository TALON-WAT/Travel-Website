package service;

import dao.UserDao;
import exception.UserNameExistsException;
import exception.UserNameNotNullException;
import model.User;
import util.MailUtil;
import util.Md5Util;
import util.UuidUtil;

/**
 * @author TALON WAT
 * @date 2019-10-17 20:49
 */
public class UserService {
    //实例用户数据访问类
    private UserDao userDao = new UserDao();

    /**
     * 处理注册的业务
     * @param user
     * @return boolean,true注册成功，false注册失败
     */
    public boolean register(User user, String basePath)throws Exception {
        //数据验证-用户名不能为空（由于客户端浏览器可以禁用js，所以后端为了安全也进行基础数据验证）
        if (user.getUsername() == null || "".equals(user.getUsername().trim())) {
            //去提示用户并且用户自己处理掉，使用抛出自定义异常
            throw new UserNameNotNullException("用户名不能为空");
        }
        //根据用户输入的用户名去查找数据库对应用户
        User dbUser = userDao.getUserByUserName(user.getUsername());
        //如果数据库用户不为空，说明用户名已被注册
        if (dbUser != null) {
            //说明用户名已被注册，抛出自定义异常
            throw new UserNameExistsException("用户名已被注册");
        }
        //激活状态为未激活
        user.setStatus("N");
        //激活码,用于激活
        user.setCode(UuidUtil.getUuid());
        //对密码加密(MD5加密，消息摘要第五版加密算法,不可逆的加密算法)
        user.setPassword(Md5Util.encodeByMd5(user.getPassword()));
        //实现注册添加用户
        userDao.register(user);
        //发送激活邮件,根据用户提供的注册邮箱发送激活邮件
        MailUtil.sendMail(user.getEmail(), "<a href='http://localhost:8080" + basePath + "/user? action = active & code = "+user.getCode()+" '>用户激活<a>");
        return true;
    }

}
