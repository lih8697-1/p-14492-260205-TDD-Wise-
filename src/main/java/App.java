import java.util.Scanner;

public class App {

    private Scanner sc;
    private WiseSayingController wiseSayingController;
    private SystemController systemController;

    public App(Scanner sc) {
        this.sc  = sc;
        this.wiseSayingController = new WiseSayingController(sc);
        this.systemController = new SystemController();
    }

    public void run() {


        System.out.println("== 명언 앱 ==");

        while (true){
            System.out.println("명령) ");
            String cmd = sc.nextLine();

            switch (cmd) {
                case "등록" -> wiseSayingController.actionAdd();
                case "목록" -> wiseSayingController.actionList();
                case "종료" -> {
                    systemController.actionExit();
                    return;
                }
            }
        }
    }
}