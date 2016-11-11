package es.upm.miw.clientespotify.artist;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import es.upm.miw.clientespotify.R;
import es.upm.miw.clientespotify.main.MainActivity;
import es.upm.miw.clientespotify.model.Album;


public class MyAdapter extends ArrayAdapter<Album> {

    private Context context;
    private int resourceId;
    private List objects;

    MyAdapter(Context context, int resource, List objects) {
        super(context, resource, objects);
        this.context = context;
        this.objects = objects;
        this.resourceId = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        RelativeLayout vista;
        if(convertView != null){
            vista = (RelativeLayout) convertView;
        }
        else {
            LayoutInflater inf = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            vista = (RelativeLayout) inf.inflate(resourceId, parent, false);
        }

        final Album album = (Album) objects.get(position);

        if(!album.getImage().isEmpty()){
            Picasso.with(context).load(album.getImage()).into((ImageView) vista.findViewById(R.id.image));
        }
        else{
            Log.i(MainActivity.TAG,"Imágenes vacía");
        }
        ((TextView)vista.findViewById(R.id.artistName)).setText(album.getAlbumName());

        RatingBar ratingBar = ((RatingBar)vista.findViewById(R.id.ratingBar));
        ratingBar.setRating((float) album.getRating());

        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {

            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                double value = (double) ratingBar.getRating();
                ContentResolver cr = context.getContentResolver();
                ContentValues c = new ContentValues();
                c.put(Album.COL_NAME_RATING, value);
                cr.update(ViewArtist.uriAlbums, c, Album.COL_NAME_ID + "=?", new String[]{String.valueOf(album.getId())});
                //Log.i(MainActivity.TAG, a + "");
            }
        });

        return vista;
    }
}
