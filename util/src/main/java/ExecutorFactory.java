import java.io.InputStream;

/**
 * SQL执行工具工厂
 *
 * @author _1090
 */
public interface ExecutorFactory {
    /**
     * 创建一个原始SQL执行工具
     *
     * @param database
     * @return
     */
    public _1090Executor createExecutor(String database);

    /**
     * 创建一个默认SQL执行工具
     *
     * @param dbType
     * @param database
     * @param username
     * @param password
     * @return
     */
    public _1090Executor createExecutor(String dbType, String database, String username, String password);

    /**
     * 读取配置创建SQL执行工具
     *
     * @param url
     * @return
     */
    public _1090Executor createExecutor(InputStream url);
}
