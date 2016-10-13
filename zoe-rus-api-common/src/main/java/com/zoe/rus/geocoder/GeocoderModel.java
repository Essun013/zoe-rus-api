package com.zoe.rus.geocoder;

import com.zoe.commons.dao.model.Jsonable;
import com.zoe.rus.model.ModelSupport;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author lpw
 */
@Component(GeocoderModel.NAME + ".model")
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class GeocoderModel extends ModelSupport {
    static final String NAME = "rus.geocoder";
}
