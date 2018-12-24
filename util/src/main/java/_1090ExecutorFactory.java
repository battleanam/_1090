import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class _1090ExecutorFactory implements ExecutorFactory {
    /**
     * 创建一个原始SQL执行工具
     *
     * @return
     */
    @Override
    public _1090Executor createExecutor(String database) {
        _1090Executor executor = new _1090Executor();
        executor.setDataBase(database);
        return executor;
    }

    /**
     * 创建一个默认SQL执行工具
     *
     * @param dbType
     * @param username
     * @param password
     * @return
     */
    @Override
    public _1090Executor createExecutor(String dbType, String database, String username, String password) {
        _1090Executor executor = new _1090Executor(dbType);
        executor.setUsername(username);
        executor.setPassword(password);
        executor.setDataBase(database);
        return executor;
    }

    /**
     * 读取配置创建SQL执行工具
     *
     * @param in
     * @return
     */
    @Override
    public _1090Executor createExecutor(InputStream in) {
        Properties properties = new Properties();
        _1090Executor executor = null;
        try {
            properties.load(in);
            executor = properties.containsKey("dbType") ? new _1090Executor() : new _1090Executor(properties.getProperty("dbType"));
            if (properties.containsKey("driveType")) {
                executor.setDriverType(properties.getProperty("driveType"));
            }
            if (properties.containsKey("hostName")) {
                executor.setHostName(properties.getProperty("hostName"));
            }
            if (properties.containsKey("port")) {
                executor.setPort(properties.getProperty("port"));
            }
            if (properties.containsKey("dataBase")) {
                executor.setDataBase(properties.getProperty("dataBase"));
            }
            if (properties.containsKey("username")) {
                executor.setUsername(properties.getProperty("username"));
            }
            if (properties.containsKey("password")) {
                executor.setPassword(properties.getProperty("password"));
            }
        } catch (IOException e) {
        }
        return executor;
    }
}
