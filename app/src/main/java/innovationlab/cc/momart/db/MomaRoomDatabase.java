package innovationlab.cc.momart.db;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

import innovationlab.cc.momart.controller.PaintController;
import innovationlab.cc.momart.model.POJO.PaintContainer;
import innovationlab.cc.momart.utils.ResultListener;

@Database(entities = {Artist.class,Paint.class}, version = 1, exportSchema = false)
public abstract class MomaRoomDatabase extends RoomDatabase {

    public abstract ArtistDAO artistDao();
    public abstract PaintDAO paintDao();

    private static volatile MomaRoomDatabase INSTANCE;

    private static RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback(){
                @Override
                public void onOpen (@NonNull SupportSQLiteDatabase db){
                    super.onOpen(db);
                    new PopulateDbAsync(INSTANCE).execute();
                }
            };


    static MomaRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (MomaRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            MomaRoomDatabase.class, "moma_database")
                            .addCallback(sRoomDatabaseCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {

        private final PaintDAO mDao;

        PopulateDbAsync(MomaRoomDatabase db) {
            mDao = db.paintDao();
        }

        @Override
        protected Void doInBackground(final Void... params) {

        //    mDao.deleteAll();
        //    Paint paint = new Paint("1","artistpaints/michelangelo_perpetualmotion.png","Perpetual Motion",1);
         //   mDao.insert(paint
        //    paint = new Paint("2","artistpaints/michelangelo_perpetualmotion.png","Perpetual Motion",1);
         //   mDao.insert(paint);
            return null;
        }
    }
}

