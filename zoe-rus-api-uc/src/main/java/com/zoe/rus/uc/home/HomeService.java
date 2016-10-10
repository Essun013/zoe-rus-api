package com.zoe.rus.uc.home;

/**
 * @author lpw
 */
public interface HomeService {
    String VALIDATOR_EXISTS = HomeModel.NAME + ".validator.exists";

    String VALIDATOR_EXISTS_CODE = HomeModel.NAME + ".validator.code.exists";

    String create();

    HomeModel get();

    HomeModel findById(String id);

    HomeModel findByCode(String code);
}
