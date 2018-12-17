import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * 数据源
 *
 * @author _1090
 */
public class _1090DataSource extends _1090Log implements _1090DataBase {
    /**
     * 数据库驱动资源文件
     */
    private Properties driverProperties;
    /**
     * 数据库URL前缀资源文件
     */
    private Properties dbUrlPrefixProperties;
    /**
     * 数据库默认端口资源文件
     */
    private Properties dbDefaultPortProperties;
    /**
     * 驱动类型
     */
    private String driverType;
    /**
     * 数据库类型
     */
    private String dbType;
    /**
     * 主机名
     */
    private String hostName;
    /**
     * 端口号
     */
    private Integer port;
    /**
     * 数据库
     */
    private String dataBase;
    /**
     * 用户名
     */
    private String username;
    /**
     * 密码
     */
    private String password;

    /**
     * 构造器
     */
    private _1090DataSource() {
        log("初始化数据库连接工具...");
        this.initDbUrlPrefixProperties();
        this.initDriverProperties();
        this.initDbDefaultPortProperties();
        log("数据库连接工具初始化完成");
    }

    /**
     * 构造器
     *
     * @param dbType 数据库类型
     */
    private _1090DataSource(String dbType) {
        log("初始化数据库连接工具...");
        this.initDbUrlPrefixProperties();
        this.initDriverProperties();
        this.initDbDefaultPortProperties();
        this.setDriverType("jdbc");
        this.setDbType(dbType);
        this.setHostName("localhost");
        this.setPort(dbType);
        log("数据库连接工具初始化完成");
    }

    /**
     * 构造器
     *
     * @param driverType 数据库驱动类型
     * @param dbType     数据库类型
     * @param hostName   主机名
     * @param port       数据库占用端口
     */
    private _1090DataSource(String driverType, String dbType, String hostName, Object port) {
        log("初始化数据库连接工具...");
        this.initDbUrlPrefixProperties();
        this.initDriverProperties();
        this.initDbDefaultPortProperties();
        this.setDriverType(driverType);
        this.setDbType(dbType);
        this.setHostName(hostName);
        this.setPort(port);
        log("数据库连接工具初始化完成");
    }

    /**
     * 构建一个原始数据源，不可以直接使用，需要对各项进行配置
     *
     * @return
     */
    public static _1090DataSource buildDataSource() {
        return new _1090DataSource();
    }

    /**
     * 构建一个默认数据源，提供数据库类型，默认驱动类型为“JDBC”、主机名为“localhost”、数据库占用端口为默认端口
     *
     * @param dbType 数据库类型
     * @return
     */
    public static _1090DataSource buildDataSource(String dbType) {
        return new _1090DataSource(dbType);
    }

    /**
     * 构建一个数据源
     *
     * @param driverType 数据库驱动类型
     * @param dbType     数据库类型
     * @param hostName   主机名
     * @param port       数据库占用端口
     * @return
     */
    public static _1090DataSource buildDataSource(String driverType, String dbType, String hostName, Object port) {
        return new _1090DataSource(driverType, dbType, hostName, port);
    }

