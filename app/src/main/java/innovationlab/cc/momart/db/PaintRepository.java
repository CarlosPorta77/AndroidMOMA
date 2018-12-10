package innovationlab.cc.momart.db;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Executor;

import innovationlab.cc.momart.controller.PaintController;
import innovationlab.cc.momart.model.POJO.PaintContainer;
import innovationlab.cc.momart.utils.ResultListener;

public class PaintRepository {

    private PaintDAO mPaintDao;
    private LiveData<List<Paint>> mAllPaints;
    private static int FRESH_TIMEOUT_IN_MINUTES = 60;

    PaintRepository(Application application) {
        MomaRoomDatabase db = MomaRoomDatabase.getDatabase(application);
        mPaintDao = db.paintDao();
        mAllPaints = mPaintDao.getAllPaints();
    }

    LiveData<List<Paint>> getAllPaints() {
        return mAllPaints;
    }

    public void insertPaints (List<Paint> paintList) {
        new insertAsyncTaskPaints(mPaintDao).execute(paintList);
    }
    public void insert (Paint paint) {
        new insertAsyncTask(mPaintDao).execute(paint);
    }



    private static class insertAsyncTask extends AsyncTask<Paint, Void, Void> {

        private PaintDAO mAsyncTaskDao;

        insertAsyncTask(PaintDAO dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Paint... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }


    private static class insertAsyncTaskPaints extends AsyncTask<List<Paint>, Void, Void> {

        private PaintDAO mAsyncTaskDao;

        insertAsyncTaskPaints(PaintDAO dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final List<Paint>... params) {
            mAsyncTaskDao.deleteAll();
            for (Paint onePaint:params[0]) {
                mAsyncTaskDao.insert(onePaint);
            }

            return null;
        }
    }
}

