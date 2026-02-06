package test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Scanner;

public class TestUtil {

    private static PrintStream ORIGINAL_OUT = System.out;
    private static PrintStream CURRENT_OUT = System.out;

    public static Scanner genScanner(String input) {
        return new Scanner(input);
    }

    public static ByteArrayOutputStream setOutByteArray() {

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(byteArrayOutputStream);

        System.setOut(printStream);
        CURRENT_OUT = printStream;

        return byteArrayOutputStream;
    }

    public static void clearSetOutToByteArray(ByteArrayOutputStream outputStream) throws Exception {
        System.setOut(ORIGINAL_OUT);
        outputStream.close();
        CURRENT_OUT.close();
    }
}