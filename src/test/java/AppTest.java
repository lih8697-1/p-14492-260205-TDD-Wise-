import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class AppTest {

    @Test
    @DisplayName("'== 명언 앱 ==' 출력")
    void t1() throws Exception {
        String out = AppTestRunner.run("");

        assertThat(out).contains("== 명언 앱 ==");

    }

    @Test
    @DisplayName("'등록")
    void t2() throws Exception {
        String out = AppTestRunner.run("""
                        등록
                        현재를 사랑하라.
                        작자미상
                        """);

        assertThat(out).contains("명령) ");
        assertThat(out).contains("명언 : ");
        assertThat(out).contains("작가 : ");
    }

    @Test
    @DisplayName("'등록")
    void t3() throws Exception {
        String out = AppTestRunner.run("""
                        등록
                        현재를 사랑하라.
                        작자미상
                        """);

        assertThat(out).contains("1번 명언이 등록되었습니다.");
    }

    @Test
    @DisplayName("등록할 때마다 생성되는 명언 번호가 증가")
    void t4() {
        String out = AppTestRunner.run("""
                등록
                현재를 사랑하라.
                작자미상
                등록
                현재를 사랑하라.
                작자미상
                """);

        assertThat(out).contains("2번 명언이 등록되었습니다.");
    }

    @Test
    @DisplayName("목록")
    void t5() {
        String out = AppTestRunner.run("""
                등록
                현재를 사랑하라.
                작자미상
                등록
                과거에 집착하지 마라.
                작자미상
                목록
                """);

        assertThat(out)
                .contains("번호 / 작가 / 명언")
                .contains("----------------------")
                .containsSubsequence("2 / 작자미상 / 과거에 집착하지 마라.",
                        "1 / 작자미상 / 현재를 사랑하라.");
    }


}