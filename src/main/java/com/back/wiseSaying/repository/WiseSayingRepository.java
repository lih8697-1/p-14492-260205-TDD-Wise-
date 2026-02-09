package com.back.wiseSaying.repository;


import com.back.wiseSaying.dto.PageDto;
import com.back.wiseSaying.entity.WiseSaying;

import java.util.ArrayList;
import java.util.List;

public class WiseSayingRepository {

    private List<WiseSaying> wiseSayings = new ArrayList<>();
    private int lastId = 0;

    public WiseSaying save(WiseSaying wiseSaying) {
        if (wiseSaying.isNew()) {
            wiseSaying.setId(++lastId);
            wiseSayings.add(wiseSaying);
        }

        return wiseSaying;
    }

    public boolean delete(int id) {
        return wiseSayings.removeIf(wiseSaying -> wiseSaying.getId() == id);
    }

    public WiseSaying findByIdOrNull(int id) {

        return wiseSayings.stream()
                .filter(wiseSaying -> wiseSaying.getId() == id)
                .findFirst()
                .orElse(null);
    }

    public PageDto findListDesc(int page, int pageSize) {
        int totalCount = wiseSayings.size();

        List<WiseSaying> content = wiseSayings.reversed()
                .stream()
                .skip((page - 1) * pageSize)
                .limit(pageSize)
                .toList();

        return new PageDto(page, pageSize, totalCount, content);
    }

    public PageDto findByContentKeywordOrderByDesc(String kw, int page, int pageSize) {

        int totalCount = wiseSayings
                .stream()
                .filter(w -> w.getSaying().contains(kw))
                .toList()
                .size();

        List<WiseSaying> content = wiseSayings.reversed()
                .stream()
                .filter(w -> w.getSaying().contains(kw))
                .skip((page - 1) * pageSize)
                .limit(pageSize)
                .toList();

        return new PageDto(page, pageSize, totalCount, content);
    }

    public PageDto findByAuthorKeywordOrderByDesc(String kw, int page, int pageSize) {

        int totalCount = wiseSayings
                .stream()
                .filter(w -> w.getAuthor().contains(kw))
                .toList()
                .size();

        List<WiseSaying> content = wiseSayings.reversed()
                .stream()
                .filter(w -> w.getAuthor().contains(kw))
                .skip((page - 1) * pageSize)
                .limit(pageSize)
                .toList();

        return new PageDto(page, pageSize, totalCount, content);
    }
}