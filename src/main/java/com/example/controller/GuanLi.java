package com.example.controller;

import com.example.view.Prin;

import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class GuanLi {


    public static boolean GuanLiIn(){
        Scanner sc = new Scanner(System.in);
        System.out.println("请输入管理员密码：");

        try {
            String mima = sc.nextLine();
            if(mima.equals("0")){
                System.out.println("登陆成功");
                Prin.Stop();
                return false;
            }else{
                System.out.println("登陆失败");
                Prin.Stop();
                return true;
            }
        } catch (Exception e) {
            System.out.println("登陆失败");
            Prin.Stop();
            return true;
        }
    }



    public static boolean GuanLiMenu(){

    Scanner sc = new Scanner(System.in);
    int n ;
    boolean flag = false;

    while(!flag){
        try{

        Prin.GuanLiNenu();
        n = sc.nextInt();
        if(n == 1){
            flag = GuanLi.GuanLisee();
        }else if(n == 2){
            flag = GuanLi.addDouctor();
        }else if(n == 3){
            flag = GuanLi.EditDouctor();
        }else if(n == 4){
            flag = GuanLi.deleteDouctor();
        }else if(n == 5){
            flag = GuanLi.addDouctorTime();
        }else if(n == 6){
            flag = dateDouctorTime();
        }else if(n == 7){
            flag = endDouctor();
        }else if(n == 8){
            flag = addStudent();
        }else if(n == 9){
            return false;
        }else {
            System.out.println("输入有误");
        }
    Prin.Stop();

        }catch(Exception e){
            sc.nextLine();
            e.printStackTrace();
        }
    }





    return false;
    }

    //1.管理员查看医生
    public static boolean GuanLisee() throws SQLException {
        ResultSet rs = JDBC.find("keshi");
        while(rs.next()){
            System.out.println("name:"+ rs.getString("douctor") +"     科室："+ rs.getString("name"));
        }
        return false;
    }

    //2.管理员添加医生
    public static boolean addDouctor() throws SQLException {
        System.out.println("请输入医生名称：");
        Scanner sc = new Scanner(System.in);
        String name = sc.nextLine();
        System.out.println("请输入医生科室：");
        String keshi = sc.nextLine();

        try {
            JDBC.add("douctor","name",name);
            JDBC.add("keshi","name",keshi,"douctor",name);
        } catch (SQLException e) {
            System.out.println("添加失败");
            throw new RuntimeException(e);
        }
        return false;
    }

    //3.修改医生
    public static boolean EditDouctor() throws SQLException {
        ResultSet rs = JDBC.find("douctor");
        while(rs.next()){
            System.out.println("name:"+ rs.getString("name"));
        }

        System.out.println("请输入要修改的医生名字：");
        Scanner sc = new Scanner(System.in);
        String name = sc.nextLine();

        List<String> sql;
        sql = new ArrayList<String>();
        sql.add("douctortime");
        sql.add("douctor.name");
        sql.add("douctortime.name");
        sql.add("keshi");
        sql.add("douctor.name");
        sql.add("keshi.douctor");
        rs = JDBC.find("douctor",sql,"douctor.name",name);
        while(rs.next()){
            if(rs.getString("douctortime.id") == null){
                System.out.println("没有可修改的信息");
                return true;
            }
            System.out.println("name:"+ rs.getString("douctor.name") +"     科室："+ rs.getString("keshi.name") +"    诊断时间："+rs.getInt("douctortime.id")+ "   " +rs.getString("douctortime.statime") + "---" + rs.getString("douctortime.endtime"));
        }int n = 0;
        boolean flag = false;
        while(!flag) {
            System.out.print("\033[H\033[2J");
            System.out.println("1.修改诊断时间");
            System.out.println("2.修改科室");
            System.out.println("3.return");
            try {
                n = sc.nextInt();
            } catch (Exception e) {
                System.out.println("输入错误，请重新输入");
                Prin.Stop();
                e.printStackTrace();
            }

            if (n == 1) {flag = true;
                System.out.println("请输入要修改的时间id：");
                int id = sc.nextInt();
                String[] who = {"id",String.valueOf(id)};
                System.out.println("请输入要修改后的开始时间：");
                sc.nextLine();
                String statime = sc.nextLine();
                System.out.println("请输入要修改后的结束时间：");
                String endtime = sc.nextLine();
                int i = JDBC.Edit("douctortime",who,"statime",statime,"endtime",endtime);
                if(i!=0){
                    System.out.println("修改成功");
                }else {
                    System.out.println("修改失败");
                }
            } else if (n == 2) {flag = true;
                System.out.println("请输入修改后的科室：");
                String newkeshi = sc.nextLine();
                String[] who = {"douctor",name};
                int i = JDBC.Edit("keshi",who,"keshi",newkeshi);
                if(i!=0){
                    System.out.println("修改成功");
                }else {
                    System.out.println("修改失败");
                }
            } else if (n == 3) {
                flag = true;

            }else{
                System.out.println("输入错误，请重新输入");
                Prin.Stop();
            }

        }
        return false;
    }

        //4.管理员删除医生
    public static boolean deleteDouctor() throws SQLException {
        ResultSet rs = JDBC.find("douctor");
        while(rs.next()){
            System.out.println("name:"+ rs.getString("name"));
        }
        System.out.println("请输入要删除的医生名字：");
        Scanner sc = new Scanner(System.in);
        String name = sc.nextLine();

        JDBC.delete("douctortime","name",name);
        JDBC.delete("keshi","douctor",name);
        JDBC.delete("studentdouctor","name",name);
        JDBC.delete("douctor","name",name);


        return false;
    }

    //5.添加医生时间
    public static boolean addDouctorTime() throws SQLException {
        ResultSet rs = JDBC.find("douctor");
        while(rs.next()){
            System.out.println("name:"+ rs.getString("name"));
        }
        System.out.println("请输入医生名字：");
        Scanner sc = new Scanner(System.in);
        String name = sc.nextLine();
        boolean flag = true;
        String starime = null;
        String endtime = null;
        while(flag) {
            System.out.println("请输入开始时间：(格式：08:00:00)");
            starime = sc.nextLine();
            System.out.println("请输入结束时间：(格式：08:00:00)");
            endtime = sc.nextLine();
            LocalTime onetime;
            LocalTime towtime;
            try {
                DateTimeFormatter formatterTIME = DateTimeFormatter.ofPattern("H:m:s");
                onetime = LocalTime.parse(starime, formatterTIME);
                towtime = LocalTime.parse(starime, formatterTIME);
                flag = false;
            } catch (Exception e) {
                System.out.println("格式错误,请从新输入");
            }
        }
        JDBC.add("douctortime","name",name,"statime",starime,"endtime",endtime);
        return false;
    }

    //6.删除医生时间
    public static boolean dateDouctorTime() throws SQLException {
        ResultSet rs = JDBC.find("douctor");
        while(rs.next()){
            System.out.println("name:"+ rs.getString("name"));
        }
        System.out.println("请输入要删除的医生名字");
        Scanner sc = new Scanner(System.in);
        String name = sc.nextLine();
        rs = JDBC.find("douctortime","name",name);
        while(rs.next()){
            System.out.println("name:"+ rs.getString("name")+"   id:"+rs.getInt("douctortime.id")+"  time:"+rs.getTime("douctortime.statime")+"--------"+rs.getTime("douctortime.endtime"));
        }
        System.out.println("请输入要删除的时间id");
        int id = sc.nextInt();
        JDBC.delete("studentdouctor","timeid",id+"");
        JDBC.delete("douctortime","id",id+"");
        return false;
    }


    public static boolean addStudent() throws SQLException {
        ResultSet rs = JDBC.find("student");
        boolean hf = true;
        while(rs.next()){
            hf = rs.getBoolean("hefa");
            System.out.println("学号："+ rs.getString("id") +"  name:"+ rs.getString("name")+"  身份：" + (rs.getBoolean("hefa")?"合法":"不合法"));
        }
        System.out.println("请输入要 授权/收回 的学号");
        Scanner sc = new Scanner(System.in);
        String id = sc.nextLine();
        try{
        int a = JDBC.Edit("student",new String[]{"id",id},"hefa",hf?"0":"1");
        if(a==0){
            System.out.println("修改失败");
        }else{
        System.out.println("修改成功");}
        }catch (Exception e){
        System.out.println("未知对象");
        }
        return false;
    }


    public static boolean endDouctor() throws SQLException {
        ResultSet rs = JDBC.find("student");
        while (rs.next()){
            System.out.println("学号："+ rs.getString("id") +"     名字："+rs.getString("name"));
        }
        System.out.println("输入要查看/修改的学号：");
        Scanner sc = new Scanner(System.in);
        String id = sc.nextLine();
        List<String> a = new ArrayList<>(Collections.emptyList());
        a.add("douctortime");
        a.add("studentdouctor.timeid");
        a.add("douctortime.id");
        rs = JDBC.find("studentdouctor",a,"studentdouctor.id",id);
        int i = 1;
        List<String> b = new ArrayList<>();
        while(rs.next()){
            System.out.println("id:" + i++ +"    医生："+ rs.getString("name") + "   时间：" + rs.getString("date"));
            b.add(rs.getString("timeid"));
        }
        String[] bArray = b.toArray(new String[0]);
        System.out.println("请输入已经完成的诊断id：");
        int n = 9999;
        while(n>i){
            try{
                n = sc.nextInt();
                if(n>i){
                    System.out.println("没有这个id");
                }
            }catch (Exception e) {
                System.out.println("输入错误.......");
            }
        }

        String c = b.get(n - 1);
        List<String> d = new ArrayList<>(Collections.emptyList());
        d.add("keshi");
        d.add("studentdouctor.name");
        d.add("keshi.douctor");
        rs = JDBC.find("studentdouctor",d,"timeid",c,"id",id);
        String Name = id+".txt";


        while(rs.next()){

            String txt ="科室： "+ rs.getString("keshi.name") +" 医生： "+rs.getString("name")+" 时间： "+rs.getString("date")+"\n";
            try (FileOutputStream fos = new FileOutputStream(Name, true)) {
                JDBC.delete("studentdouctor","timeid",rs.getString("studentdouctor.id"));
                byte[] bytes = txt.getBytes();
                fos.write(bytes);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }



        return false;
    }


}




