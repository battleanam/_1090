public class userDao {
    public static void main(String args[]){
        ExecutorFactory executorFactory = new _1090ExecutorFactory();
        _1090Executor userExecutor = executorFactory.createExecutor("mariadb","tai12138","root","Dream_1874");
        System.out.println(userExecutor.selectOne("SELECT * FROM T_USER WHERE ID = 8"));
    }
}
