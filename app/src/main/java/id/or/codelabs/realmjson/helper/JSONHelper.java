package id.or.codelabs.realmjson.helper;

import android.app.Activity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by FitriFebriana on 9/1/2016.
 */
public class JSONHelper  {

    public JSONHelper() {

    }

    public static JSONObject loadJSONFromAsset(Activity act, String data) {
        try {
            InputStream is = act.getAssets().open(data);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            String a = new String(buffer, "UTF-8");
            JSONObject json = new JSONObject(a);
            return json;
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }

    }
}
