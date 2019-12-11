package gg.bidavid.fragmentcommunicationexcercise;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class ModularFragment extends Fragment {
    private static final String BUNDLE_MESSAGE = "message";

    public static ModularFragment newInstance(String message) {
        ModularFragment fragment = new ModularFragment();
        Bundle args = new Bundle();
        args.putString(BUNDLE_MESSAGE, message);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        String wantedType = getArguments().getString(BUNDLE_MESSAGE);

        if (wantedType.matches("text")) {
            ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_text, container, false);
            return rootView;
        }

        else{
            ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_picture, container, false);
            return rootView;
        }
    }
}
