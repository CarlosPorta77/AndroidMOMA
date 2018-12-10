package innovationlab.cc.momart.db;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public  interface ArtistDAO {

    @Insert
    void insert(Artist artist);

    @Query("DELETE FROM Artist")
    void deleteAll();

    @Query("SELECT * from Artist ORDER BY artistId ASC")
    LiveData<List<Artist>> getAllArtists();

    @Query("SELECT * FROM Artist WHERE artistID == :artistId")
    LiveData<Artist> getArtist(int artistId);

    @Query("SELECT COUNT(*) FROM Artist WHERE artistID == :artistID")
    Integer checkIfExists(Integer artistID);

}
