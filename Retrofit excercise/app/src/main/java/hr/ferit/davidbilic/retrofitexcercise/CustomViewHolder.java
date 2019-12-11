package hr.ferit.davidbilic.retrofitexcercise;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class CustomViewHolder extends RecyclerView.ViewHolder {
    private TextView tvProductName;
    private TextView tvProductPrice;
    private TextView tvProductDescription;
    private ImageView ivProductImage;

    public CustomViewHolder(@NonNull View itemView) {
        super(itemView);

        tvProductName=(TextView)itemView.findViewById(R.id.tvProductName);
        tvProductPrice=(TextView)itemView.findViewById(R.id.tvProductPrice);
        tvProductDescription=(TextView)itemView.findViewById(R.id.tvProductDescription);

        ivProductImage=(ImageView) itemView.findViewById(R.id.ivProductImage);

    }


    public void setCellAttributes(Product product){
        tvProductName.setText(product.getName());
        tvProductPrice.setText(product.getPrice()+"$");
        tvProductDescription.setText(product.getDescription());
        Picasso.get().load(product.getImageLink()).into(ivProductImage);
    }
}
