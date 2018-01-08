
package model;

import com.google.gson.Gson;
import org.json.JSONArray;
import java.util.List;
import java.util.Map;

public class Json {
    public String ListToArrayJson(List<Map<String, String>> list){
        String json = new Gson().toJson(list);
        return json;
    }

    public JSONArray ArrayJsonToList(String parameter) {
        JSONArray list = new JSONArray(parameter);
        return list;
    }
}
