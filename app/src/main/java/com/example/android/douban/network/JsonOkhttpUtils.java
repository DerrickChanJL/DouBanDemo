package com.example.android.douban.network;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * Created by Derrick on 2018/4/22.
 */

public class JsonOkhttpUtils {

    public static RequestBody getRequestBody(Map<String,Object> data){
        JSONObject result = new JSONObject();
        try {
            for (Map.Entry<String,Object> entry: data.entrySet())
            {
                //            result.put("phoneNumbers", "+86"+mBinding.phonenumberEditView.getText().toString());
                result.put(entry.getKey(),entry.getValue());
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestBody body = RequestBody.create(MediaType.parse("application/json"), result.toString());

        return body;
    }
}
