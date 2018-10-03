package nodebase.tech.akshaynexus;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Utility {

    private static List<JsonData> jsonData = new ArrayList<>();

    public static List<JsonData> jsonToList(MyRecyclerViewAdapter adapter, JSONArray json) throws JSONException {

    	for (int i = 0; i < json.length(); ++i) {
    	    jsonData.add(extractObject((JSONObject)json.get(i)));
        }
        adapter.notifyDataSetChanged();
        return jsonData;
    }

    private static JsonData extractObject(JSONObject object) throws JSONException {
        String pubkey = (String)object.get("txhash");
        String status = (String)object.get("status");
        String payeeAddr = (String)object.get("addr");
        return new JsonData(pubkey, status, payeeAddr);
    }

    public static List<JsonData> getJsonData() {
        return jsonData;
    }
}