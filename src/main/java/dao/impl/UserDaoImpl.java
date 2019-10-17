package dao.impl;

import dao.UserDao;
import model.User;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import util.JDBCUtil;

/**
 * @author TALON WAT
 * @date 2019-10-17 16:56
 */
public class UserDaoImpl implements UserDao {
    private JdbcTemplate template = new JdbcTemplate(JDBCUtil.getDataSource());

    @Override
    public User findUser(String username) {
        try {
            String sql = "select * from tab_user where username = ?";
            return template.queryForObject(sql, new BeanPropertyRowMapper<>(User.class),username);
        } catch (DataAccessException e) {
            return null;
        }
    }

    @Override
    public void saveUser(User user) {
        System.out.println(user.getUsername());
        String sql = "insert into tab_user(username,password,name,birthday,sex,telephone,email) values (?,?,?,?,?,?,?)";
        template.update(sql,
                user.getUsername(),
                user.getPassword(),
                user.getName(),
                user.getBirthday(),
                user.getSex(),
                user.getTelephone(),
                user.getEmail());
    }
}
