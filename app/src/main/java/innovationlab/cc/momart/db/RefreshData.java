package innovationlab.cc.momart.db;

import android.app.Activity;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

import innovationlab.cc.momart.controller.PaintController;
import innovationlab.cc.momart.model.POJO.PaintContainer;
import innovationlab.cc.momart.utils.ResultListener;

public class RefreshData {
    private FirebaseUser usuarioLogeado;
    private FirebaseDatabase database;
    private Artist artist;
    private ArtistViewModel mArtistViewModel;
    private FragmentActivity fragmentActivity;
    private PaintViewModel mPaintViewModel;
    private PaintController paintController;

    public RefreshData(FragmentActivity fragmentActivity) {
        this.fragmentActivity = fragmentActivity;
        init();
    }

    private void init() {
        usuarioLogeado = FirebaseAuth.getInstance().getCurrentUser();
        database = FirebaseDatabase.getInstance();
        mArtistViewModel = ViewModelProviders.of(fragmentActivity).get(ArtistViewModel.class);
        mPaintViewModel = ViewModelProviders.of(fragmentActivity).get(PaintViewModel.class);
    }
    public void refreshArtists() {
        if (isConnected()) {
        DatabaseReference databaseReference =  database.getReference("artists");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<Artist> artistas = new ArrayList<>();
                for (DataSnapshot child: dataSnapshot.getChildren()) {
                    artist = child.getValue(Artist.class);
                    artistas.add(artist);
                    Log.d("FIREBASE", "Value is: " + artist.toString());
                }
                mArtistViewModel.insertArtists(artistas);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.w("FIREBASE", "Failed to read value.", databaseError.toException());
            }
        });
    } }


    public void refreshPaints() {
        if (isConnected()) {
        paintController = new PaintController();

        paintController.getPaints(new ResultListener<PaintContainer>() {
            @Override
            public void finish(PaintContainer result) {
                List<Paint> paintData = new ArrayList<>();
                for (innovationlab.cc.momart.model.POJO.Paint paint: result.getPaints()) {
                    paintData.add(new Paint(paint.getImage(),paint.getName(), paint.getArtistId()));
                }
                mPaintViewModel.insertPaints(paintData);
            }
        });
        }

    }

     public Boolean isConnected() {
        ConnectivityManager connectivityManager = (ConnectivityManager) fragmentActivity.getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
         NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
         return networkInfo != null && networkInfo.isConnectedOrConnecting();
     }
}

