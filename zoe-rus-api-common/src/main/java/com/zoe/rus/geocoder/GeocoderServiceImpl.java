package com.zoe.rus.geocoder;

import com.zoe.commons.dao.model.ModelHelper;
import com.zoe.commons.util.Http;
import com.zoe.rus.classify.ClassifyModel;
import com.zoe.rus.classify.ClassifyService;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author lpw
 */
@Service(GeocoderModel.NAME + ".service")
public class GeocoderServiceImpl implements GeocoderService {
    private static final String[] ADDRESS = {"http://apis.map.qq.com/ws/geocoder/v1/?location=", "&get_poi=0&key="};

    @Autowired
    protected Http http;
    @Autowired
    protected ModelHelper modelHelper;
    @Autowired
    protected ClassifyService classifyService;
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
        JSONObject component = result.getJSONObject("address_component");
        object.put("component", component);
        List<ClassifyModel> list = classifyService.find("region", new String[]{component.getString("nation"),
                component.getString("province"), component.getString("city"), component.getString("district")});
        object.put("region", list.isEmpty() ? new JSONObject() : modelHelper.toJson(list.get(0)));

        return object;
    }
}
