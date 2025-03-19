package com.example.controller;

import java.sql.*;

public class JDBC {

        public static int add(String where,Object... values) throws SQLException {//列+内容
            Connection conn = Pool.getPool();
            Statement stmt = conn.createStatement();
            StringBuilder one = new StringBuilder("INSERT INTO `" + where + "` (");
            StringBuilder two = new StringBuilder(" VALUES (");
            for(int i=0;i<values.length;i=i+2) {
                String value = values[i].toString();
                if(i==values.length-2){
                    one.append(value).append(")");
                }else{

                    one.append(value).append(",");
                }
            }
            for(int i=1;i<values.length;i=i+2) {
                String value = null;
                Time time =null;
                if(values[i-1]=="time"){
                    time =new Time(((Date) values[i]).getTime());
                }else{
                    value = (String) values[i];
                }
                    if(i==values.length-1){
                        two.append("'").append(value == null ? time : value).append("')");
                    }else{
                    two.append("'").append(value == null ? time : value).append("',");
            }}
            String sql = one + two.toString();
            return stmt.executeUpdate(sql);
        }

        public static int delete(String where,String list,String id) throws SQLException {
            Connection conn = Pool.getPool();
            Statement stmt = conn.createStatement();
            String sql = "DELETE FROM " + where +" WHERE '"+ list +"'='"+ id + "'";
            return stmt.executeUpdate(sql);
        }


    public static int deleteDouctorTime(String Name,Time StaTime) throws SQLException {
        Connection conn = Pool.getPool();
        Statement stmt = conn.createStatement();
        return stmt.executeUpdate("DELETE FROM douctortime WHERE name = '"+ Name +"'AND statime ='"+ StaTime + "'");
        }

    public static int deleteStudentDouctor(String id,String Name) throws SQLException {
        Connection conn = Pool.getPool();
        Statement stmt = conn.createStatement();
        return stmt.executeUpdate("DELETE FROM studentdouctor WHERE id = '" + id + "'AND name='" + Name + "';");
        }

    public static ResultSet find(String where,Object...values) throws SQLException {
            Connection conn = Pool.getPool();
            ResultSet rs = null;
        StringBuilder sql = new StringBuilder("SELECT * FROM `").append(where);
            if(values.length>0){sql.append("` WHERE ")}
            for(int i=0;i<values.length;i+=2) {
                if(i>0){
                    sql.append(" AND ");

                }sql.append(values[i]).append(" = ?");}

        PreparedStatement pstmt = conn.prepareStatement(sql.toString());

                for(int i=0;i<values.length;i=i+2) {
                    Object value = values[i + 1];
                    int index = (i / 2) + 1;

                if(values[i] instanceof Date){
                    Time time = new Time(((Date) value).getTime());
                    pstmt.setTime(index,time);
                    }else {
                    pstmt.setObject(index,value.toString());
                    }
                }return pstmt.executeQuery();

    }
}
