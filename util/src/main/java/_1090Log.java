
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 日志打印工具
 * @author _1090
 */
public class _1090Log {

    protected _1090Log(){

    }

    /**
     * 打印消息
     */
    protected void log(String msg){
        System.out.println("["+new SimpleDateFormat("hh:mm:ss").format(new Date())+"]"+"[INFO] "+msg);
    }

    /**
     * 打印错误
     */
    protected void error(String type,String msg){
        System.out.println("["+new SimpleDateFormat("hh:mm:ss").format(new Date())+"]"+"[ERROR]["+type+"] "+msg);
    }

    /**
     * 打印错误
     */
    protected void error(String msg){
        String type = "程序异常";
        System.out.println("["+new SimpleDateFormat("hh:mm:ss").format(new Date())+"]"+"[ERROR]["+type+"] "+msg);
    }
    /**
     * 打印警告
     */
    protected void warning(String msg){
        System.out.println("["+new SimpleDateFormat("hh:mm:ss").format(new Date())+"]"+"[WARNING] "+msg);
    }
}
