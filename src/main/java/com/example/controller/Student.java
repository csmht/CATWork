package com.example.controller;

import com.example.view.Prin;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Student {

    private static String id;
    public static String getId() {
        return id;
    }

    public static Boolean StudentIn() throws SQLException {
        Scanner sc = new Scanner(System.in);
        try {
            System.out.println("请输入学号：");
            String id = sc.nextLine();
            System.out.println("请输入密码：");
            String mima = sc.nextLine();
            ResultSet res = JDBC.find("student","id",id);

            while (res.next()) {
                if (res.getString("mima").equals(mima)) {
                    if(!res.getBoolean("hefa")){
                        System.out.println("账号未授权");
                        return true;
                    }
                    Student.id = id;
                    System.out.println("登陆成功");
                    Prin.Stop();
                    return false;
                }
            }
            System.out.println("登陆失败");
            Prin.Stop();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("登陆失败");
            Prin.Stop();
            return true;
        }
    }

    public static Boolean StudentNew() throws SQLException {
        Scanner sc = new Scanner(System.in);
        System.out.println("请输入学号：");
        String id = sc.nextLine();
        System.out.println("请输入密码：");
        String mima1 = sc.nextLine();
        System.out.println("请再次输入密码：");
        String mima2 = sc.nextLine();

        if(!mima1.equals(mima2)) {
            System.out.println("两次密码不一致");
            Prin.Stop();
            return true;
        }
        System.out.println("请输入手机号：");
        String num = sc.nextLine();

        int i = JDBC.add("student","id",id,"mima",mima1,"num",num);
        if(i!=0){
            System.out.println("注册成功，请找管理员授权身份");
            Prin.Stop();
            return false;
        }else{
            System.out.println("注册失败");
            Prin.Stop();
            return true;
        }

    }

    public static Boolean StudentMenu() throws SQLException {
        Scanner sc = new Scanner(System.in);
        int n = 0;
        Prin.StudentMenu();
        try {
            n = sc.nextInt();
            System.out.print("\033[H\033[2J");
            if (n == 1) {
                ResultSet rs = JDBC.find("keshi");
                while(rs.next()){
                    String name = rs.getString("douctor");
                    String keshi = rs.getString("name");
                    System.out.println("科室：" + keshi + "医生：" + name);
                }
            } else if (n == 2) {
                System.out.println("请输入要挂号的科室：");
                String keshi = sc.nextLine();
                ResultSet rs = JDBC.find("keshi","name",keshi);
                while(rs.next()){
                    String name = rs.getString("douctor");
                    System.out.println("科室：" + keshi + "    医生：" + name);
                }
                System.out.println("请输入要挂号的医生：");
                String name = sc.nextLine();
                rs = JDBC.find("douctortime","name",name);
                while(rs.next()){
                    String statime = rs.getString("statime");
                    String endtime = rs.getString("endtime");
                    System.out.println("医生："+ name +"   开始时间："+ statime +"  结束时间" + endtime);
                }
                System.out.println("请输入要挂号的时间：");
                String statime = sc.nextLine();
                JDBC.add("studentdouctor","id",Student.id,"name",name,"statime",statime);
                System.out.println("挂号成功");
            } else if (n == 3) {

            } else if (n == 4) {

            } else if (n == 5) {
                return false;
            }
        }catch (Exception e) {
            e.printStackTrace();
        }


        return true;
    }
}
