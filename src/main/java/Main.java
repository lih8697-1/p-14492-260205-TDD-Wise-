import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        lab2();
    }

    public static void lab2() {
        PrintStream ORIGINAL_OUT = System.out;

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(byteArrayOutputStream);

        System.setOut(printStream);

        System.out.println("Hi");

        System.setOut(ORIGINAL_OUT); // 모니터 출력 복원

        String result = byteArrayOutputStream.toString();

        if(result.equals("1번 명언이 등록되었습니다."))
            System.out.println(result);
    }

    public static void lab1() {
        Scanner scan = new Scanner("""
                등록
                과거에 집착하지 마라.
                이인희
                """);

        String str1 = scan.nextLine();
        String str2 = scan.nextLine();
        String str3 = scan.nextLine();

        System.out.println(str1);
        System.out.println(str2);
        System.out.println(str3);
    }
}
