package innovationlab.cc.momart.db;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import java.io.Serializable;

@Entity(tableName = "Paint")
public class Paint implements Serializable {
    @PrimaryKey(autoGenerate = true)
    @NonNull
    private Integer paintId;
    private String image;
    private String name;
    private Integer artistId;

    public Paint(String image, String name, Integer artistId) {
        this.image = image;
        this.name = name;
        this.artistId = artistId;
    }


    public String getImage() {
        return image;
    }

    public String getName() {
        return name;
    }

    public Integer getArtistId() {
        return artistId;
    }


    @Override
    public String toString() {
        return "Paint{" +
                "paintId='" + paintId + '\'' +
                ", image='" + image + '\'' +
                ", name='" + name + '\'' +
                ", artistId=" + artistId +
                '}';
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setArtistId(Integer artistId) {
        this.artistId = artistId;
    }

    @NonNull
    public Integer getPaintId() {
        return paintId;
    }

    public void setPaintId(@NonNull Integer paintId) {
        this.paintId = paintId;
    }
}
