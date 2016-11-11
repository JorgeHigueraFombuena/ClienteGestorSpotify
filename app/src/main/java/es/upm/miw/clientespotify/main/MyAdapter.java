package es.upm.miw.clientespotify.main;

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
import es.upm.miw.clientespotify.model.Artist;


public class MyAdapter extends ArrayAdapter<Artist> {

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

        Artist item = (Artist) objects.get(position);

        if(!item.getImage().isEmpty()){
            Picasso.with(context).load(item.getImage()).into((ImageView) vista.findViewById(R.id.image));
        }
        else{
            Log.i(MainActivity.TAG,"Imágenes vacía");
        }
        ((TextView)vista.findViewById(R.id.artistName)).setText(item.getArtistName());

        ((RatingBar)vista.findViewById(R.id.ratingBar)).setRating((float) item.getRating());

        return vista;
    }
}
