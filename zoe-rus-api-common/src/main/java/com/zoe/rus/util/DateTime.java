package com.zoe.rus.util;

/**
 * @auth lpw
 */
public interface DateTime {
    /**
     * 将字符串天数范围转化为数值天数范围。
     *
     * @param string 字符串天数范围
     * @return 数值天数范围。
     */
    int[] range(String string);
}
