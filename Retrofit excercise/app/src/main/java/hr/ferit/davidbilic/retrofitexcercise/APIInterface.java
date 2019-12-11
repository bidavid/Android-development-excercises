package hr.ferit.davidbilic.retrofitexcercise;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface APIInterface {

    @GET("api/v1/products.json")
    Call<List<Product>> getProducts(@Query("brand") String brand_name);
}
