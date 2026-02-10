package com.back.wiseSaying.repository;

import com.back.standard.util.Util;
import com.back.wiseSaying.dto.PageDto;
import com.back.wiseSaying.entity.WiseSaying;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class WiseSayingFileRepository {

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

    public void delete(WiseSaying wiseSaying1) {
        Util.file.delete("%s/%d.json".formatted(getDbPath(), wiseSaying1.getId()));
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

    public String getDbPath() {
        return "db/wiseSaying";
    }

    public List<WiseSaying> findAll() {
        return Util.file.walkRegularFiles(getDbPath(), "^\\d+\\.json$")
                .map(path -> Util.file.get(path.toString(), ""))
                .map(Util.json::toMap)
                .map(WiseSaying::fromMap)
                .toList();
    }

    public PageDto findByContentContainingDesc(String kw, int pageSize, int pageNo) {
        List<WiseSaying> filteredWiseSayings = findAll().stream()
                .filter(wiseSaying -> wiseSaying.getSaying().contains(kw))
                .sorted(Comparator.comparing(WiseSaying::getId).reversed())
                .toList();

        return pageOf(filteredWiseSayings, pageNo, pageSize);
    }

    private PageDto pageOf(List<WiseSaying> filteredContent, int pageNo, int pageSize) {

        List<WiseSaying> content = filteredContent.stream()
                .skip((pageNo-1) * pageSize)
                .limit(pageSize)
                .toList();

        int totalItems = filteredContent.size();
        return new PageDto(pageNo, pageSize, totalItems, content);
    }

    public PageDto findByAuthorContainingDesc(String kw, int pageSize, int pageNo) {
        List<WiseSaying> filteredWiseSayings = findAll().stream()
                .filter(wiseSaying -> wiseSaying.getAuthor().contains(kw))
                .sorted(Comparator.comparing(WiseSaying::getId).reversed())
                .toList();

        return pageOf(filteredWiseSayings, pageNo, pageSize);
    }

    public PageDto findByContentContainingOrAuthorContainingDesc(String kw, int pageSize, int pageNo) {
        List<WiseSaying> filteredWiseSayings = findAll().stream()
                .filter(wiseSaying -> wiseSaying.getAuthor().contains(kw) || wiseSaying.getSaying().contains(kw))
                .sorted(Comparator.comparing(WiseSaying::getId).reversed())
                .toList();

        return pageOf(filteredWiseSayings, pageNo, pageSize);
    }
}