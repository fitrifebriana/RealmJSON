package id.or.codelabs.realmjson.util;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

/**
 * Created by FitriFebri on 8/31/2016.
 */
public class JSONParser {
    static JSONObject jObject = null;
    static String json = "";
    private static String url = "http://http://bukuapi.azurewebsites.net/api/buku/";


    public JSONParser() {

    }

    public JSONObject getJSONFromUrl(){
        BufferedReader reader = null;
        try{
           URL urlp = new URL(url);
            try {
                HttpURLConnection connection = (HttpURLConnection)urlp.openConnection();
                connection.setRequestMethod("GET");
                connection.setReadTimeout(10000);
                connection.connect();
                reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
                StringBuilder sBuilder = new StringBuilder();
                String line = null;
                while ((line = reader.readLine()) != null){
                    sBuilder.append(line);
                }
                json = sBuilder.toString();
            } finally {
                if (reader != null){
                    reader.close();
                }
            }
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            jObject = new JSONObject(json);
        }catch (JSONException e){
            Log.e("JSON Parser", "Error parsing data" + e.toString());
        }

        return jObject;
    }
}
