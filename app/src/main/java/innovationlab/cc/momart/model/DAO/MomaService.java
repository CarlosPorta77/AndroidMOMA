package innovationlab.cc.momart.model.DAO;

import innovationlab.cc.momart.model.POJO.PaintContainer;
import retrofit2.Call;
import retrofit2.http.GET;

public interface MomaService {

    @GET("/bins/x858r/")
    Call<PaintContainer> getPaints();

}
