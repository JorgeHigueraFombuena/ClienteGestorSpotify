package es.upm.miw.clientespotify.artist;

import android.app.Activity;
import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import es.upm.miw.clientespotify.R;
import es.upm.miw.clientespotify.main.MainActivity;
import es.upm.miw.clientespotify.model.Album;
import es.upm.miw.clientespotify.model.Artist;

import static android.R.id.list;

public class ViewArtist extends Activity {

    public static String TAG_BUNDLE = "artist";

    public static final Uri uriAlbums = Uri.parse("content://es.upm.miw.gestordespotify.model.bd.provider/album");

    private ListView listView;

    private ArrayList<Album> cachedList;

    private RatingBar ratingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_artist);

        final Artist artist =  getIntent().getParcelableExtra(TAG_BUNDLE);
        setTitle(artist.getArtistName());
        ((TextView)findViewById(R.id.artistNameArtist)).setText(artist.getArtistName());
        Picasso.with(getBaseContext()).load(artist.getImage()).into((ImageView) findViewById(R.id.imageArtist));

        listView = (ListView) findViewById(R.id.listViewAlbums);
        listView.setFastScrollEnabled(true);

        ratingBar = (RatingBar) findViewById(R.id.ratingBarArtist);
        ratingBar.setRating((float) artist.getRating());
        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {

            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                double value = (double) ratingBar.getRating();
                ContentResolver cr = getContentResolver();
                ContentValues c = new ContentValues();
                c.put(Album.COL_NAME_RATING, value);
                int a = cr.update(MainActivity.uriArtistas, c, Artist.COL_NAME_ID + "=?", new String[]{String.valueOf(artist.getId())});
                Log.i(MainActivity.TAG, a + "");
            }
        });


        ContentResolver cr = getContentResolver();
        Cursor c = cr.query(uriAlbums, null, Album.COL_NAME_ARTIST_ID + "=?", new String[]{String.valueOf(artist.getId())}, null);

        cachedList = new ArrayList<>();

        if (c.moveToFirst()) {
            do {
                String[] columnNames = c.getColumnNames();
                //Log.i("MITAG:", String.valueOf(c.getType(0)));
                //Log.i("MITAG:", String.valueOf(c.getColumnIndex(Artist.COL_NAME_ID)));
                cachedList.add(new Album(
                        c.getInt(c.getColumnIndex(Album.COL_NAME_ID)),
                        c.getString(c.getColumnIndex(Album.COL_NAME_ID_API)),
                        c.getInt(c.getColumnIndex(Album.COL_NAME_ARTIST_ID)),
                        c.getString(c.getColumnIndex(Album.COL_NAME_ALBUM_NAME)),
                        c.getString(c.getColumnIndex(Album.COL_NAME_IMAGE)),
                        c.getDouble(c.getColumnIndex(Album.COL_NAME_RATING))

                ));

            } while (c.moveToNext());
        }
        if(cachedList.isEmpty()){
            Toast.makeText(this, "Se están descargando los datos, pruebe en unos segundos", Toast.LENGTH_LONG).show();
        }

        MyAdapter myAdapter = new MyAdapter(
                getBaseContext(),
                R.layout.element_album,
                cachedList
        );
        listView.setAdapter(myAdapter);
    }





}
