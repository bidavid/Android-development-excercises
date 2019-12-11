package hr.ferit.davidbilic.retrofitexcercise;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private Call<List<Product>> apiCall;

    private Button btnSearchProduct;
    private EditText etSearchProduct;

    private RecyclerView recycler;
    private RecyclerAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeUI();
        setUpRecycler();

        btnSearchProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setUpAPIcall(etSearchProduct.getText().toString());
            }
        });

    }


    public void initializeUI(){
        btnSearchProduct= findViewById(R.id.btnSearchProduct);
        etSearchProduct= findViewById(R.id.etSearchProduct);
        recycler=findViewById(R.id.rvProducts);
    }

    public void setUpRecycler(){
        recycler.setLayoutManager(new LinearLayoutManager(this));
        adapter= new RecyclerAdapter();
        recycler.setAdapter(adapter);
    }

    private void setUpAPIcall(String brand) {
        apiCall = NetworkUtils.getApiInterface().getProducts(brand);
        apiCall.enqueue(new Callback<List<Product>>() {

            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    adapter.addProducts(response.body());
                }
                if (response.isSuccessful() && response.body().size()==0) {
                    etSearchProduct.setError("No product matches search request");
                }
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    protected void onDestroy() {
    super.onDestroy();
    if (apiCall != null)
        apiCall.cancel();
}
}
