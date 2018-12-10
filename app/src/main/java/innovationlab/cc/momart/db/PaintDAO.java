package innovationlab.cc.momart.db;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface PaintDAO {
    @Insert
    void insert(Paint paint);

    @Query("DELETE FROM Paint")
    void deleteAll();

    @Query("SELECT * from Paint ORDER BY PaintId ASC")
    LiveData<List<Paint>> getAllPaints();

    @Query("SELECT COUNT(*) FROM Paint WHERE paintID == :paintID")
    Integer checkIfExists(Integer paintID);
}
