package com.back.wiseSaying.repository;

import com.back.global.AppContext;
import com.back.wiseSaying.entity.WiseSaying;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class WiseSayingFileRepositoryTest {
    private WiseSayingFileRepository wiseSayingFileRepository;

    public WiseSayingFileRepositoryTest() {
        AppContext.init();
        wiseSayingFileRepository = AppContext.wiseSayingFileRepository;
    }

    @Test
    @DisplayName("명언 저장")
    void t1() {
        WiseSaying wiseSaying = new WiseSaying(0, "꿈을 지녀라. 그러면 어려운 현실을 이길 수 있다.", "괴테");

        wiseSayingFileRepository.save(wiseSaying);

        WiseSaying foundedWiseSaying = wiseSayingFileRepository.findByIdOrNull(1);

        assertThat(foundedWiseSaying).isEqualTo(wiseSaying);

    }
}
