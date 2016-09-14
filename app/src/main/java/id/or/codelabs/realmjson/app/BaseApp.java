package id.or.codelabs.realmjson.app;

import android.app.Application;
import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;

import id.or.codelabs.realmjson.model.Book;
import io.realm.DynamicRealm;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmMigration;
import io.realm.RealmResults;
import io.realm.RealmSchema;

/**
 * Created by FitriFebriana on 8/31/2016.
 */
public class BaseApp extends Application {
    JSONArray dataJson = null;
    private static final  String TAG_OBJ = "fixtures";
    private Realm realm;

    @Override
    public void onCreate() {
        super.onCreate();
        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder(this)
                .name("book.realm")
                .schemaVersion(2)
                .migration(new DataMigration())
                .build();

        Realm.setDefaultConfiguration(realmConfiguration);
        realm = Realm.getDefaultInstance();
        RealmResults<Book> results = realm.where(Book.class).findAll();
        if (results.size() == 0){
            new BooksHelper().execute();
        }
    }

    public class BooksHelper extends AsyncTask<String, String, JSONObject>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected JSONObject doInBackground(String... strings) {
            JSONObject jsonObject =loadJSONFromAsset("book.json");
            return jsonObject;
        }

        @Override
        protected void onPostExecute(JSONObject jsonObject) {
            try {
                dataJson = jsonObject.getJSONArray(TAG_OBJ);
                for (int i = 0; i < dataJson.length(); i++){
                    JSONObject object = dataJson.getJSONObject(i);
                    realm.beginTransaction();
                    Book book = realm.createObject(Book.class);
                    book.setKdBuku(object.getString("kdBuku"));
                    book.setJudulBuku(object.getString("judulBuku"));
                    book.setPengarang(object.getString("pengarang"));
                    realm.copyToRealm(book);
                    realm.commitTransaction();
                }
            } catch (JSONException e) {

            }
        }
    }

    public JSONObject loadJSONFromAsset(String data){
        try {
            InputStream is = getAssets().open(data);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            String a = new String(buffer, "UTF-8");
            JSONObject json = new JSONObject(a);

            return json;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } catch (JSONException e){
            e.printStackTrace();
            return null;
        }
    }

    private class DataMigration implements RealmMigration{

        @Override
        public void migrate(DynamicRealm realm, long oldVersion, long newVersion) {
            RealmSchema realmSchema = realm.getSchema();
            if (oldVersion == 0){
                realmSchema.create("Book")
                        .addField("kdBuku", String.class)
                        .addField("judulBuku", String.class)
                        .addField("pengarang", String.class);

                oldVersion++;
            }
        }
    }
}
