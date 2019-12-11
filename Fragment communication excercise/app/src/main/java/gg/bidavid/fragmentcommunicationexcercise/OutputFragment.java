package gg.bidavid.fragmentcommunicationexcercise;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class OutputFragment extends Fragment {
    private TextView tvDisplayedMessage;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup
            container, @Nullable Bundle savedInstanceState){
        ViewGroup rootView = (ViewGroup)inflater.inflate(R.layout.fragment_output,container,false);

        tvDisplayedMessage= rootView.findViewById(R.id.tvMessage);
        return rootView;
    }

    public void displayMessage(String message){
        tvDisplayedMessage.setText(message);
    }
}
