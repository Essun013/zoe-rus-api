package com.zoe.rus.kb.knowledge;

import com.zoe.commons.scheduler.MinuteJob;
import org.springframework.stereotype.Service;

/**
 * @author lpw
 */
@Service(KnowledgeModel.NAME + ".service")
public class KnowledgeServiceImpl implements KnowledgeService, MinuteJob {
    @Override
    public void executeMinuteJob() {
    }
}
