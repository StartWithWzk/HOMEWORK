package model;

import view.Console;

import java.sql.*;

/**
 * Created by 黄伟烽 on 2017/7/14.
 */
public class OperateDateBase {

    private Connection conn;
    private PreparedStatement pstmt;
    private String sql;
    private String url;
    private String username;
    private String password;

    public OperateDateBase(){
        url = "jdbc:mysql://localhost:3306/tt" ;
        username = "root" ;
        password = "1234" ;
        try{
            Class.forName("com.mysql.jdbc.Driver");
            System.out.println("成功加载MySQL驱动程序");

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public int addStudent(Student student){
        int result = 0;
        sql = "insert into students(major,class,name,sex,number,chinese,maths,english) values(?,?,?,?,?,?,?,?)";
        try{
            doPstmt(sql);
            setValues(student);
            result = pstmt.executeUpdate();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            closeResource();
        }
        return result;
    }

    private void setValues(Student student) throws SQLException {
        pstmt.setString(1,student.getMajor());
        pstmt.setString(2,student.getClas());
        pstmt.setString(3,student.getName());
        pstmt.setString(4,student.getSex());
        pstmt.setString(5,student.getNumber());
        pstmt.setInt(6,student.getChinese());
        pstmt.setInt(7,student.getMaths());
        pstmt.setInt(8,student.getEnglish());
    }

    private void closeResource() {
        try{
            pstmt.close();
            conn.close();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    private void doPstmt(String sql) {
        try{
            doConn();
            pstmt = conn.prepareStatement(sql);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void doConn() {
        try{
            conn = DriverManager.getConnection(url, username, password);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public int deleteStudent(String number){
        int result = 0;
        doConn();
        sql = "delete from students where Number ='" + number + "'";
        doPstmt(sql);
        try{
            result = pstmt.executeUpdate();
        }catch (Exception e) {
            e.printStackTrace();
        }finally {
            closeResource();
        }
        return result;
    }

    public String queryStuSub(String number, String subject){
        ResultSet rs = null;
        sql = "SELECT NUMBER,NAME," + subject + " FROM STUDENTS WHERE NUMBER=" + "'" + number + "'";
        try{
            doPstmt(sql);
            rs = pstmt.executeQuery(sql);
            StringBuilder sb = new StringBuilder();
            while(rs.next()){
                for(int i = 1; i <=3; i++){
                    sb.append(rs.getString(i) + "    ");
                }
            }
            return sb.toString();

        }catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    public String queryStuAllInfo(String number){
        ResultSet rs = null;
        sql = "SELECT * FROM STUDENTS WHERE Number=" + "'" + number + "'";
        try{
            doPstmt(sql);
            rs = pstmt.executeQuery(sql);
            StringBuilder sb = new StringBuilder();
            while (rs.next()){
                for(int i = 1; i <= 8; i++)
                sb.append(rs.getString(i) + "    ");
            }
            return sb.toString();
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            closeResource();
        }
        return null;
    }

    public String sortBySub(String subject){
        ResultSet rs = null;
        sql = "SELECT * FROM STUDENTS ORDER BY " + subject +  " DESC";
        try{
            doPstmt(sql);
            rs = pstmt.executeQuery(sql);
            StringBuilder sb = new StringBuilder();
            while(rs.next()){
                for(int i = 1; i <= 8; i++){
                    sb.append(rs.getString(i) + "    ");
                }
                sb.append("\n");
            }
            return sb.toString();
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            closeResource();
        }
        return null;
    }

    public String queryByRange(String subject, int max, int min){
        ResultSet rs = null;
        //sql = "SELECT * FROM STUDENTS WHERE chinese>5 AND chinese<50";
        sql = "SELECT * FROM STUDENTS WHERE chinese BETWEEN " + min + " AND " + max;
        try{
            doPstmt(sql);
            rs = pstmt.executeQuery();
            StringBuilder sb = new StringBuilder();
            while(rs.next()){
                for(int i = 1; i <= 8; i++){
                    sb.append(rs.getString(i) + "    ");
                }
                sb.append("\n");
            }
            return sb.toString();

        }catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    public int updateStudent(Student student){
        int result = 0;
        sql = "update students set major = ?, class = ?, name = ?, sex = ?, number = ?, Chinese = ?, Maths= ? , English = ? where number = ?";
        try{
            doPstmt(sql);
            setValues(student);
            pstmt.setString(9, student.getNumber());
            result = pstmt.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            closeResource();
        }
        return result;
    }

    public int updateStuCol(String number, String column, String value){
        int result = 0;
        sql = "update students set " + column + " = ? where number = ?";
        try{
            doPstmt(sql);
            pstmt.setString(1, value);
            pstmt.setString(2, number);
            result = pstmt.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
        return result;
    }

}
