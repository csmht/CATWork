import java.util.InputMismatchException;
import java.util.Scanner;

public class Servlet {
    public static void main(String[] args) {
        int n;

        Scanner sc = new Scanner(System.in);
        boolean flag = true;
        while (flag) {

            try {
                Prin.Menu();
                n = sc.nextInt();
                if (n == 1) {
                flag = Student.StudentIn();
                } else if (n == 2) {
                flag = GuanLi.GuanLiIn();
                } else if (n == 3) {
                Student.StudentNew();
                } else if (n == 4) {
                return;
                } else {
                System.out.println("输入错误，请重新输入1：");
                }
            } catch (Exception e) {
                sc.nextLine();
                System.out.println("输入错误，请重新输入：");
                Prin.Stop();
                throw new RuntimeException(e);
            }
        }
        return  ;
    }
}
