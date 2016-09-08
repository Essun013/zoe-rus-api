package com.zoe.rus.util;

import com.zoe.commons.ctrl.context.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

/**
 * @author lpw
 */
@Controller("rus.util.pagination")
public class PaginationImpl implements Pagination {
    @Autowired
    protected Request request;

    @Override
    public int getPageSize() {
        return request.getAsInt("pageSize");
    }

    @Override
    public int getPageNum() {
        return request.getAsInt("pageNum");
    }
}
