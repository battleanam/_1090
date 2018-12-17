import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * SQL执行工具
 *
 * @author _1090
 */
public class _1090Executor extends _1090Log implements _1090DataBase {
    /**
     * 初始化一个数据库连接工具
     */
    private _1090DataSource dataSource;
    /**
     * 建立一个到指定数据库的链接
     */
    private Connection connection;
    /**
     * 执行SQL语句的statement
     */
    private Statement statement;
    /**
     * 查询结果
     */
    private ResultSet rs;

    /**
     * 初始化三巨头
     */
    private void onInit() {
        this.connection = null;
        this.statement = null;
        this.rs = null;
    }

    public _1090Executor() {
        dataSource = _1090DataSource.buildDataSource();
        onInit();
    }

    public _1090Executor(String dbType) {
        dataSource = _1090DataSource.buildDataSource(dbType);
        onInit();
    }

    public _1090Executor(String driverType, String dbType, String hostName, Object port) {
        dataSource = _1090DataSource.buildDataSource(driverType, dbType, hostName, port);
        onInit();
    }

    /**
     * 插入语句
     *
     * @param sql
     * @return
     */
    public int insert(String sql) {
        execute(sql);
        close();
        return 1;
    }

    /**
     * 执行一条只有一条结果的查询语句
     *
     * @param sql 被执行的SQL语句
     * @return 查询结果
     */
    public Map<String, Object> selectOne(String sql) {
        Map<String, Object> result = null;
        execute(sql);
        try {
            rs.last();
            if (rs.getRow() > 1) {
                error("结果匹配异常", "无法将结果 List<E> 赋给 <E>");
                close();
                return null;
            } else if (rs.getRow() < 1) {
                log("结果总计: 0");
                close();
                return new HashMap<>();
            }
            ResultSetMetaData rsmd = rs.getMetaData();
            int index = 0;
            result = new HashMap<>();
            while (index < rsmd.getColumnCount()) {
                result.put(rsmd.getColumnName(++index), rs.getObject(index));
            }
            log("结果总计: " + (result.isEmpty() ? 0 : 1));
            close();
            return result;
        } catch (SQLException e) {
            error("SQLException", e.getMessage());
            close();
        }
        log("结果总计: " + (result == null || result.isEmpty() ? 0 : 1));
        return result;
    }

    /**
     * 执行一条具有多个结果查询语句
     *
     * @param sql
     * @return
     */
    public List<Map<String, Object>> selectList(String sql) {
        Map<String, Object> element = new HashMap<>();
        List<Map<String, Object>> result = new ArrayList<>();
        execute(sql);
        try {
            int index;
            ResultSetMetaData rsmd = rs.getMetaData();
            while (rs.next()) {
                index = 0;
                while (index < rsmd.getColumnCount()) {
                    element.put(rsmd.getColumnName(++index), rs.getObject(index));
                }
                result.add(element);
                element = new HashMap<>();
            }
        } catch (SQLException e) {
            error("SQLException", e.getMessage());
        }
        log("结果总计: " + result.size());
        return result;
    }

    /**
     * 执行SQL语句
     *
     * @param sql
     * @return
     */
    private void execute(String sql) {
        connection = dataSource.connect(dataSource.getDataBase());
        log("创建Statement...");
        Statement statement = null;
        try {
            statement = connection.createStatement();
            log("Statement创建成功");
            log("执行SQL语句: " + sql);
            log("参数: 数量(0)");
            rs = statement.executeQuery(sql);
        } catch (SQLException e) {
            error("SQLException", e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * 关闭三巨头
     */
    private void close() {
        try {
            if (rs != null) {
                rs.close();
            }
            if (statement != null) {
                statement.close();
            }
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            error("SQLException", e.getMessage());
        }
    }

    public String getDriver() {
        return this.dataSource.getDriver();
    }

    public String getUrl() {
        return this.dataSource.getUrl();
    }

    @Override
    public void setDriverType(String driverType) {
        this.dataSource.setDriverType(driverType);
    }

    @Override
    public void setDbType(String dbType) {
        this.dataSource.setDbType(dbType);
    }

    @Override
    public void setHostName(String hostName) {
        this.dataSource.setHostName(hostName);
    }

    @Override
    public void setPort(Object param) {
        this.dataSource.setPort(param);
    }

    @Override
    public void setDataBase(String dataBase) {
        this.dataSource.setDataBase(dataBase);
    }

    @Override
    public void setUsername(String username) {
        this.dataSource.setUsername(username);
    }

    @Override
    public void setPassword(String password) {
        this.dataSource.setPassword(password);
    }
}
