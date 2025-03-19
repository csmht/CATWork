package com.example.controller;

import com.example.view.Prin;

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




}
