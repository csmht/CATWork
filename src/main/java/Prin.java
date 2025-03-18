import java.util.Scanner;

public class Prin {

    public static void Stop(){
        System.out.println("输入回车继续");
        Scanner scanner = new Scanner(System.in);
        scanner.nextLine();
    }

    public static void Menu(){
        System.out.print("\033[H\033[2J");
        System.out.println("欢迎使用学生挂号系统");
        System.out.println("1.学生登录");
        System.out.println("2.管理员登录");
        System.out.println("3.注册学生账号");
        System.out.println("4.return");
        System.out.print("请输入您的选择：");
    }

    public static void StudentMenu(){
        System.out.print("\033[H\033[2J");
        System.out.println("===学生菜单====");
        System.out.println("1.查看医生");
        System.out.println("2.挂号");
        System.out.println("3.取消挂号");
        System.out.println("4.查看历史挂号医生");
        System.out.println("5.退出");
        System.out.print("请输入您的选择：");
    }

    public static void GuanLiNenu() {
        System.out.print("\033[H\033[2J");
        System.out.println("===管理员菜单====");
        System.out.println("1.查看所有医生");
        System.out.println("2.添加医生");
        System.out.println("3.修改医生信息");
        System.out.println("4.删除医生");
        System.out.println("5.添加医生坐诊时间");
        System.out.println("6.删除医生坐诊时间");
        System.out.println("7.查看患者信息");
        System.out.println("8.授权学生身份");
        System.out.println("9.退出");
        System.out.print("请输入您的选择：");
    }
}
