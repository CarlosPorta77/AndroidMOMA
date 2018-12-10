package innovationlab.cc.momart.view;

import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.List;

import innovationlab.cc.momart.R;
import innovationlab.cc.momart.db.Paint;

public class PaintAdapter extends RecyclerView.Adapter<PaintAdapter.PaintViewHolder> {
    private List<Paint> paints;
    private ListenerAdapterPaint listenerAdapterPaint;
    private FirebaseStorage storage;
    private StorageReference storageReference;

    public PaintAdapter(ListenerAdapterPaint listenerAdapterPaint) {
        this.listenerAdapterPaint = listenerAdapterPaint;
        this.storage = FirebaseStorage.getInstance();
        this.storageReference = storage.getReference();
    }

    @NonNull
    @Override
    public PaintViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.cell_paint, viewGroup, false);
        PaintViewHolder paintViewHolder = new PaintViewHolder(view);
        return paintViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull PaintViewHolder paintViewHolder, int i) {
        paintViewHolder.bindPaint(paints.get(i));
    }

    @Override
    public int getItemCount() {
        return paints.size();
    }

    public void setPaints(List<Paint> paints) {
        this.paints = paints;
        notifyDataSetChanged();
    }

    public class PaintViewHolder extends RecyclerView.ViewHolder{
        private ImageView imageViewPaint;
        private TextView textviewPaintName;

        public PaintViewHolder(View itemView) {
            super(itemView);
            imageViewPaint = itemView.findViewById(R.id.imageview_paint);
            textviewPaintName = itemView.findViewById(R.id.textview_paint_name);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Paint selectedPaint = paints.get(getAdapterPosition());
                    listenerAdapterPaint.paintSelected(selectedPaint);
                }
            });

        }

        public void bindPaint(Paint paint){
            textviewPaintName.setText(paint.getName());
            storageReference.child(paint.getImage()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    Glide.with(itemView).load(uri).into(imageViewPaint);

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                }
            });


        }

    }


    public interface ListenerAdapterPaint{
        public void paintSelected (Paint selectedPaint);
    }

}


