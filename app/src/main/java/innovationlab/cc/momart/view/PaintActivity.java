package innovationlab.cc.momart.view;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import innovationlab.cc.momart.R;
import innovationlab.cc.momart.controller.PaintController;
import innovationlab.cc.momart.db.Paint;
import innovationlab.cc.momart.db.PaintRepository;
import innovationlab.cc.momart.db.PaintViewModel;
import innovationlab.cc.momart.model.POJO.PaintContainer;
import innovationlab.cc.momart.utils.ResultListener;

public class PaintActivity extends AppCompatActivity implements PaintAdapter.ListenerAdapterPaint {
    private PaintAdapter paintAdapter;
  //  private PaintController paintController;
    private GridLayoutManager gridLayoutManager;
    private RecyclerView recyclerPaint;
    private PaintViewModel mPaintViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paint);
    //    paintController = new PaintController();
        recyclerPaint = findViewById(R.id.recyclerview_paint);

        gridLayoutManager = new GridLayoutManager(this,2);

        recyclerPaint.setLayoutManager(gridLayoutManager);
        paintAdapter = new PaintAdapter(this);
        mPaintViewModel = ViewModelProviders.of(this).get(PaintViewModel.class);

        mPaintViewModel.getAllPaints().observe(this, new Observer<List<Paint>>() {
            @Override
            public void onChanged(@Nullable final List<Paint> paints) {
                // Update the cached copy of the paints in the adapter.
                paintAdapter.setPaints(paints);
                recyclerPaint.setAdapter(paintAdapter);
            }
        });
       // loadPaints();
       // refreshData();

    }

    @Override
    public void paintSelected(Paint selectedPaint) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(PaintDetailActivity.PAINT, selectedPaint);
        Intent intent = new Intent(PaintActivity.this, PaintDetailActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);

    }

//    public void loadPaints(){
//        paintController.getPaints(new ResultListener<PaintContainer>() {
//            @Override
//            public void finish(PaintContainer result) {
//
//                paintAdapter.setPaints(result.getPaints());
//                recyclerPaint.setAdapter(paintAdapter);
//            }
//        });
//    }

    //Si tengo acceso a la API (internet) entonces refresco las obras en async
//       private void refreshData() {
//            paintController = new PaintController();
//
//               paintController.getPaints(new ResultListener<PaintContainer>() {
//                   @Override
//                   public void finish(PaintContainer result) {
//                       List<Paint> paintData = new ArrayList<>();
//                       for (innovationlab.cc.momart.model.POJO.Paint paint: result.getPaints()) {
//                           paintData.add(new Paint(paint.getImage(),paint.getName(), paint.getArtistId()));
//                       }
//                       mPaintViewModel.insertPaints(paintData);
//                   }
//               });
//           }

}
