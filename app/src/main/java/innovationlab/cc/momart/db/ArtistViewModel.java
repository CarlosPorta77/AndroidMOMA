package innovationlab.cc.momart.db;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import java.util.List;

public class ArtistViewModel extends AndroidViewModel {

    private ArtistRepository mRepository;

    private LiveData<List<Artist>> mAllArtists;

    public ArtistViewModel (Application application) {
        super(application);
        mRepository = new ArtistRepository(application);
        mAllArtists = mRepository.getAllArtists();
    }

    public LiveData<List<Artist>> getAllArtists() { return mAllArtists; }
    public LiveData<Artist> getArtist(Integer artistID) { return mRepository.getArtist(artistID); }

    public void insert(Artist artist) { mRepository.insert(artist); }
    public void insertArtists(List<Artist> artists) { mRepository.insertArtists(artists); }
}
