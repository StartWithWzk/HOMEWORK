package Model;

import java.math.BigInteger;
import java.net.SocketPermission;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by linzongzhan on 2017/7/15.
 */
public class JudgeString {

    public static void add (String name,String sex,String number,String major,String clas) {
        String sql;
        Statement statement = null;

        name = "'" + name + "'";
        sex = "'" + sex + "'";
        major = "'" + major + "'";
        clas = "'" + clas + "'";

        try {
            BigInteger biginteger = new BigInteger(number);
            sql = "INSERT INTO StudentMessage (name,sex,number,major,clas) VALUES (" + name + "," + sex + "," + biginteger + "," + major + ","  + clas + ")";
            statement = Query.query();
            int i = statement.executeUpdate(sql);
            if (i == 1) {
                System.out.println("添加学生成功");
            } else {
                System.out.println("添加学生失败");
            }
        } catch (SQLException sqle) {
            System.out.println("执行添加学生到数据库操作出错");
        } catch (NumberFormatException e) {
            System.out.println("输入学生学号格式错误");
        }

        try {
            statement.close();
        } catch (Exception e) {
            System.out.println("添加学生时关闭Statement出错");
        }
    }

    public static void delete (String nameORnumber) {
        String sql;
        Statement statement;

        nameORnumber = "'" + nameORnumber + "'";

        sql = "DELETE FROM StudentMessage WHERE name = " + nameORnumber;

        statement = Query.query();
        try {
            int i = statement.executeUpdate(sql);

            if (i > 0) {
                System.out.println("删除学生成功");
            } else {
                System.out.println("学生不存在");
            }
        } catch (Exception e) {
            System.out.println("执行将学生从数据库中删除操作出错");
        }

        try {
            statement.close();
        } catch (Exception e) {
            System.out.println("删除学生时关闭Statement出错");
        }

    }

    public static void change (String nameORnumber,String project,String result) {
        String sql;
        nameORnumber = "'" + nameORnumber + "'";

        if (project.equals("name") || project.equals("sex") || project.equals("major") || project.equals("clas")) {

            result = "'" + result + "'";

            sql = "UPDATE StudentMessage SET " + project + " = " + result + " WHERE name = " + nameORnumber;
            Statement statement = Query.query();
            try {
                int i = statement.executeUpdate(sql);
                if (i == 1) {
                    System.out.println("修改学生信息成功");
                } else {
                    System.out.println("修改学生信息失败");
                }
            } catch (Exception e) {
                System.out.println("修改学生信息时对数据库操作出错");
            }

            try {
                statement.close();
            } catch (Exception e) {
                System.out.println("修改学生信息时关闭Statement出错");
            }
        } else if (project.equals("chinese") || project.equals("math") || project.equals("english")) {
            Statement statement;
            statement = Query.query();
            try {
                Integer mark = Integer.parseInt(result);
                sql = "UPDATE StudentMessage SET " + project + " = " + mark + " WHERE name = " + nameORnumber;
                int i = statement.executeUpdate(sql);
                if (i == 1) {
                    System.out.println("修改学生成绩成功");
                } else {
                    System.out.println("修改学生成绩失败");
                }
            } catch (NumberFormatException nfe) {
                System.out.println("输入成绩格式错误");
            } catch (SQLException e) {
                System.out.println("修改学生成绩时对数据库的操作出错");
            }
            try {
                statement.close();
            } catch (Exception e) {
                System.out.println("修改成绩时关闭Statement出错");
            }

        } else if (project.equals("number")) {
            Statement statement;
            statement = Query.query();
            try {
                BigInteger bigInteger = new BigInteger(result);
                sql = "UPDATE StudentMessage SET " + project + " = " + bigInteger + " WHERE name " + nameORnumber;
                int i = statement.executeUpdate(sql);
            } catch (NumberFormatException nfe) {
                System.out.println("输入学号格式错误");
            } catch (SQLException e) {
                System.out.println("修改学生学号时对数据库的操作出错");
            }

            try {
                statement.close();
            } catch (Exception e) {
                System.out.println("修改学号时关闭Statement出错");
            }

        } else {
            System.out.println("你输入的修改项出错");
        }

    }

