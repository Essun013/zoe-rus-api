package com.zoe.rus.geocoder;

import com.zoe.commons.util.Http;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * @author lpw
 */
@Service(GeocoderModel.NAME + ".service")
public class GeocoderServiceImpl implements GeocoderService {
    private static final String[] ADDRESS = {"http://apis.map.qq.com/ws/geocoder/v1/?location=", "&get_poi=0&key="};

    @Autowired
    protected Http http;
    @Value("${rus.geocoder.key:}")
    protected String key;

    @Override
    public JSONObject address(String lat, String lng) {
        JSONObject json = JSONObject.fromObject(http.get(new StringBuilder(ADDRESS[0]).append(lat).append(',').append(lng).append(ADDRESS[1]).append(key).toString(), null, ""));
        if (json == null || json.getInt("status") != 0)
            return new JSONObject();

        JSONObject object = new JSONObject();
        JSONObject result = json.getJSONObject("result");
        object.put("address", result.getString("address"));
        object.put("component", result.getJSONObject("address_component"));

        return object;
    }
}
