package Demo1;


import org.springframework.jdbc.core.JdbcTemplate;
import utils.druidTool;

import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class demo {
    //获取jdbcTemplate对象
    static JdbcTemplate jdbcTemplate = new JdbcTemplate(druidTool.getDataSource()); //默认就是private
    public static String root = null;
    public static String password  = null;
    public static void main(String[] args) {

        while (true) {
            System.out.println("-------------------欢迎来到选课系统--------------------");
            System.out.println("1.学生登陆");
            System.out.println("2.教师登录");
            System.out.println("3.注册");
            System.out.println("0.退出程序");

            Scanner sc = new Scanner(System.in);
            String option = sc.nextLine();

            switch (option) {
                case "1": {
                   if (isloginStudnet()){
                       studentChoose();
                   }
                    break;

                }
                case "2": {
                   if (isloginTeacher()){
                       teacherChoose();
                   }
                    break;

                }
                case "3": {

                    break;
                }
                case "0": {
                    System.exit(0);
                    //退出程序
                }
            }

        }
    }



    private static boolean isloginTeacher() {
        System.out.println("教师登陆系统  请输入你的账号和密码：");
        while (true) {
            Scanner scanner = new Scanner(System.in);
            System.out.println("输入账号");
            root = scanner.nextLine();
            System.out.println("输入密码");
            password = scanner.nextLine();

            String sql = "select * from teacher where root = ? and password = ? ";
            //执行sql
            List<Map<String, Object>> islogin = jdbcTemplate.queryForList(sql, root, password);
            if (islogin.size() != 0) {
                System.out.println("登录成功");
                return true;
            } else {
                System.out.println("登录失败 请重新输入账号密码");
                return false;
            }
        }
    }

    private static void teacherChoose(){
        while (true) {
            System.out.println("-------------------欢迎来到教师选课系统--------------------");
            System.out.println("1.个人信息查询");
            System.out.println("2.个人信息修改");
            System.out.println("3.账号密码修改");
            System.out.println("4.选课查询");
            System.out.println("0.退出程序");

            Scanner sc = new Scanner(System.in);
            String option = sc.nextLine();
            switch (option){
                case "1":{
                    String sql = "select * from teacher where root = ? and password = ? ";
                    Map<String,Object> stringObjectMap = jdbcTemplate.queryForMap(sql, root, password);
                    stringObjectMap.values().stream().map(Object::toString).forEach(value -> System.out.print(value.replaceAll("[()]", "") + " "));
                    System.out.println("");
                    break;
                }
                case "2":{
                    while (true) {
                        System.out.println("选择你要修改的内容");
                        System.out.println("1.姓名");
                        System.out.println("2.电话");
                        System.out.println("0.退出");
                        Scanner sc1 = new Scanner(System.in);
                        String option1 = sc1.nextLine();
                        switch (option1) {
                            case "1": {
                                System.out.println("你的新姓名是?");
                                String name = sc.nextLine();
                                String sql = "update  teacher set name  = ? where root = ? and password = ? ";
                                jdbcTemplate.update(sql, name, root, password);
                                System.out.println("姓名修改成功!");
                                break;
                            }
                            case "2": {
                                System.out.println("你的新电话是?");
                                String phone = sc.nextLine();
                                String sql = "update  teacher set  phone  = ? where root = ? and password = ? ";
                                jdbcTemplate.update(sql, phone, root, password);
                                System.out.println("电话修改成功!");
                                break;
                            }
                            case "0":{
                                break;
                            }
                        }
                        if (option1.equals("0")) {
                            break;
                        }
                    }
                    break;
                }
                case "3":{
                    System.out.println("请输入你的新密码");
                    String password = sc.nextLine();
                    String sql = "update  teacher set  password  = ? where root = ? and password = ? ";
                    jdbcTemplate.update(sql,password,root,password);
                    System.out.println("密码修改成功!");
                    break;
                }
                case "4":{
                    while (true) {
                        System.out.println("选课查询");
                        System.out.println("1.查看我的课程");
                        System.out.println("2.查看选课学生");
                        System.out.println("3.修改课程信息");
                        System.out.println("0.退出");
                        Scanner sc1 = new Scanner(System.in);
                        String option1 = sc.nextLine();
                        switch (option1) {
                            case "1": {
                                System.out.println("我的课程");
                                String sql = "select teacher.name,classtable.className from teacher join classtable on teacher.id = classtable.teacherid where teacher.root = ? and teacher.password = ?";
                                List<Map<String, Object>> List = jdbcTemplate.queryForList(sql, root, password);
                                for (Map<String,Object> map:List){
                                    map.values().stream().map(Object::toString).forEach(value -> System.out.print(value.replaceAll("[()]", "") + " "));
                                }
                                System.out.println("");
                                break;
                            }
                            case "2": {
                                System.out.println("选课学生");

                                String sql = "" +
                                        "select student.name ,student.class \n" +
                                        "from student\n" +
                                        " join associatetables \n" +
                                        "on associatetables .学生id = student.id\n" +
                                        "join classtable\n" +
                                        "on associatetables.课程id = classtable.id\n" +
                                        "join teacher\n" +
                                        "on classtable.teacherid = teacher.id\n" +
                                        "where teacher.root = ? and teacher.password = ?";
                                List<Map<String, Object>> List = jdbcTemplate.queryForList(sql, root, password);
                                for (Map<String,Object> map:List){
                                    map.values().stream().map(Object::toString).forEach(value -> System.out.print(value.replaceAll("[()]", "") + " "));
                                }
                                System.out.println("");
                                break;
                            }
                            case "3": {
                                while (true) {
                                    System.out.println("修改课程信息");
                                    System.out.println("选择你要修改的内容");
                                    System.out.println("1.修改课程名");
                                    System.out.println("0.退出");
                                    Scanner sc2 = new Scanner(System.in);
                                    String option2 = sc.nextLine();
                                    switch (option2) {
                                        case "1": {
                                            System.out.println("你的新课程名是?");
                                            String className = sc.nextLine();
                                            String sql = "update  classtable set className  = ? where teacherid = (select id from teacher where root = ? and password = ?) ";
                                            jdbcTemplate.update(sql, className, root, password);
                                            System.out.println("姓名修改成功!");
                                            break;
                                        }
                                        case "0":{
                                            break;
                                        }
                                    }
                                    if (option2.equals("0")) {
                                        break;
                                    }
                                }
                                break;
                            }

                        }
                        if (option1.equals("0")) {
                            break;
                        }
                    }
                    break;
                }

                case "0":{
                    break;
                }
            }
            if (option.equals("0")){
                break;
            }
        }
    }



    //验证学生登录-------------------------------------------
    private static boolean isloginStudnet(){
        System.out.println("学生登陆系统  请输入你的账号和密码：");
        while (true) {
            Scanner scanner = new Scanner(System.in);
            System.out.println("输入账号");
            root = scanner.nextLine();
            System.out.println("输入密码");
            password = scanner.nextLine();
//            //获取jdbcTemplate对象
//            JdbcTemplate jdbcTemplate = new JdbcTemplate(druidTool.getDataSource()); //默认就是private
            String sql = "select * from student where root = ? and password = ? ";
            //执行sql
            List<Map<String, Object>> islogin = jdbcTemplate.queryForList(sql, root, password);
            if (islogin.size() != 0) {
                System.out.println("登录成功");
                return true;
            } else {
                System.out.println("登录失败 请重新输入账号密码");
                return false;
            }
        }
    }


    private static void studentChoose(){
        while (true) {
            System.out.println("-------------------欢迎来到学生选课系统--------------------");
            System.out.println("1.个人信息查询");
            System.out.println("2.个人信息修改");
            System.out.println("3.账号密码修改");
            System.out.println("4.选课与退课");
            System.out.println("0.退出程序");

            Scanner sc = new Scanner(System.in);
            String option = sc.nextLine();
            switch (option){
                case "1":{
                    String sql = "select * from student where root = ? and password = ? ";
                    Map<String, Object> stringObjectMap = jdbcTemplate.queryForMap(sql, root, password);
                    stringObjectMap.values().stream().map(Object::toString).forEach(value -> System.out.print(value.replaceAll("[()]", "") + " "));
                    System.out.println(" ");
                    break;
                }
                case "2": {
                    while (true) {
                        System.out.println("选择你要修改的内容");
                        System.out.println("1.姓名");
                        System.out.println("2.性别");
                        System.out.println("3.班级");
                        System.out.println("0.退出");
                        Scanner sc1 = new Scanner(System.in);
                        String option1 = sc.nextLine();
                        switch (option1) {
                            case "1": {
                                System.out.println("你的新姓名是?");
                                String name = sc.nextLine();
                                String sql = "update  student set name  = ? where root = ? and password = ? ";
                                jdbcTemplate.update(sql, name, root, password);
                                System.out.println("姓名修改成功!");
                                break;
                            }
                            case "2": {
                                System.out.println("你的新性别是?");
                                String sex = sc.nextLine();
                                String sql = "update  student set  sex  = ? where root = ? and password = ? ";
                                jdbcTemplate.update(sql, sex, root, password);
                                System.out.println("性别修改成功!");
                                break;
                            }
                            case "3": {
                                System.out.println("你的新班级是?");
                                String class1 = sc.nextLine();
                                String sql = "update  student set  class  = ? where root = ? and password = ? ";
                                jdbcTemplate.update(sql, class1, root, password);
                                System.out.println("班级修改成功!");
                                break;
                            }

                        }
                        if (option1.equals("0")) {
                            break;
                        }
                    }

                }
                break;
                case "3":{
                    System.out.println("请输入你的新密码");
                    String password1 = sc.nextLine();
                    String sql = "update  student set  password  = ? where root = ? and password = ? ";
                    jdbcTemplate.update(sql,password1,root,password);
                    System.out.println("密码修改成功!");
                    break;
                }

                case "4":{

                    while (true) {
                        System.out.println("请选择你的课程操作");
                        System.out.println("1.查看选课情况");
                        System.out.println("2.选课");
                        System.out.println("3.退课");
                        System.out.println("0.退出");
                        Scanner sc1 = new Scanner(System.in);
                        String option2 = sc.nextLine();
                        switch (option2){
                            case "1":{
                                System.out.println("你的选课情况是");
                                String sql = " select student.name,classtable.className " +
                                        "from student join associatetables on student.id =associatetables.学生id "+
                                        "join classtable on classtable.id =associatetables.课程id  "+
                                        "where student.root = ? and student.password = ?";
                                List<Map<String, Object>> List = jdbcTemplate.queryForList(sql, root, password);
                                for (Map<String,Object> map:List){
                                    map.values().stream().map(Object::toString).forEach(value -> System.out.print(value.replaceAll("[()]", "") + " "));

                                }
                                System.out.println("");
                                break;
                            }
                            case "2":{
                                System.out.println("你要选择的课程id是?");
                                String classid = sc.nextLine();
                                String sql = "select id from student where root = ? and password = ?";
                                Map<String, Object> map = jdbcTemplate.queryForMap(sql, root, password);

                                String sql2 = "insert into associatetables values(null,?,?) ";
                                jdbcTemplate.update(sql2,map.get("id"),classid);

                                int i = Integer.parseInt(map.get("id").toString());
                                int i2 = Integer.parseInt(classid);
                                String sql1 = "select count(*) from associatetables where 学生id = " + i + " and 课程id = " + i2;
                                Long o = jdbcTemplate.queryForObject(sql1, Long.class);
                                int i3 = o.intValue(); // 将 Long 类型的结果转换为 int 类型
                                System.out.println(i3);
                                if (i3>=2){
                                    System.out.println("你选了重复的课 选课失败!");
                                }
                                String sql3 = "Delete from associatetables where 学生id = ? and 课程id = ? ";
                                jdbcTemplate.update(sql3,map.get("id"),classid);

                                String sql4 = "insert into associatetables values(null,?,?) ";
                                jdbcTemplate.update(sql4,map.get("id"),classid);
                                break;
                            }
                            case "3":{
                                System.out.println("输入你要退的课");
                                int exitClass= sc.nextInt();

                                String sql1 = "select id from student where root = ? and password = ?";
                                Map<String, Object> map = jdbcTemplate.queryForMap(sql1, root, password);
                                int i = Integer.parseInt(map.get("id").toString());

                                String sql = "Delete from associatetables where 学生id = ? and 课程id = ?  ";
                                jdbcTemplate.update(sql,exitClass,i);

                                System.out.println("退课成功!");
                                System.out.println(" ");
                                break;
                            }

                        }
                        if (option2.equals("0" )){
                            break;
                        }
                    }
                    break;
                }

                case "0":{
                    break;
                }
            }
            if (option.equals("0")){
                break;
            }
        }

    }






    //学生注册-------------------------------------------
    private static void enroll(){

    }
}
