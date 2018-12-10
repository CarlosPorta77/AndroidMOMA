package innovationlab.cc.momart.db;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "Artist")
public class Artist {

    @PrimaryKey
    @NonNull
    private Integer artistId;
    private String Influenced_by;
    private String name;
    private String nationality;

    public Artist(String influenced_by, Integer artistId, String name, String nationality) {
        Influenced_by = influenced_by;
        this.artistId = artistId;
        this.name = name;
        this.nationality = nationality;
    }

    public Artist() {
    }

    public String getInfluenced_by() {
        return Influenced_by;
    }

    public Integer getArtistId() {
        return artistId;
    }

    public String getName() {
        return name;
    }

    public String getNationality() {
        return nationality;
    }

    @Override
    public String toString() {
        return "Artist{" +
                "Influenced_by='" + Influenced_by + '\'' +
                ", artistId=" + artistId.toString() +
                ", name='" + name + '\'' +
                ", nationality='" + nationality + '\'' +
                '}';
    }

    public void setArtistId(@NonNull Integer artistId) {
        this.artistId = artistId;
    }

    public void setInfluenced_by(String influenced_by) {
        Influenced_by = influenced_by;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }
}

