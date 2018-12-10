package innovationlab.cc.momart.view;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.Date;
import java.util.List;

import innovationlab.cc.momart.R;
import pl.aprilapps.easyphotopicker.DefaultCallback;
import pl.aprilapps.easyphotopicker.EasyImage;

public class CameraActivity extends AppCompatActivity {
    private static final Integer PEDIDO_CAMARA_PERMISO = 1;

    private FirebaseStorage storage = FirebaseStorage.getInstance();
    private ImageView imageView;

    private StorageReference referenciaRaiz;
    private StorageReference referenciaImagenes;
    private FirebaseUser currentUser;
    private FirebaseAuth mAuth;
    private StorageReference referenciaUsuario;
    private StorageReference referenciaImagen;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);
        mAuth = FirebaseAuth.getInstance();
        imageView = findViewById(R.id.imageview_picture);
        referenciaRaiz = storage.getReference();
        referenciaImagenes = referenciaRaiz.child("UserImages");
        currentUser = mAuth.getCurrentUser();
        referenciaUsuario = referenciaImagenes.child(currentUser.getUid());

        referenciaImagen = referenciaUsuario.child(new Date().getTime() + ".jpg");

        Button buttonUpload = findViewById(R.id.button_send_image);
        buttonUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                byte[] bytesImagen = convertirImagenABytes(imageView);
                UploadTask uploadTask = referenciaImagen.putBytes(bytesImagen);

                uploadTask.addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(CameraActivity.this, "Fail to Send Image", Toast.LENGTH_SHORT).show();

                    }
                });

                uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Toast.makeText(CameraActivity.this, "Imagen Enviada", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });


        Button takePicture = findViewById(R.id.button_camera_take_picture);
        takePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestCameraPermission();

            }
        });



    }


    //Método para convertir la imagen del imageview en byte array
    public byte[] convertirImagenABytes(ImageView imageView){
        imageView.setDrawingCacheEnabled(true);
        imageView.buildDrawingCache();
        Bitmap bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        return baos.toByteArray();
    }

    public void requestCameraPermission(){
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.CAMERA},
                PEDIDO_CAMARA_PERMISO);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == PEDIDO_CAMARA_PERMISO && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){

           // Toast.makeText(this, "Permiso concedido", Toast.LENGTH_SHORT).show();
            sacarFoto();
        }
    }

    public void sacarFoto(){ EasyImage.openCamera(this, 0);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        EasyImage.handleActivityResult(requestCode, resultCode, data, this, new DefaultCallback() {
            @Override
            public void onImagesPicked(@NonNull List<File> imageFiles, EasyImage.ImageSource source, int type) {

                File fotoSacada = imageFiles.get(0);
                //Para subir a firebase directamente
                //byte[] bytesDeImagen = fotoSacada.getPath().getBytes();

                //Mostrar en un imageview
                Bitmap myBitmap = BitmapFactory.decodeFile(fotoSacada.getAbsolutePath());

                imageView.setImageBitmap(myBitmap);

            }
        });
    }


}
