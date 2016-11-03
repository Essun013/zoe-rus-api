package com.zoe.rus.uc.timeline;

import net.sf.json.JSONObject;

import java.util.Date;

/**
 * @author lpw
 */
public interface TimelineService {
    boolean create(Date lmp, Date childbirth, Date birthday, String region, String hospital);

    void modify(Date lmp, Date childbirth, Date birthday, String region, String hospital);

    void portrait(String portrait);

    TimelineModel get();

    JSONObject getPhysical();

    void done(String id);
}
