package com.example.controller;

import com.example.view.Prin;

import java.io.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
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
            if (n == 1) {//看医生
                ResultSet rs = JDBC.find("keshi");
                boolean flag = true;
                while(rs.next()){
                    flag = false;
                    String name = rs.getString("douctor");
                    String keshi = rs.getString("name");
                    System.out.println("科室：" + keshi + "    医生：" + name);
                }
                if(flag){
                    System.out.println("没有科室信息");
                    Prin.Stop();
                }
            } else if (n == 2) {//挂号
                try{
                    sc.nextLine();
                System.out.println("请输入要挂号的科室：");
                String keshi = sc.nextLine();
                ResultSet rs = null;
                try {
                    rs = JDBC.find("keshi", "name", keshi);
                } catch (SQLException e) {
                    System.out.println("没有找到相关信息");
                    Prin.Stop();
                    return true;
                }
                while (rs.next()) {
                    String name = rs.getString("douctor");
                    System.out.println("科室：" + keshi + "    医生：" + name);
                }
                System.out.println("请输入要挂号的医生：");
                String name = sc.nextLine();
                try {
                    rs = JDBC.find("douctortime", "name", name);
                } catch (SQLException e) {
                    System.out.println("没有找到相关信息");
                    Prin.Stop();
                    return true;
                }
                List<LocalTime> rstime = new ArrayList<>();
                    List<Integer> idtime = new ArrayList<Integer>();
                int i = 0;
                int timeid = 0;
                while (rs.next()) {
                    Time statime = rs.getTime("statime");
                    Time endtime = rs.getTime("endtime");
                    int id = rs.getInt("id");
                    rstime.add(statime.toLocalTime());
                    rstime.add(endtime.toLocalTime());
                    idtime.add(id);
                    System.out.println("医生：" + name + "   开始时间：" + statime.toString() + "  结束时间" + endtime.toString());
                }
                boolean op = true;
                    String statime = "";
                    String[] time = new String[2];
                while (op) {//判断用户输入的时间是否正确

                    System.out.println("请输入要挂号的时间：格式为1111-01-01 01:01:01");
                    statime = sc.nextLine();

                    time = statime.split(" ");
                    DateTimeFormatter formatterTIME = DateTimeFormatter.ofPattern("H:m:s");
                    LocalTime usetime = LocalTime.parse(time[1], formatterTIME);

                    boolean pd = false;
                    for(int o = 0 ; o < rstime.size() ; o+=2) {
                        pd = usetime.isAfter(rstime.get(o)) && usetime.isBefore(rstime.get(o + 1));
                        if(pd){
                            timeid = idtime.get(o/2);
                            break;
                        }
                    }

                    if (!pd) {
                        System.out.println("没有这个时间：");
                        continue;
                    }op = false;
                }
                //处理时间字符串格式化
                String date = TimeTran.tran2(statime);
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
                    String retime = rstime.get(0).format(formatter);


                

                JDBC.add("studentdouctor", "id", Student.id, "name", name, "statime", retime ,"date",date,"timeid",String.valueOf(timeid));
            } catch (SQLException e) {
                    System.out.println("挂号失败");
                    Prin.Stop();
                    e.printStackTrace();
                    return true;
                }
                System.out.println("挂号成功");
            } else if (n == 3) {//取消挂号
                List<String> sql = new ArrayList<String>();
                sql.add("douctortime");
                sql.add("studentdouctor.name");
                sql.add("douctortime.name");
                ResultSet rs = JDBC.find("studentdouctor",sql,"id",Student.id);
                boolean pd = false;
                while(rs.next()){
                    pd = true;
                    String name = rs.getString("name");
                    String statime = rs.getString("date");

                    System.out.println("名字："+ name +"   开始时间："+ statime );
                }if(!pd){
                    System.out.println("无挂号信息");
                    return true;
                }
                sc.nextLine();
                System.out.println("请输入要取消挂号的医生");
                String name = sc.nextLine();


                int a = JDBC.deleteStudentDouctor(Student.id,name);
                if(a!=0){
                    System.out.println("修改成功");
                }else{
                    System.out.println("修改失败");
                }
            } else if (n == 4) {//查看挂号
                addStudent();
                return true;
            } else if (n == 5) {
                return false;
            }
        }catch (Exception | ClassFormatError e) {
            e.printStackTrace();
        }


        return true;
    }

    public static boolean addStudent() throws SQLException, FileNotFoundException {
        List<String> a = new ArrayList<>();
        a.add("keshi");a.add("studentdouctor.name");a.add("keshi.douctor");
        ResultSet rs = JDBC.find("studentdouctor",a,"id",Student.id);
        int n = 0;
        System.out.println("未完成:");
        while (rs.next()) {
            n++;
            String name = "科室：" + rs.getString("keshi.name")+"   医生："+rs.getString("douctor") + "  时间：" + rs.getString("Date");
            System.out.println(name);
        }

        System.out.println("历史：");
        String name = Student.id + ".txt";
        try(FileInputStream fis = new FileInputStream(name);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr)) {
            String line;
            while ((line = br.readLine()) != null) {


                System.out.println(line);
            }
        } catch (IOException e) {
            System.out.println("暂无历史挂号信息");
            System.out.println(e);
        }




        return true;
    }
}




