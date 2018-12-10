package innovationlab.cc.momart.model.POJO;

import java.io.Serializable;

public class Paint implements Serializable {
    private String image;
    private String name;
    private Integer artistId;

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
                "image='" + image + '\'' +
                ", name='" + name + '\'' +
                ", artistId=" + artistId +
                '}';
    }
}
