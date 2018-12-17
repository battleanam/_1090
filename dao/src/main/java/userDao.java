import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

public class userDao {
    public static void main(String args[]) {
        ExecutorFactory executorFactory = new _1090ExecutorFactory();
        _1090Executor test = executorFactory.createExecutor("mariadb", "test", "root", "zhegebunenggaosuni");
        _1090Executor tai12138 = executorFactory.createExecutor("mariadb", "tai12138", "root", "zhegeyebunenggaosuni");
        List<Map<String, Object>> testData = test.selectList("SELECT * FROM measurement_data");
        for (Map<String, Object> element : testData) {
            int deviceId = (Integer) tai12138.selectOne("select * from device where mac = '" + element.get("devicename") + "'").get("id");
            try {
                tai12138.insert("insert into data(device,route,pole,time,total,a,b,c,d,lat,lon,man) values(" +
                        deviceId + ",'" +
                        element.get("routename") + "','" +
                        element.get("number") + "'," +
                        new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(String.valueOf(element.get("time"))).getTime() / 1000 + "," +
                        element.get("total") + "," +
                        element.get("A") + "," +
                        element.get("B") + "," +
                        element.get("C") + "," +
                        element.get("D") + "," +
                        element.get("lat") + "," +
                        element.get("lon") + ",'" +
                        element.get("man") + "')");
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }
}
