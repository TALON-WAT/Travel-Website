package util;
import com.alibaba.druid.pool.DruidDataSourceFactory;
import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

/**
 * 使用Druid连接池:从Druid；连接池中获取连接对象
 * 连接池使用的步骤:
 * 1.拷贝jar包
 * 2.准备配置文件
 * 3.创建连接池对象
 * 4.使用连接池对象获取连接
 * 5.调用连接的close()方法归还连接
 */
public class JDBCUtil {
    private static DataSource dataSource;
    static {
        //1.创建Druid连接池对象
        //读取druidconfig.properties配置文件
        ClassLoader classLoader = JDBCUtil.class.getClassLoader();
        InputStream is = classLoader.getResourceAsStream("druidconfig.properties");
        Properties properties = new Properties();
        try {
            properties.load(is);
            //使用工厂类创建
            dataSource = DruidDataSourceFactory.createDataSource(properties);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static DataSource getDataSource(){
        return dataSource;
    }
    /**
     * 获取连接对象的方法
     * @return
     * @throws Exception
     */
    public static Connection getConnection() throws Exception {
        //获取连接，不再新创建了，而是从连接池对象中获取
        return dataSource.getConnection();
    }

    /**
     * 关闭conn和statement的方法
     * @param conn
     * @param stm
     * @throws SQLException
     */
    public static void close(Connection conn,Statement stm) throws SQLException {
        close(conn,stm,null);
    }

    /**
     * 关闭conn、stm、rst对象
     * @param conn
     * @param stm
     * @param rst
     */
    public static void close(Connection conn, Statement stm, ResultSet rst) throws SQLException {
        if (rst != null) {
            rst.close();
        }
        if(stm != null){
            stm.close();
        }
        if (conn != null) {
            conn.close();
        }
    }
}
