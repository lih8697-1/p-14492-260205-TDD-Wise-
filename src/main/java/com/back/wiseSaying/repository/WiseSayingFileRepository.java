package com.back.wiseSaying.repository;

import com.back.global.AppConfig;
import com.back.standard.util.Util;
import com.back.wiseSaying.dto.PageDto;
import com.back.wiseSaying.entity.WiseSaying;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public class WiseSayingFileRepository implements WiseSayingRepository{

    public WiseSaying save(WiseSaying wiseSaying) {

        if (wiseSaying.isNew()) {

            increaseLastId();
            int lastId = getLastId();

            wiseSaying.setId(lastId);
            Map<String, Object> wiseSayingMap = wiseSaying.toMap();
            String jsonStr = Util.json.toString(wiseSayingMap);
            Util.file.set("%s/%d.json".formatted(getDbPath(), wiseSaying.getId()), jsonStr);

            return wiseSaying;
        }

        String jsonStr = Util.json.toString(wiseSaying.toMap());
        Util.file.set("%s/%d.json".formatted(getDbPath(), wiseSaying.getId()), jsonStr);

        return wiseSaying;
    }

    public boolean delete(WiseSaying wiseSaying1) {
        return Util.file.delete("%s/%d.json".formatted(getDbPath(), wiseSaying1.getId()));
    }

    private int getLastId() {
        return Util.file.getAsInt("%s/lastId.txt".formatted(getDbPath()), 0);
    }

    private void increaseLastId() {
        Util.file.set("%s/lastId.txt".formatted(getDbPath()), String.valueOf(getLastId() + 1));
    }

    public Optional<WiseSaying> findById(int id) {
        String jsonStr = Util.file.get("%s/%d.json".formatted(getDbPath(), id), "");
        if( jsonStr.isBlank()) {
            return Optional.empty();
        }

        Map<String, Object> map = Util.json.toMap(jsonStr);
        WiseSaying ws = WiseSaying.fromMap(map);

        return Optional.of(ws);

    }

    public void clear() {
        Util.file.delete(getDbPath());
    }

    private String getDbPath() {
        return AppConfig.getMode() + "/db/wiseSaying";
    }

    public PageDto findAll(int page, int pageSize) {
        List<WiseSaying> filteredContent = findAll().stream()
                .skip((page - 1) * pageSize)
                .limit(pageSize)
                .toList();
        int totalCount = findAll().size();
        return new PageDto(page, pageSize, totalCount, filteredContent);
    }

    public List<WiseSaying> findAll() {
        return Util.file.walkRegularFiles(getDbPath(), "^\\d+\\.json$")
                .map(path -> Util.file.get(path.toString(), ""))
                .map(Util.json::toMap)
                .map(WiseSaying::fromMap)
                .toList();
    }

    public PageDto findByContentContainingDesc(String kw, int page, int pageSize) {
        List<WiseSaying> filteredContent = findAll().reversed().stream()
                .filter(w -> w.getSaying().contains(kw))
                .toList();

        return pageOf(filteredContent, page, pageSize);
    }

    private PageDto pageOf(List<WiseSaying> filteredContent, int page, int pageSize) {
        int totalCount = filteredContent.size();

        List<WiseSaying> pagedFilteredContent = filteredContent
                .stream()
                .skip((page - 1) * pageSize)
                .limit(pageSize)
                .toList();

        return new PageDto(page, pageSize, totalCount, pagedFilteredContent);
    }

    public PageDto findByAuthorContainingDesc(String kw, int page, int pageSize) {
        List<WiseSaying> filteredContent = findAll().reversed().stream()
                .filter(w -> w.getAuthor().contains(kw))
                .toList();

        return pageOf(filteredContent, page, pageSize);
    }
}