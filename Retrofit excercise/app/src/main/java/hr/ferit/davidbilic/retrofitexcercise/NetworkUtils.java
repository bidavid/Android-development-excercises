package hr.ferit.davidbilic.retrofitexcercise;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class NetworkUtils {
    private static final String BASE_API="https://makeup-api.herokuapp.com/";
    private static APIInterface apiInterface;

    public static APIInterface getApiInterface() {
        if (apiInterface == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_API)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

        apiInterface = retrofit.create(APIInterface.class);
        }
        return apiInterface;
    }
}
