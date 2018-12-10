package innovationlab.cc.momart.db;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import java.util.List;

public class PaintViewModel extends AndroidViewModel {

    private PaintRepository mRepository;

    private LiveData<List<Paint>> mAllPaints;

    public PaintViewModel (Application application) {
        super(application);
        mRepository = new PaintRepository(application);
        mAllPaints = mRepository.getAllPaints();
    }

    public LiveData<List<Paint>> getAllPaints() { return mAllPaints; }

    public void insert(Paint paint) { mRepository.insert(paint); }
    public void insertPaints(List<Paint> paints) { mRepository.insertPaints(paints); }
}
