package es.upm.miw.clientespotify.main;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import es.upm.miw.clientespotify.R;
import es.upm.miw.clientespotify.artist.ViewArtist;
import es.upm.miw.clientespotify.model.Artist;

import static android.R.id.list;

public class MainActivity extends Activity {

    public static Uri uriArtistas = Uri.parse("content://es.upm.miw.gestordespotify.model.bd.provider/artist");

    public final static String TAG = "MIWUPM";

    private ListView listView;

    private ArrayList<Artist> cachedList;

    private boolean firstTime = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ListView) findViewById(R.id.listView);
        listView.setFastScrollEnabled(true);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getApplicationContext(), ViewArtist.class);
                intent.putExtra(ViewArtist.TAG_BUNDLE,cachedList.get(i));
                startActivity(intent);
            }
        });

    }

    public void buscar(View v){
        ContentResolver cr = getContentResolver();
        String name = ((EditText)findViewById(R.id.text)).getText().toString();
        Cursor c = cr.query(uriArtistas, null, Artist.COL_NAME_ARTIST_NAME + " like '%" + name + "%'", new String[]{name}, null);

        cachedList = new ArrayList<>();

        if (c.moveToFirst()) {
            do {
                cachedList.add(new Artist(
                        c.getInt(c.getColumnIndex(Artist.COL_NAME_ID)),
                        c.getString(c.getColumnIndex(Artist.COL_NAME_ID_API)),
                        c.getString(c.getColumnIndex(Artist.COL_NAME_ARTIST_NAME)),
                        c.getString(c.getColumnIndex(Artist.COL_NAME_IMAGE)),
                        c.getInt(c.getColumnIndex(Artist.COL_NAME_POPULARITY)),
                        c.getDouble(c.getColumnIndex(Artist.COL_NAME_RATING))

                ));

            } while (c.moveToNext());
        }

        if(cachedList.isEmpty()){
            Toast.makeText(this, "Se están descargando los datos, pruebe en unos segundos", Toast.LENGTH_LONG).show();
        }

        MyAdapter myAdapter = new MyAdapter(
                getBaseContext(),
                R.layout.element,
                cachedList
        );
        listView.setAdapter(myAdapter);
        firstTime=false;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(!firstTime) {
            buscar(null);
        }
    }
}
