package nodebase.tech.akshaynexus;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class Utility {

    public static List<JsonData> jsonToList(JSONArray json) throws JSONException {
        List<JsonData> jsonData = new ArrayList<>();

    	for (int i = 0; i < json.length(); ++i) {
    	    jsonData.add(extractObject((JSONObject)json.get(i)));
        }
        return jsonData;
    }

    private static JsonData extractObject(JSONObject object) throws JSONException {
        String pubkey = (String)object.get("txhash");
        String status = (String)object.get("status");
        String payeeAddr = (String)object.get("addr");
        return new JsonData(pubkey, status, payeeAddr);
    }


    private static List<JsonData> _jsonToList_(JSONObject json) throws JSONException {
        List<JsonData> retMap = new ArrayList<>();

        if(json != JSONObject.NULL) {
            retMap = toList(json);
        }
        return retMap;
    }


    private static List<JsonData> toList(JSONObject object) throws JSONException {
        List<JsonData> map = new ArrayList<>();

        Iterator<String> keysItr = object.keys();
        while(keysItr.hasNext()) {
            String key = keysItr.next();
            Object value = object.get(key);

            if(value instanceof JSONArray) {
                value = toList((JSONArray) value);
            }

            else if(value instanceof JSONObject) {
                value = toList((JSONObject) value);
            }
        }
        return map;
    }


    public static List<Object> toList(JSONArray array) throws JSONException {
        List<Object> list = new ArrayList<Object>();
        for(int i = 0; i < array.length(); i++) {
            Object value = array.get(i);
            if(value instanceof JSONArray) {
                value = toList((JSONArray) value);
            }

            else if(value instanceof JSONObject) {
                value = toList((JSONObject) value);
            }
            list.add(value);
        }
        return list;
    }
}