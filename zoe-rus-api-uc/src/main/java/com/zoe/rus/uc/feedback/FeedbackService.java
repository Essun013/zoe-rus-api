package com.zoe.rus.uc.feedback;

import net.sf.json.JSONObject;

/**
 * @author lpw
 */
public interface FeedbackService {
    void save(String content);

    JSONObject query();
}
