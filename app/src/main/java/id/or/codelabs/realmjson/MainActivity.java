package id.or.codelabs.realmjson;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import id.or.codelabs.realmjson.adapter.BookAdapter;
import id.or.codelabs.realmjson.helper.JSONHelper;
import id.or.codelabs.realmjson.model.Book;
import id.or.codelabs.realmjson.util.SimpleDividerItemDecoration;
import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmResults;

public class MainActivity extends AppCompatActivity {

    private static String url = "http://bukuapi.azurewebsites.net/api/buku/";
    JSONArray dataJSON = null;
    private RealmList<Book> bookList = new RealmList<>();
    private RecyclerView recyclerView;
    private BookAdapter adapter;
    private Realm realm;
    private String books;
    View coordinatorLayoutView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = (RecyclerView) findViewById(R.id.rv_book);
        adapter = new BookAdapter(bookList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.addItemDecoration(new SimpleDividerItemDecoration(getResources()));
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
        coordinatorLayoutView = findViewById(R.id.snackbarPosition);
        realm = Realm.getDefaultInstance();
        firstLoad();

    }

    private void firstLoad() {
        bookList.clear();
        RealmResults<Book> result = realm.where(Book.class).findAll();
        if (result.size() == 0) {
            new Helper().execute();
        }else{
            bookList.addAll(result);
        }
        adapter.notifyDataSetChanged();
    }


    public class Helper extends AsyncTask<String, String, JSONObject> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected JSONObject doInBackground(String... strings) {
            JSONObject jsonObject = JSONHelper.loadJSONFromAsset(MainActivity.this, "book.json");
            return jsonObject;
        }

        @Override
        protected void onPostExecute(JSONObject jsonObject) {
            try {
                dataJSON = jsonObject.getJSONArray("result");
                for (int i = 0; i < dataJSON.length(); i++) {
                    JSONObject object = dataJSON.getJSONObject(i);
                    realm.beginTransaction();
                    Book book = realm.createObject(Book.class);
                    book.setKdBuku(object.getString("kdBuku"));
                    book.setJudulBuku(object.getString("judulBuku"));
                    book.setPengarang(object.getString("pengarang"));
                    realm.commitTransaction();
                }
                bookList.addAll(realm.where(Book.class).findAll());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }


}
