/**
 * @author _1090
 * 数据库接口 声明对数据源各属性的配置方法
 */
public interface _1090DataBase {
    /**
     * 配置驱动类型 结合数据库类型匹配数据库驱动
     * @param driverType 驱动类型 目前可选值: "jdbc"
     */
    public void setDriverType(String driverType);

    /**
     * 配置数据库类型 自动匹配URL前缀 结合驱动类型匹配数据库驱动
     * @param dbType 数据库类型 目前可选值："mysql" "mariadb" "sqlserver" "oracle" "postgresql"
     */
    public void setDbType(String dbType);

    /**
     * 配置主机名
     * @param hostName
     */
    public void setHostName(String hostName);

    /**
     * 配置端口
     * 如果参数是int类型 判别为端口号 如果参数为String类型 派别为数据库类型 设置端口为默认端口号
    * @param param 可以使用两种类型 "int" "string"
     */
    public void setPort(Object param);

    /**
     * 配置数据库名称
     * @param dataBase
     */
    public void setDataBase(String dataBase);

    /**
     * 配置用于连接的用户名
     * @param username
     */
    public void setUsername(String username);

    /**
     * 配置用于连接的用户的密码
     * @param password
     */
    public void setPassword(String password);
}
