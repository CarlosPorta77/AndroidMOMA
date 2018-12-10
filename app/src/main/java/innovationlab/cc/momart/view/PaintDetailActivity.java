package innovationlab.cc.momart.view;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
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

import innovationlab.cc.momart.R;
import innovationlab.cc.momart.db.Artist;
import innovationlab.cc.momart.db.ArtistViewModel;
import innovationlab.cc.momart.db.Paint;



public class PaintDetailActivity extends AppCompatActivity {
    public static final String PAINT = "paint";
    private ImageView imageViewPicture;
    private TextView textViewName;
    private TextView textViewArtistName;
    private TextView textViewArtistNationality;
    private  TextView textViewArtistInfluencedby;

//    private FirebaseUser usuarioLogeado;
//    private FirebaseDatabase database;
    private FirebaseStorage storage;
    private StorageReference storageReference;

    private Artist artist;
    private ArtistViewModel mArtistViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paint_detail);

//        usuarioLogeado = FirebaseAuth.getInstance().getCurrentUser();
//        database = FirebaseDatabase.getInstance();
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        imageViewPicture = findViewById(R.id.imageview_paintdetail_picture);
        textViewName = findViewById(R.id.textview_paintdetail_name);
        textViewArtistName = findViewById(R.id.textview_paintdetail_artist_name);
        textViewArtistNationality = findViewById(R.id.textview_paintdetail_artist_nationality);
        textViewArtistInfluencedby = findViewById(R.id.textview_paintdetail_artist_influencedby);


        Intent unIntent = getIntent();
        Bundle unBundle = unIntent.getExtras();
        Paint receivedPaint = (Paint) unBundle.getSerializable(PAINT);
        textViewName.setText(receivedPaint.getName());
        Integer artistID = receivedPaint.getArtistId();
        loadImage(receivedPaint.getImage());
        //loadData(artistID);

        mArtistViewModel = ViewModelProviders.of(this).get(ArtistViewModel.class);
        loadArtist(artistID);
    }


    public void loadImage(String imageuri) {
        storageReference.child(imageuri).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Glide.with(PaintDetailActivity.this).load(uri).into(imageViewPicture);

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(PaintDetailActivity.this, "Image Load Fail", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void loadArtist(Integer artistID) {

        mArtistViewModel.getArtist(artistID).observe(this, new Observer<Artist>() {
            @Override
            public void onChanged(@Nullable final Artist artist) {
                textViewArtistName.setText(artist.getName());
                textViewArtistNationality.setText(artist.getNationality());
                textViewArtistInfluencedby.setText(artist.getInfluenced_by());

            }
        });
    }

//    public void refreshData() {
//        DatabaseReference databaseReference =  database.getReference("artists");
//
//        databaseReference.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                List<Artist> artistas = new ArrayList<>();
//                for (DataSnapshot child: dataSnapshot.getChildren()) {
//                    artist = child.getValue(Artist.class);
//                    artistas.add(artist);
//                    Log.d("FIREBASE", "Value is: " + artist.toString());
//                }
//                mArtistViewModel.insertArtists(artistas);
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//                Log.w("FIREBASE", "Failed to read value.", databaseError.toException());
//            }
//        });
//    }


//    public void loadData(Integer artistID) {
//        DatabaseReference databaseReference =  database.getReference("artists").child(artistID.toString());
//
//        databaseReference.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                artist = dataSnapshot.getValue(Artist.class);
//               // Log.d("FIREBASE", "Value is: " + artist.toString());
//                textViewArtistName.setText(artist.getName());
//                textViewArtistNationality.setText(artist.getNationality());
//                textViewArtistInfluencedby.setText(artist.getInfluenced_by());
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//                Log.w("FIREBASE", "Failed to read value.", databaseError.toException());
//            }
//        });
//    }

}
