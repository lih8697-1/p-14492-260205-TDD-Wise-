package com.back.wiseSaying.service;

import com.back.global.AppContext;
import com.back.wiseSaying.entity.WiseSaying;
import com.back.wiseSaying.repository.WiseSayingRepository;

import java.util.List;
public class WiseSayingService {

    private WiseSayingRepository wiseSayingRepository;

    public WiseSayingService() {
        this.wiseSayingRepository = AppContext.wiseSayingRepository;
    }

    public WiseSaying write(String content, String author) {
        WiseSaying wiseSaying = new WiseSaying(0, content, author);
        wiseSayingRepository.save(wiseSaying);

        return wiseSaying;
    }

    public boolean delete(int id) {
        return wiseSayingRepository.delete(id);
    }

    public void modify(WiseSaying wiseSaying, String newSaying, String newAuthor) {

        wiseSaying.setSaying(newSaying);
        wiseSaying.setAuthor(newAuthor);

        wiseSayingRepository.save(wiseSaying);
    }

    public List<WiseSaying> findListDesc(String kw, String kwt, int page, int pageSize) {
        return switch (kwt) {
            case "content" -> wiseSayingRepository.findByContentKeywordOrderByDesc(kw, page, pageSize);
            case "author" -> wiseSayingRepository.findByAuthorKeywordOrderByDesc(kw, page, pageSize);
            default -> wiseSayingRepository.findListDesc(page, pageSize);
        };
    }

    public WiseSaying findByIdOrNull(int id) {
        return wiseSayingRepository.findByIdOrNull(id);
    }

}