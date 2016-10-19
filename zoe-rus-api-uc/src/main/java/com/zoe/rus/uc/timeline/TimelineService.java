package com.zoe.rus.uc.timeline;

import java.util.Date;

/**
 * @author lpw
 */
public interface TimelineService {
    boolean create(Date lmp, Date childbirth, Date birthday);

    void portrait(String portrait);

    TimelineModel get();
}
