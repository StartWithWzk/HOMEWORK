package Model;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.ResultSet;
import com.mysql.jdbc.Statement;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by 小吉哥哥 on 2017/7/14.
 */
public class SIMSmodel {
    //链接
    private Connection mCon;
    //连接状态监听器
    private ConnectListener mConnectListener = null;
    private GetStudentListener getStudentListener = null;

    public interface ConnectListener {
        void succeed();

        void failed();
    }

    public interface GetStudentListener {
        void onGet(List<Student> list);
    }

    //设置监听器
    public void setConnectListener(ConnectListener c) {
        mConnectListener = c;
    }

    public void setGetStudentListener(GetStudentListener getStudentListener) {
        this.getStudentListener = getStudentListener;
    }

    //初始化
    public SIMSmodel() {
    }

    //链接数据库
    public void connect(String url, String user, String password) {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            mCon = (Connection) DriverManager.getConnection(url, user, password);
            //连接成功
            if (mConnectListener != null) {
                mConnectListener.succeed();
            }
        } catch (Exception e) {
            //链接失败
            if (mConnectListener != null) {
                mConnectListener.failed();
            }
            e.printStackTrace();
        }
    }

    //得到所有学生
    public void getAllStudents() {
        List<Student> list = new ArrayList<>();
        try {
            Statement statement = (Statement) mCon.createStatement();
            ResultSet resultSet = (ResultSet) statement.executeQuery("SELECT * FROM information");
            while (resultSet.next()) {
                list.add(new Student(resultSet.getInt(1), resultSet.getString(2),
                        resultSet.getString(3), resultSet.getString(4), resultSet.getInt(5)));
            }
            if (getStudentListener != null) {
                getStudentListener.onGet(list);
            }

        } catch (SQLException e) {
            e.printStackTrace();

        }
    }

    //根据id或名字查找学生
    public void getStudent(Object o) {
        if (!(o instanceof Integer) && !(o instanceof String)) return ;
        try {
            PreparedStatement statement;
            if (o instanceof Integer) {
                statement = (PreparedStatement) mCon.prepareStatement("SELECT * FROM information WHERE id = ?");
                statement.setInt(1, (Integer) o);
            } else {
                statement = (PreparedStatement) mCon.prepareStatement("SELECT * FROM information WHERE name = ?");
                statement.setString(1, (String) o);
            }

            ResultSet resultSet = (ResultSet) statement.executeQuery();
            List<Student> list = new ArrayList<>();
            while (resultSet.next()) {
                list.add( new Student(resultSet.getInt(1), resultSet.getString(2),
                        resultSet.getString(3), resultSet.getString(4), resultSet.getInt(5)));
            }
            if (getStudentListener != null) {
                getStudentListener.onGet(list);
            }
        } catch (SQLException e) {
            e.printStackTrace();

        }
    }


    //修改学生信息
    public boolean setInfo(int id, String columnName, String to) {
        PreparedStatement preparedStatement;
        if (!columnName.equals("name") && !columnName.equals("sex") && !columnName.equals("major") && !columnName.equals("clas")) {
            if (!columnName.equals("math") && !columnName.equals("english") && !columnName.equals("chinese")) {
                return false;
            }
            try {
                preparedStatement = (PreparedStatement) mCon.prepareStatement("UPDATE result SET " + columnName + " = ? WHERE id = ?");
                preparedStatement.setInt(2, id);
                preparedStatement.setInt(1, Integer.parseInt(to));
                preparedStatement.execute();
                if (preparedStatement.getUpdateCount() == 1) {
                    return true;
                }
            } catch (SQLException e) {
                e.printStackTrace();
                return false;
            }
        }
        try {
            preparedStatement = (PreparedStatement) mCon.prepareStatement("UPDATE information SET " + columnName + " = ? WHERE id = ?");
            preparedStatement.setInt(2, id);
            if (columnName.equals("clas")) {
                preparedStatement.setInt(1, Integer.parseInt(to));
            } else {
                preparedStatement.setString(1, to);
            }
            preparedStatement.execute();
            if (preparedStatement.getUpdateCount() == 1) {
                return true;
            }
            return false;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }


    }

    //删除学生
    public boolean deleteStudent(int id) {
        try {
            PreparedStatement preparedStatement = (PreparedStatement) mCon.prepareStatement("delete from information where id = ?");
            preparedStatement.setInt(1, id);
            preparedStatement.execute();
            if (preparedStatement.getUpdateCount() == 1) {
                preparedStatement = (PreparedStatement) mCon.prepareStatement("delete from result where id = ?");
                preparedStatement.setInt(1, id);
                preparedStatement.execute();
                if (preparedStatement.getUpdateCount() == 1) {
                    return true;
                }
            }
            return false;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    //根据id或名字查找学生成绩
    public int getResult(Object o, String columnName) {
        if (!(o instanceof Integer) && !(o instanceof String)) return -1;
        if (!columnName.equals("chinese") && !columnName.equals("math") && !columnName.equals("english")) return -1;
        try {
            PreparedStatement statement;
            if (o instanceof Integer) {
                statement = (PreparedStatement) mCon.prepareStatement("SELECT " + columnName + " FROM result WHERE id = ?");
                statement.setInt(1, (Integer) o);
            } else {
                statement = (PreparedStatement) mCon.prepareStatement("SELECT " + columnName + " FROM result WHERE name = ?");
                statement.setString(1, (String) o);
            }

            ResultSet resultSet = (ResultSet) statement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getInt(columnName);
            }
            return -1;
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }

    //插入学生记录
    public boolean insertStudent(Student student) {
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = (PreparedStatement) mCon.prepareStatement("insert into information (name, sex, major, clas) values (?,?,?,?)");
            preparedStatement.setString(1, student.getName());
            preparedStatement.setString(2, student.getSex());
            preparedStatement.setString(3, student.getMajor());
            preparedStatement.setInt(4, student.getClas());
            if (preparedStatement.executeUpdate() == 1) {
                preparedStatement = (PreparedStatement) mCon.prepareStatement("select max(id) from information");
                ResultSet resultSet = (ResultSet) preparedStatement.executeQuery();
                if (resultSet.next()) {
                    student.setId(resultSet.getInt(1));
                }
                preparedStatement = (PreparedStatement) mCon.prepareStatement("insert into result (id, name) values (?,?)");
                preparedStatement.setInt(1, student.getId());
                preparedStatement.setString(2, student.getName());
                if (preparedStatement.executeUpdate() == 1) {
                    return true;
                }
            }
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    //筛选分数段
    public List<Student> getStudentsInResultRing(String columnName, int max, int low) {
        List<Student> list = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = (PreparedStatement) mCon.prepareStatement("select * from result where " + columnName + " between ? and ?");
            preparedStatement.setInt(1, max);
            preparedStatement.setInt(2, low);
            ResultSet resultSet = (ResultSet) preparedStatement.executeQuery();
            while (resultSet.next()) {
                Student student = new Student();
                student.setId(resultSet.getInt(1));
                student.setName(resultSet.getString(2));
                student.setChinese(resultSet.getInt(3));
                student.setMath(resultSet.getInt(4));
                student.setEnglish(resultSet.getInt(5));
                list.add(student);
            }
            return list;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    //降分排序
    public List<Student> orderByResultDesc(String columnName) {
        List<Student> list = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = (PreparedStatement) mCon.prepareStatement("select * from result order by " + columnName + " desc");
            ResultSet resultSet = (ResultSet) preparedStatement.executeQuery();
            while (resultSet.next()) {
                Student student = new Student();
                student.setId(resultSet.getInt(1));
                student.setName(resultSet.getString(2));
                student.setChinese(resultSet.getInt(3));
                student.setMath(resultSet.getInt(4));
                student.setEnglish(resultSet.getInt(5));
                list.add(student);
            }
            return list;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    //升分排序
    public List<Student> orderByResult(String columnName) {
        List<Student> list = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = (PreparedStatement) mCon.prepareStatement("select * from result order by " + columnName);
            ResultSet resultSet = (ResultSet) preparedStatement.executeQuery();
            while (resultSet.next()) {
                Student student = new Student();
                student.setId(resultSet.getInt(1));
                student.setName(resultSet.getString(2));
                student.setChinese(resultSet.getInt(3));
                student.setMath(resultSet.getInt(4));
                student.setEnglish(resultSet.getInt(5));
                list.add(student);
            }
            return list;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
