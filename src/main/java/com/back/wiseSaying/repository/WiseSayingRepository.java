package com.back.wiseSaying.repository;


import com.back.wiseSaying.entity.WiseSaying;

import java.util.ArrayList;
import java.util.List;

public class WiseSayingRepository {

    private List<WiseSaying> wiseSayings = new ArrayList<>();
    private int lastId = 0;

    public WiseSaying save(WiseSaying wiseSaying) {
        if(wiseSaying.isNew()) {
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

    public List<WiseSaying> findListDesc(int page, int pageSize) {
        return wiseSayings.reversed()
                .stream()
                .skip((long)(page - 1)* pageSize)
                .limit(pageSize)
                .toList();
    }

    public List<WiseSaying> findByContentKeywordOrderByDesc(String kw, int page, int pageSize) {
        return wiseSayings.reversed()
                .stream()
                .filter(w -> w.getSaying().contains(kw))
                .skip((long)(page - 1)* pageSize)
                .limit(pageSize)
                .toList();
    }

    public List<WiseSaying> findByAuthorKeywordOrderByDesc(String kw, int page, int pageSize) {
        return wiseSayings.reversed()
                .stream()
                .filter(w -> w.getAuthor().contains(kw))
                .skip((long)(page - 1)* pageSize)
                .limit(pageSize)
                .toList();
    }
}