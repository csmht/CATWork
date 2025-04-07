package com.example.controller;

import java.sql.*;
import java.util.List;

public class JDBC {


        public static int add(String where,String... values) throws SQLException {//列+内容
            Connection conn = Pool.getPool();
            Statement stmt = conn.createStatement();
            StringBuilder one = new StringBuilder("INSERT INTO `" + where + "` (");
            StringBuilder two = new StringBuilder(" VALUES (");
            for(int i=0;i<values.length;i=i+2) {
                String value = values[i];
                if(i==values.length-2){
                    one.append(value).append(")");
                }else{

                    one.append(value).append(",");
                }
            }
            for(int i=1;i<values.length;i=i+2) {


                    if(i==values.length-1){
                        two.append("'").append(values[i]).append("')");
                    }else{
                    two.append("'").append(values[i]).append("',");
            }}
            String sql = one + two.toString();
            Pool.returnConn(conn);
            return stmt.executeUpdate(sql);
        }


        public static int delete(String where,String...list) throws SQLException {
            Connection conn = Pool.getPool();
            Statement stmt = conn.createStatement();
            String a = " ";
            for (int i = 0;i<list.length;i+=2){
                a +=" " + list[i] + " = '" + list[i+1] + "' ";
                if(i!=list.length-2){
                    a += " AND ";
                }
            }


            String sql = "DELETE FROM " + where +" WHERE " + a;
            Pool.returnConn(conn);
            return stmt.executeUpdate(sql);
        }



    public static int deleteDouctorTime(String Name,Time StaTime) throws SQLException {
        Connection conn = Pool.getPool();
        Statement stmt = conn.createStatement();
        Pool.returnConn(conn);
        return stmt.executeUpdate("DELETE FROM douctortime WHERE name = '"+ Name +"'AND statime ='"+ StaTime + "'");
        }


    public static int deleteStudentDouctor(String id,String Name) throws SQLException {
        Connection conn = Pool.getPool();
        Statement stmt = conn.createStatement();
        Pool.returnConn(conn);
        return stmt.executeUpdate("DELETE FROM studentdouctor WHERE id = '" + id + "'AND name='" + Name +"'");
        }


    public static ResultSet find(String where,Object...values) throws SQLException {
            Connection conn = Pool.getPool();
            ResultSet rs = null;
        StringBuilder sql = new StringBuilder("SELECT * FROM `").append(where).append("`");
            if(values.length>0){sql.append(" WHERE ");}
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
                }
        Pool.returnConn(conn);
                return pstmt.executeQuery();

    }


  public static ResultSet find(String main,List<String> where, String...values) throws SQLException {//where 链接表 主表链接列 连接表链接列
        Connection conn = Pool.getPool();
        ResultSet rs = null;
        StringBuilder sql = new StringBuilder("SELECT * FROM `" + main + "` ");
        for(int i=0;i<where.size();i+=3) {
            sql.append("LEFT JOIN ").append(where.get(i)).append(" ON ").append(where.get(i+1)).append("=").append(where.get(i+2)).append(" ");
        }
        if(values.length>0){sql.append(" WHERE ");}
        for(int i=0;i<values.length;i+=2) {
            if(i>0){
                sql.append(" AND ");
            }sql.append(values[i]).append(" = '").append(values[i+1]).append("'");}
        PreparedStatement pstmt = conn.prepareStatement(sql.toString());
        Pool.returnConn(conn);
        return pstmt.executeQuery();
    }



    public static int Edit(String where,String[] who,String...what) throws SQLException {
            Connection conn = Pool.getPool();
            ResultSet rs = null;
            StringBuilder sql = new StringBuilder("UPDATE `" + where + "` SET ");

            for(int i=0;i<what.length;i+=2) {
                sql.append(what[i]).append(" = '").append(what[i+1]).append("' ");
                if(i!=what.length-2){
                    sql.append(" , ");
                }
            }

            for(int i=0;i<who.length;i+=2) {
                if(i==0){
                    sql.append(" WHERE ");
                }
                sql.append(who[i]).append(" = ").append(who[i+1]);
                if(i!=who.length-2){
                    sql.append(" AND ");
                }
            }

        PreparedStatement pstmt = conn.prepareStatement(sql.toString());
            int count = pstmt.executeUpdate();
            Pool.returnConn(conn);

        return count;
    }

}
