import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;

public class TestUtilTest {

    @Test
    @DisplayName("TestUtil.genScanner()")
    void t1() {
        Scanner scanner = TestUtil.genScanner("""
                등록
                현재를 사랑하라.
                작자미상
                """);

        String cmd = scanner.nextLine();
        String content = scanner.nextLine();
        String author = scanner.nextLine();

        assertThat(cmd).isEqualTo("등록");
        assertThat(content).isEqualTo("현재를 사랑하라.");
        assertThat(author).isEqualTo("작자미상");
    }

    @Test
    @DisplayName("TestUtil.setOutByteArray()")
    void t2() {
        ByteArrayOutputStream byteArrayOutputStream = TestUtil.setOutByteArray();

        System.out.println("안녕하세요.");

        String rst = byteArrayOutputStream.toString();

        assertThat(rst).isEqualTo("안녕하세요.");
    }

//    @Test
//    @DisplayName("등록")
//    void t1() {
//
//        final String out = TestUtil.run("""
//                등록
//                현재를 사랑하라.
//                작자미상
//                """);
//
//        assertThat(out)
//                .contains("명언 :")
//                .contains("작가 :")
//                .contains("1번 명언이 등록되었습니다.");
//    }
}