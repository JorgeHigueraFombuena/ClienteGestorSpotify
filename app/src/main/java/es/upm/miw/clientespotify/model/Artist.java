package es.upm.miw.clientespotify.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Jorge on 08/11/2016.
 */

public class Artist implements Parcelable {

    public final static String COL_NAME_ID = "_id";
    public final static String COL_NAME_ID_API = "id_api";
    public final static String COL_NAME_ARTIST_NAME = "artist_name";
    public final static String COL_NAME_IMAGE = "image";
    public final static String COL_NAME_POPULARITY = "popularity";
    public final static String COL_NAME_RATING = "rating";

    private int id;

    private String idApi;

    private String artistName;

    private String image;

    private int popularity;

    private double rating;

    public Artist(int id, String idApi, String artistName, String image, int popularity, double rating) {
        this.id = id;
        this.idApi = idApi;
        this.artistName = artistName;
        this.image = image;
        this.popularity = popularity;
        this.rating = rating;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIdApi() {
        return idApi;
    }

    public void setIdApi(String idApi) {
        this.idApi = idApi;
    }

    public String getArtistName() {
        return artistName;
    }

    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getPopularity() {
        return popularity;
    }

    public void setPopularity(int popularity) {
        this.popularity = popularity;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Artist artist = (Artist) o;

        if (id != artist.id) return false;
        if (popularity != artist.popularity) return false;
        if (Double.compare(artist.rating, rating) != 0) return false;
        if (idApi != null ? !idApi.equals(artist.idApi) : artist.idApi != null) return false;
        return artistName != null ? artistName.equals(artist.artistName) : artist.artistName == null;

    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = id;
        result = 31 * result + (idApi != null ? idApi.hashCode() : 0);
        result = 31 * result + (artistName != null ? artistName.hashCode() : 0);
        result = 31 * result + popularity;
        temp = Double.doubleToLongBits(rating);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(idApi);
        parcel.writeString(artistName);
        parcel.writeString(image);
        parcel.writeInt(popularity);
        parcel.writeDouble(rating);
    }

    protected Artist(Parcel in){
        this.id = in.readInt();
        this.idApi = in.readString();
        this.artistName = in.readString();
        this.image = in.readString();
        this.popularity = in.readInt();
        this.rating = in.readDouble();
    }

    public static final Creator<Artist> CREATOR = new Creator<Artist>(){
        @Override
        public Artist createFromParcel(Parcel parcel) {
            return new Artist(parcel);
        }

        @Override
        public Artist[] newArray(int i) {
            return new Artist[i];
        }
    };
}
