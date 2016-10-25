package com.zoe.rus.util;

import com.zoe.commons.util.Converter;
import com.zoe.commons.util.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @auth lpw
 */
@Component("rus.util.date-time")
public class DateTimeImpl implements DateTime {
    @Autowired
    protected Validator validator;
    @Autowired
    protected Converter converter;

    @Override
    public int[] range(String string) {
        if (validator.isEmpty(string))
            return new int[]{0, 0};

        String[] array = converter.toArray(string, "-");

        return new int[]{toDay(array[0], false), toDay(array[array.length - 1], true)};
    }

    protected int toDay(String string, boolean end) {
        int day = 0;
        char[] chars = string.toCharArray();
        char ch = 0;
        for (int i = 0; i < chars.length; i++) {
            ch = chars[i];
            if (ch >= '0' && ch <= '9')
                day = day * 10 + ch - '0';
            else if (ch == 'y')
                day *= 365;
            else if (ch == 'm')
                day = day * 30 + (day >> 1);
            else if (ch == 'w')
                day *= 7;
        }
        if (end) {
            if (ch == 'y')
                day += 365;
            else if (ch == 'm')
                day += 30;
            else if (ch == 'w')
                day += 7;
        }

        return day;
    }
}
