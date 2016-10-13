package com.zoe.rus.geocoder;

import net.sf.json.JSONObject;

/**
 * @author lpw
 */
public interface GeocoderService {
    JSONObject address(String lat, String lng);
}
