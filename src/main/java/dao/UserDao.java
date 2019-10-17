package dao;

import model.User;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import util.JDBCUtil;

/**
 * @author TALON WAT
 * @date 2019-10-17 20:51
 */
public class UserDao {
    private JdbcTemplate template = new JdbcTemplate(JDBCUtil.getDataSource());
    public User getUserByUserName(String username) {
        String sql = "SELECT * FROM tab_user WHERE username=?;";
        User user = null;
        try {
            user = template.queryForObject(sql, new BeanPropertyRowMapper<User>(User.class), username);
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
        return user;
    }

    /**
     * 注册用户
     * @param user
     * @return int，返回影响行数
     */
    public int register(User user) {
        //定义插入用户sql语句
        String sql="INSERT INTO tab_user VALUES(NULL,?,?,?,?,?,?,?,?,?)";
        //执行sql语句,返回影响行数
        return template.update(sql,
                user.getUsername(),
                user.getPassword(),
                user.getName(),
                user.getBirthday(),
                user.getSex(),
                user.getTelephone(),
                user.getEmail(),
                user.getStatus(),
                user.getCode()
        );
    }
}
