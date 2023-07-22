package utils;

import com.alibaba.druid.pool.DruidDataSourceFactory;

import javax.sql.DataSource;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class druidTool {
    private static DataSource dataSource;

    static {
        //加载配置文件

        try {
            Properties properties = new Properties();
            properties.load(druidTool.class.getClassLoader().getResourceAsStream("druid.properties"));
            //获取DataSource对象
            dataSource = DruidDataSourceFactory.createDataSource(properties);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    //获取连接
    public static Connection getConnection() throws SQLException {
        return dataSource.getConnection();

    }

    //释放资源
    public  static void close(Statement stmt, Connection conn){
        if (stmt!=null){
            try {
                stmt.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        if (conn!=null){
            try {
                conn.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
    public  static void close(Statement stmt, Connection conn, ResultSet re){
        if (stmt!=null){
            try {
                stmt.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        if (conn!=null){
            try {
                conn.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        if (re!=null){
            try {
                re.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
    //获取连接池
    public static DataSource getDataSource(){
        return dataSource;
    }
}
