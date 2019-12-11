package hr.ferit.davidbilic.retrofitexcercise;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<CustomViewHolder>{

    private List<Product> productList = new ArrayList<>();

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_cell,parent, false);
        return new CustomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder customViewHolder, int position) {
        customViewHolder.setCellAttributes(productList.get(position));
    }

    @Override
    public int getItemCount() {
        if (productList!=null)
        {return productList.size();}
        return 0;
    }

    public void addProducts(List<Product> list){
        this.productList.clear();
        this.productList.addAll(list);
        notifyDataSetChanged();
    }
}
