package com.zoe.rus.geocoder;

import com.zoe.commons.dao.model.TephraModelSupport;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * @author lpw
 */
@Component(GeocoderModel.NAME + ".model")
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class GeocoderModel extends TephraModelSupport {
    static final String NAME = "rus.geocoder";
}
