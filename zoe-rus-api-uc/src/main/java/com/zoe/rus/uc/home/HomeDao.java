package com.zoe.rus.uc.home;

/**
 * @author lpw
 */
interface HomeDao {
    HomeModel findById(String id);

    HomeModel findByCode(String code);

    void save(HomeModel home);
}