    public static void checkAllMessage (String nameORnumber) {
        Statement statement;
        String sql;

        nameORnumber = "'" + nameORnumber + "'";

        sql = "SELECT * FROM StudentMessage WHERE name = " + nameORnumber;
        statement = Query.query();
        try {
            int i = 0;
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                i = 1;
                String name = resultSet.getString("name");
                String sex = resultSet.getString("sex");
                BigInteger number = BigInteger.valueOf(resultSet.getLong("number"));
                String major = resultSet.getString("major");
                String clas = resultSet.getString("clas");
                System.out.println("姓名：" + name + "   性别：" + sex + "   学号：" + number + "   专业：" + major + "   班级：" + clas);
            }
            if (i == 0) {
                System.out.println("学生不存在");
            }
        } catch (Exception e) {
            System.out.println("查看学生全部信息时对数据库的操作出错");
            e.printStackTrace();
        }

        try {
            statement.close();
        } catch (SQLException sqle) {
            System.out.println("查看学生全部信息时关闭Statement出错");
        }

    }

    public static void checkChineseMark (String nameORnumber) {
        Statement statement;
        String sql;

        nameORnumber = "'" + nameORnumber + "'";

        sql = "SELECT name,chinese From StudentMessage WHERE name = " + nameORnumber;
        statement = Query.query();
        try {
            int i = 0;
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                i = 1;
                String name = resultSet.getString("name");
                int chinese = resultSet.getInt("chinese");
                System.out.println("姓名：" + name + "   语文成绩：" + chinese );
            }
            if (i == 1) {
                System.out.println("学生不存在");
            }
        } catch (Exception e) {
            System.out.println("查看语文成绩时对数据库的操作失败");
        }

        try {
            statement.close();
        } catch (SQLException sqle) {
            System.out.println("查看语文成绩时关闭Statement出错");
        }
    }

    public static void addChineseMark (String nameORnumber,String result) {
        Statement statement;
        String sql;
        nameORnumber = "'" + nameORnumber + "'";

        sql = "UPDATE StudentMessage SET chinese = " + result + " WHERE name = " + nameORnumber;
        statement = Query.query();

        try {
            int i = statement.executeUpdate(sql);
            if (i == 1) {
                System.out.println("添加学生语文成绩成功");
            } else {
                System.out.println("学生不存在");
            }
        } catch (SQLException e) {
            System.out.println("添加学生语文成绩时对数据库的操作出错");
            e.printStackTrace();
        }

        try {
            statement.close();
        } catch (SQLException sqle) {
            System.out.println("添加语文成绩时关闭Statement出错");
        }
    }

    public static void checkMathMark (String nameORnumber) {
        Statement statement;
        String sql;

        nameORnumber = "'" + nameORnumber + "'";

        sql = "SELECT name,math From StudentMessage WHERE name = " + nameORnumber;
        statement = Query.query();
        try {
            int i = 0;
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                i = 1;
                String name = resultSet.getString("name");
                int math = resultSet.getInt("math");
                System.out.println("姓名：" + name + "   数学成绩：" + math );
            }
            if (i == 1) {
                System.out.println("学生不存在");
            }
        } catch (Exception e) {
            System.out.println("查看数学成绩时对数据库的操作失败");
        }

        try {
            statement.close();
        } catch (SQLException sqle) {
            System.out.println("查看数学成绩时关闭Statement出错");
        }
    }

    public static void addMathMark (String nameORnumber,String result) {
        Statement statement;
        String sql;

        nameORnumber = "'" + nameORnumber + "'";

        //sql = "INSERT INTO StudentMessage (math) VALUES (" + result + ") WHERE name = " + nameORnumber;
        sql = "UPDATE StudentMessage SET math = " + result + " WHERE name = " + nameORnumber;
        statement = Query.query();

        try {
            int i = statement.executeUpdate(sql);
            if (i == 1) {
                System.out.println("添加学生数学成绩成功");
            } else {
                System.out.println("学生不存在");
            }
        } catch (SQLException e) {
            System.out.println("添加学生数学成绩时对数据库的操作出错");
            e.printStackTrace();
        }

        try {
            statement.close();
        } catch (SQLException sqle) {
            System.out.println("添加数学成绩时关闭Statement出错");
        }
    }

    public static void checkEnglishMark (String nameORnumber) {
        Statement statement;
        String sql;

        nameORnumber = "'" + nameORnumber + "'";

        sql = "SELECT name,english From StudentMessage WHERE name = " + nameORnumber;
        statement = Query.query();
        try {
            int i = 0;
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                i = 1;
                String name = resultSet.getString("name");
                int english = resultSet.getInt("english");
                System.out.println("姓名：" + name + "   英语成绩：" + english );
            }
            if (i == 1) {
                System.out.println("学生不存在");
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("查看英语成绩时对数据库的操作失败");
        }

        try {
            statement.close();
        } catch (SQLException sqle) {
            System.out.println("查看英语成绩时关闭Statement出错");
        }
    }

    public static void addEnglishMark (String nameORnumber,String result) {
        Statement statement;
        String sql;

        nameORnumber = "'" + nameORnumber + "'";

        sql = "UPDATE StudentMessage SET english = " + result + " WHERE name = " + nameORnumber;
        statement = Query.query();

        try {
            int i = statement.executeUpdate(sql);
            if (i == 1) {
                System.out.println("添加学生英语成绩成功");
            } else {
                System.out.println("学生不存在");
            }
        } catch (SQLException e) {
            System.out.println("添加学生英语成绩时对数据库的操作出错");
        }

        try {
            statement.close();
        } catch (SQLException sqle) {
            System.out.println("添加英语成绩时关闭Statement出错");
        }
    }

    public static void sort (String subject) {
        Statement statement;
        String sql;

        sql = "SELECT * FROM StudentMessage ORDER BY " + subject;

        statement = Query.query();
        try {
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                String name = resultSet.getString("name");
                String sex = resultSet.getString("sex");
                BigInteger number = BigInteger.valueOf(resultSet.getLong("number"));
                String major = resultSet.getString("major");
                String clas = resultSet.getString("clas");
                int chinese = resultSet.getInt("chinese");
                int math = resultSet.getInt("math");
                int english = resultSet.getInt("english");
                System.out.println("姓名：" + name + "   性别：" + sex +"   学号：" + number + "   专业：" + major + "   班级：" + clas + "   语文：" + chinese + "   数学：" + math + "   英语：" + english);
            }
        } catch (Exception e) {
            System.out.println("根据成绩排序时对数据库的操作出错");
        }

        try {
            statement.close();
        } catch (SQLException sqle) {
            System.out.println("根据成绩排序时关闭statement出错");
        }
    }

    public static void check (String Subject,String lower,String upper) {

        Integer low = new Integer(0);
        Integer up = new Integer(100);

        String sql;
        Statement statement;
        statement = Query.query();

        try {
            low = Integer.parseInt(lower);
        } catch  (NumberFormatException e){
            System.out.println("输入的下限成绩格式出错");
        }

        try {
            up = Integer.parseInt(upper);
        } catch (NumberFormatException e) {
            System.out.println("输入的上限成绩格式出错");
        }

        if (Subject.equals("chinese") || Subject.equals("math") || Subject.equals("english")) {
            try {
                sql = "SELECT * FROM StudentMessage WHERE " + Subject + " BETWEEN " + low + " AND " + up;
                ResultSet resultSet = statement.executeQuery(sql);
                while (resultSet.next()) {
                    String name = resultSet.getString("name");
                    String sex = resultSet.getString("sex");
                    BigInteger number = BigInteger.valueOf(resultSet.getLong("number"));
                    String major = resultSet.getString("major");
                    String clas = resultSet.getString("clas");
                    int chinese = resultSet.getInt("chinese");
                    int math = resultSet.getInt("math");
                    int english = resultSet.getInt("english");
                    System.out.println("姓名：" + name + "   性别：" + sex + "   学号：" + number + "   专业：" + major + "   班级：" + clas + "   语文：" + chinese + "   数学：" + math + "   英语：" + english);
                }
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("按成绩范围查找时对数据库的操作出错");
            }
        } else {
            System.out.println("输入的查找科目出错");
        }

        try {
            statement.close();
        } catch (SQLException e) {
            System.out.println("按成绩范围查找时关闭Statement出错");
        }

    }
}
