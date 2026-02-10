package com.back.wiseSaying.repository;

import com.back.standard.util.Util;
import com.back.wiseSaying.entity.WiseSaying;

import java.util.Map;

public class WiseSayingFileRepository {

    public WiseSaying save(WiseSaying wiseSaying) {

        if (wiseSaying.isNew()) {

            increaseLastId();
            int lastId = getLastId();

            wiseSaying.setId(lastId);
            Map<String, Object> wiseSayingMap = wiseSaying.toMap();
            String jsonStr = Util.json.toString(wiseSayingMap);
            Util.file.set("db/wiseSaying/%d.json".formatted(wiseSaying.getId()), jsonStr);

        }

        return wiseSaying;
    }

    private int getLastId() {
        return Util.file.getAsInt("db/wiseSaying/lastId.txt", 0);
    }

    private void increaseLastId() {
        Util.file.set("db/wiseSaying/lastId.txt", String.valueOf(getLastId() + 1));
    }

    public WiseSaying findByIdOrNull(int id) {
        String jsonStr = Util.file.get("db/wiseSaying/%d.json".formatted(id), "");
        if( jsonStr.isBlank()) {
            return null;
        }

        Map<String, Object> map = Util.json.toMap(jsonStr);
        return WiseSaying.fromMap(map);
    }

    public void clear() {
        Util.file.delete("db/wiseSaying");
    }
}