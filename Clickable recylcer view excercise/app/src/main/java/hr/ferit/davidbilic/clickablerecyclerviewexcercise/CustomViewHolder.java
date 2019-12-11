package hr.ferit.davidbilic.clickablerecyclerviewexcercise;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class CustomViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{///ZA INTERFACE!!!
    private TextView tvIme;
    private ImageView ivSlikaX;
    private PictureClickListener clickListener;///ZA INTERFACE!!!

    public CustomViewHolder(@NonNull View itemView, PictureClickListener listener){///ZA INTERFACE!!!
        super(itemView);
        tvIme =(TextView)itemView.findViewById(R.id.tvIme);
        ivSlikaX=(ImageView)itemView.findViewById(R.id.ivSlikaX);
        this.clickListener=listener; ///ZA INTERFACE!!!
        ivSlikaX.setOnClickListener(this); ///ZA INTERFACE!!!
    }

    public void setName(String name){
        tvIme.setText(name);
    }

    @Override ///ZA INTERFACE!!!
    public void onClick(View v) { ///ZA INTERFACE!!!
        clickListener.OnPictureClick(getAdapterPosition());///ZA INTERFACE!!!
    }
}
