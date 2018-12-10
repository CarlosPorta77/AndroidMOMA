package innovationlab.cc.momart.model.DAO;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MomaRetrofit {

    protected Retrofit retrofit;

    public MomaRetrofit(String baseUrl) {
        //Cliente HTTP que va a manejar las conexiones a internet
        OkHttpClient.Builder okHttpClient = new OkHttpClient.Builder();
        //Builder de Retrofit
        Retrofit.Builder builder = new Retrofit.Builder()
                //método para configurar la url base
                .baseUrl(baseUrl)
                //método para configurar la librería para parseo JSON (GSON)
                .addConverterFactory(GsonConverterFactory.create());

        //Construyo el retrofit con la información del builder y el cliente HTTP
        retrofit = builder.client(okHttpClient.build()).build();
    }

}
