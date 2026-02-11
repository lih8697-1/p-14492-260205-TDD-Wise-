package com.back.global;

import com.back.system.controller.SystemController;
import com.back.wiseSaying.controller.WiseSayingController;
import com.back.wiseSaying.repository.WiseSayingFileRepository;
import com.back.wiseSaying.repository.WiseSayingMemRepository;
import com.back.wiseSaying.repository.WiseSayingRepository;
import com.back.wiseSaying.service.WiseSayingService;

import java.util.Scanner;

public class AppContext {

    public static Scanner sc;
    public static SystemController systemController;
    public static WiseSayingController wiseSayingController;
    public static WiseSayingService wiseSayingService;
    public static WiseSayingMemRepository wiseSayingMemRepository;
    public static WiseSayingFileRepository wiseSayingFileRepository;
    public static WiseSayingRepository wiseSayingRepository;

    public static void init(Scanner _sc, boolean isFileMode) {
        AppContext.sc = _sc;
        AppContext.wiseSayingFileRepository = new WiseSayingFileRepository();
        AppContext.wiseSayingMemRepository = new WiseSayingMemRepository();
        AppContext.wiseSayingRepository = isFileMode ? wiseSayingFileRepository : wiseSayingMemRepository;
        AppContext.wiseSayingService = new WiseSayingService();
        AppContext.wiseSayingController = new WiseSayingController(sc);
        AppContext.systemController = new SystemController();
    }

    public static void init() {
        init(new Scanner(System.in), true);
    }
}