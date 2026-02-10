package com.back.wiseSaying.repository;


import com.back.wiseSaying.dto.PageDto;
import com.back.wiseSaying.entity.WiseSaying;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class WiseSayingMemRepository implements WiseSayingRepository {

    private List<WiseSaying> wiseSayings = new ArrayList<>();
    private int lastId = 0;

    public WiseSaying save(WiseSaying wiseSaying) {
        if (wiseSaying.isNew()) {
            wiseSaying.setId(++lastId);
            wiseSayings.add(wiseSaying);
        }

        return wiseSaying;
    }

    public boolean delete(WiseSaying wiseSaying) {
        return wiseSayings.removeIf(w -> w.getId() == wiseSaying.getId());
    }

    public Optional<WiseSaying> findById(int id) {

        return wiseSayings.stream()
                .filter(wiseSaying -> wiseSaying.getId() == id)
                .findFirst();
    }

    public PageDto findAll(int page, int pageSize) {
        return pageOf(wiseSayings, page, pageSize);
    }

    public PageDto findByContentContainingDesc(String kw, int page, int pageSize) {

        List<WiseSaying> filteredContent = wiseSayings.reversed()
                .stream()
                .filter(w -> w.getSaying().contains(kw))
                .toList();

        return pageOf(filteredContent, page, pageSize);
    }

    public PageDto findByAuthorContainingDesc(String kw, int page, int pageSize) {

        List<WiseSaying> filteredContent = wiseSayings.reversed()
                .stream()
                .filter(w -> w.getAuthor().contains(kw))
                .toList();

        return pageOf(filteredContent, page, pageSize);
    }

    private PageDto pageOf(List<WiseSaying> filteredContent, int page, int pageSize) {
        int totalCount = filteredContent.size();

        List<WiseSaying> pagedFilteredContent = filteredContent.reversed()
                .stream()
                .skip((page - 1) * pageSize)
                .limit(pageSize)
                .toList();

        return new PageDto(page, pageSize, totalCount, pagedFilteredContent);
    }
}