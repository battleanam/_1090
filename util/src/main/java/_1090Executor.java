import java.sql.*;
import java.util.HashMap;
import java.util.Map;

/**
 * SQL执行工具
 * @author _1090
 */
public class _1090Executor extends _1090Log implements _1090DataBase{
    /* 初始化一个数据库连接工具 */
    private _1090DataSource dataSource;
    private Connection connection;

    public _1090Executor(){
        dataSource = _1090DataSource.buildDataSource();
    }

    public _1090Executor(String dbType){
        dataSource = _1090DataSource.buildDataSource(dbType);
    }

    public _1090Executor(String driverType, String dbType, String hostName, Object port){
        dataSource = _1090DataSource.buildDataSource(driverType,dbType,hostName,port);
    }

    public Map<String,Object> selectOne(String sql){
        Map<String,Object> result = null;
        connection = dataSource.connect(dataSource.getDataBase());
        try {
            log("创建Statement...");
            Statement statement = connection.createStatement();
            log("Statement创建成功");
            log("执行SQL语句: "+sql);
            log("参数: 数量(0)");
            ResultSet rs = statement.executeQuery(sql);
            rs.last();
            if(rs.getRow() > 1){
                error("结果匹配异常","无法将结果 List<E> 赋给 <E>");
                return null;
            }else if(rs.getRow() < 1){
                log("结果总计: "+ (result == null || result.isEmpty() ? 0 : 1));
                return new HashMap<>();
            }
            int index;
            ResultSetMetaData rsmd = rs.getMetaData();
            index = 0;
            result = new HashMap<>();
            while (index < rsmd.getColumnCount()) {
                result.put(rsmd.getColumnName(++index),rs.getObject(index));
            }
            if(connection != null) connection.close();
            statement.close();
            rs.close();
            log("结果总计: "+ (result == null || result.isEmpty() ? 0 : 1));
            return result;
        }catch (SQLException e){
            error("SQLException",e.getMessage());
        }finally {
            try {
                connection.close();
            }catch (SQLException e){
                error("SQLException",e.getMessage());
            }
        }
        log("结果总计: "  + (result == null || result.isEmpty() ? 0 : 1));
        return result;
    }

    /**
     * 获取SQL执行工具的数据源
     * @return
     */
    public _1090DataSource getDataSource() {
        return dataSource;
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
