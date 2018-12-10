package innovationlab.cc.momart.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.google.firebase.auth.FirebaseAuth;

import org.json.JSONException;
import org.json.JSONObject;

import innovationlab.cc.momart.R;
import innovationlab.cc.momart.db.RefreshData;

public class ArtActivity extends AppCompatActivity {
    private RefreshData refreshData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_art);

        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        getUserData(accessToken);

        //Si hay internet Intenta refrescar la data
        refreshData = new RefreshData(this);
        refreshData.refreshArtists();
        refreshData.refreshPaints();


        Button buttonLogOut = findViewById(R.id.button_logout);
        buttonLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                LoginManager.getInstance().logOut();
                goMain();
            }
        });

        Button buttonShowPaints = findViewById(R.id.button_show_paints);
        buttonShowPaints.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ArtActivity.this, PaintActivity.class);
                startActivity(intent);
            }
        });


        Button buttonTakePicture = findViewById(R.id.button_take_picture);
        buttonTakePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ArtActivity.this, CameraActivity.class);
                startActivity(intent);
            }
        });

        Button buttonChat = findViewById(R.id.button_chat);
        buttonChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ArtActivity.this, ChatActivity.class);
                startActivity(intent);
                //Toast.makeText(ArtActivity.this, "Aun no implementado", Toast.LENGTH_SHORT).show();
            }
        });



    }
    private void goMain() {
        Intent intent = new Intent(ArtActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    //Pedir datos del usuario de facebook
    public void getUserData(AccessToken accessToken){

        GraphRequest request = GraphRequest.newMeRequest(accessToken,
                new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        try {
                            TextView textViewUserInfo = findViewById(R.id.textview_user_info);
                            ImageView imageViewLogin = findViewById(R.id.imageview_user);

                            if (refreshData.isConnected()) {
                                String email = object.getString("email");
                                //String birthday = object.getString("birthday"); // 01/31/1980 format
                                String name = object.getString("name");
                                //String gender = object.getString("gender");
                                String picture = object.getJSONObject("picture").getJSONObject("data").getString("url");
                                textViewUserInfo.setText(name);
                                Glide.with(ArtActivity.this).load(picture).into(imageViewLogin);
                            } else {
                                textViewUserInfo.setText("Modo Offline");
                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });

        Bundle parameters = new Bundle();
        parameters.putString("fields", "id,name,email,picture.type(large)");
        request.setParameters(parameters);
        request.executeAsync();

    }


}