    /**
     * 连接数据库 使用之前需要配置用户名、密码
     *
     * @param dataBase 要连接的数据库
     * @return 返回一个到dataBase的连接
     */
    public Connection connect(String dataBase) {
        this.setDataBase(dataBase);
        try {
            Class.forName(this.getDriver());
            Connection connection = DriverManager.getConnection(
                    this.getUrl(),
                    this.username,
                    this.password
            );
            log("建立与数据库 \"" + this.getUrl() + "\" 的连接");
            return connection;
        } catch (ClassNotFoundException e) {
            error("ClassNotFoundException", "数据库驱动加载失败");
            log(e.getMessage());
        } catch (SQLException e) {
            error("SQLException", "无法连接到数据库 \"" + this.getUrl() + "\" , 用户名: " + this.username + " 密码: " + this.password);
            log(e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 加载数据库驱动资源
     */
    private void initDriverProperties() {
        try {
            log("加载数据库驱动资源...");
            InputStream in = _1090DataSource.class.getResourceAsStream("dbDriver.properties");
            Properties properties = new Properties();
            properties.load(in);
            this.driverProperties = properties;
            log("数据库驱动资源加载完成");
            in.close();
        } catch (FileNotFoundException e) {
            error("FileNotFoundException", "未找到文件 \"dbDriver.properties\"");
            log(e.getMessage());
        } catch (IOException e) {
            error("IOException", "无法从文件 \"dbDriver.properties\" 中加载配置信息");
            log(e.getMessage());
        }
    }

    /**
     * 加载数据库URL资源
     */
    private void initDbUrlPrefixProperties() {
        try {
            log("加载URL资源...");
            InputStream in = _1090DataSource.class.getResourceAsStream("dbUrlPrefix.properties");
            Properties properties = new Properties();
            properties.load(in);
            this.dbUrlPrefixProperties = properties;
            log("URL资源加载完成");
            in.close();
        } catch (FileNotFoundException e) {
            error("未找到文件 \"dbUrlPrefix.properties\"");
            log(e.getMessage());
        } catch (IOException e) {
            error("无法从文件 \"dbUrlPrefix.properties\" 中加载配置信息");
            log(e.getMessage());
        }
    }

    /**
     * 加载数据库默认端口资源
     */
    private void initDbDefaultPortProperties() {
        try {
            log("加载数据库默认端口资源...");
            InputStream in = _1090DataSource.class.getResourceAsStream("dbDefaultPort.properties");
            Properties properties = new Properties();
            properties.load(in);
            this.dbDefaultPortProperties = properties;
            log("数据库默认端口资源加载完成");
            in.close();
        } catch (FileNotFoundException e) {
            error("未找到文件 \"dbDefaultPort.properties\"");
            log(e.getMessage());
        } catch (IOException e) {
            error("无法从文件 \"dbDefaultPort.properties\" 中加载配置信息");
            log(e.getMessage());
        }
    }

    /**
     * 获取数据源当前使用的驱动
     *
     * @return 返回一个驱动类的完全限定名
     */
    public String getDriver() {
        String driverUrl = this.driverProperties.getProperty(this.getDriverType() + "." + this.getDbType());
        if (driverUrl == null) {
            error("未匹配到 \"" + this.getDbType() + "\" 的默认 \"" + this.getDriverType() + "\" 驱动");
            return "";
        }
        return driverUrl;
    }

    /**
     * 获取最后一次连接数据库的URL
     *
     * @return
     */
    public String getUrl() {
        String urlPrefix = this.dbUrlPrefixProperties.getProperty(this.getDriverType() + "." + this.getDbType());
        return urlPrefix + this.hostName + ":" + this.port + "/" + dataBase;
    }

    /**
     * 获取驱动类型
     *
     * @return
     */
    public String getDriverType() {
        return driverType;
    }

    /**
     * 配置驱动类型 结合数据库类型匹配数据库驱动
     *
     * @param driverType 驱动类型 目前可选值: "jdbc"
     */
    public void setDriverType(String driverType) {
        this.driverType = driverType.toLowerCase();
    }

    /**
     * 获取当前数据源的数据库类型
     *
     * @return
     */
    public String getDbType() {
        return dbType;
    }

    /**
     * 配置数据库类型 自动匹配URL前缀 结合驱动类型匹配数据库驱动
     *
     * @param dbType 数据库类型 目前可选值："mysql" "mariadb" "sqlserver" "oracle" "postgresql"
     */
    public void setDbType(String dbType) {
        this.dbType = dbType.toLowerCase();
    }

    /**
     * 获取当前连接的主机名
     *
     * @return
     */
    public String getHostName() {
        return hostName;
    }

    /**
     * 配置主机名
     *
     * @param hostName
     */
    public void setHostName(String hostName) {
        this.hostName = hostName.toLowerCase();
    }

    @Override
    public void setPort(Object param) {
        if (param instanceof String) {
            setPort((String) param);
        } else if (param instanceof Integer) {
            setPort((Integer) param);
        }
    }

    /**
     * 获取最后一次连接数据库的端口号
     *
     * @return
     */
    public Integer getPort() {
        return port;
    }

    /**
     * 配置端口
     *
     * @param port
     */
    public void setPort(Integer port) {
        this.port = port;
    }

    /**
     * 根据数据库类型配置默认端口
     *
     * @param dbType
     */
    public void setPort(String dbType) {
        String port = this.dbDefaultPortProperties.getProperty(dbType);
        if (port == null) error("未匹配到数据库类型 \"" + dbType + "\" 的默认端口");
        else this.setPort(Integer.parseInt(port));
    }

    /**
     * 获取数据库名称
     *
     * @return
     */
    public String getDataBase() {
        return dataBase;
    }

    /**
     * 配置数据库名称
     *
     * @param dataBase
     */
    public void setDataBase(String dataBase) {
        this.dataBase = dataBase.toLowerCase();
    }

    /**
     * 获取用于连接的用户名
     *
     * @return
     */
    public String getUsername() {
        return username;
    }

    /**
     * 配置用于连接的用户名
     *
     * @param username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * 配置用于连接的用户的密码
     *
     * @param password
     */
    public void setPassword(String password) {
        this.password = password;
    }

}
