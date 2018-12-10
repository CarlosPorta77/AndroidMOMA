package innovationlab.cc.momart.model.DAO;


import innovationlab.cc.momart.model.POJO.PaintContainer;
import innovationlab.cc.momart.utils.ResultListener;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MomaDAO extends MyRetrofit{
    public MomaService service;

    public MomaDAO() {
        super("https://api.myjson.com/");
        this.service = retrofit.create(MomaService.class);
    }

    public void getPaints(final ResultListener<PaintContainer> listener){
        Call<PaintContainer> call = service.getPaints();
        call.enqueue(new Callback<PaintContainer>() {
            @Override
            public void onResponse(Call<PaintContainer> call, Response<PaintContainer> response) {
                listener.finish(response.body());
            }

            @Override
            public void onFailure(Call<PaintContainer> call, Throwable t) {
                t.printStackTrace();

            }
        });
    }



}
