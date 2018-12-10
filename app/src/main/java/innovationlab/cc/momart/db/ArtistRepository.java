package innovationlab.cc.momart.db;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.ArrayList;
import java.util.List;

public class ArtistRepository {

    private ArtistDAO mArtistDao;
    private LiveData<List<Artist>> mAllArtists;

    ArtistRepository(Application application) {
        MomaRoomDatabase db = MomaRoomDatabase.getDatabase(application);
        mArtistDao = db.artistDao();
        mAllArtists = mArtistDao.getAllArtists();
    }


    private Boolean checkIfExists(Integer ArtistId) {
        return mArtistDao.checkIfExists(ArtistId) > 0;
    }

    LiveData<List<Artist>> getAllArtists() {
        return mAllArtists;
    }

    public void insertArtists (List<Artist> artistList) {
        new ArtistRepository.insertAsyncTaskArtists(mArtistDao).execute(artistList);
    }

    public LiveData<Artist> getArtist(int artistId) {
        return mArtistDao.getArtist(artistId);
    }

    public void insert (Artist artist) {
        new insertAsyncTask(mArtistDao).execute(artist);
    }

    private static class insertAsyncTask extends AsyncTask<Artist, Void, Void> {

        private ArtistDAO mAsyncTaskDao;

        insertAsyncTask(ArtistDAO dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Artist... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }



    private static class insertAsyncTaskArtists extends AsyncTask<List<Artist>, Void, Void> {

        private ArtistDAO mAsyncTaskDao;

        insertAsyncTaskArtists(ArtistDAO dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final List<Artist>... params) {
            mAsyncTaskDao.deleteAll();
            for (Artist oneArtist:params[0]) {
                mAsyncTaskDao.insert(oneArtist);
            }

            return null;
        }
    }

}
