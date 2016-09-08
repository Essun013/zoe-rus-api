package com.zoe.rus.kb.keyword;

import com.zoe.commons.cache.Cache;
import com.zoe.commons.util.Converter;
import net.sf.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

/**
 * @author lpw
 */
@Service(KeyWordModel.NAME + ".service")
public class KeyWordServiceImpl implements KeyWordService {
    private static final String CACHE_WORDS = KeyWordModel.NAME + ".service.words:";

    @Autowired
    protected Cache cache;
    @Autowired
    protected Converter converter;
    @Autowired
    protected KeyWordDao keyWordDao;

    @Override
    public JSONArray words() {
        String key = CACHE_WORDS + (System.currentTimeMillis() >> 18); // 约5分钟。
        JSONArray array = cache.get(key);
        if (array == null) {
            Set<String> set = new HashSet<>();
            keyWordDao.query().getList().forEach(kw -> set.add(kw.getWord()));
            cache.put(key, array = JSONArray.fromObject(set), false);
        }

        return array;
    }

    @Override
    public void save(String words, String knowledge) {
        keyWordDao.delete(knowledge);
        for (String word : converter.toArray(words, ",")) {
            KeyWordModel model = new KeyWordModel();
            model.setWord(word.trim());
            model.setKnowledge(knowledge);
            keyWordDao.save(model);
        }
    }
}
