package hr.ferit.davidbilic.clickablerecyclerviewexcercise;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<CustomViewHolder> {

    private List<String> dataList= new ArrayList<>();
    public static int counter=0;
    private PictureClickListener clickListener;///ZA INTERFACE!!!

    public RecyclerAdapter(PictureClickListener listener){///ZA INTERFACE!!!
        this.clickListener=listener;///ZA INTERFACE!!!
    }

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int ViewType) {
        View cellView = LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_name,parent,false);
        return new CustomViewHolder(cellView,clickListener);///ZA INTERFACE!!!
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder customViewHolder, int position) {
        customViewHolder.setName(dataList.get(position));
    }

    @Override
    public int getItemCount() {  return dataList.size(); }

    public void addNewCell(String newName){
        dataList.add(counter, newName);
        notifyItemInserted(counter);
        counter=counter+1;
    }
    public void removeCell(int position){
        dataList.remove(position);
        notifyItemRemoved(position);
        counter=counter-1;
    }

}
