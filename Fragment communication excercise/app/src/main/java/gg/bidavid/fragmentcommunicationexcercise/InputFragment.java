package gg.bidavid.fragmentcommunicationexcercise;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class InputFragment extends Fragment implements TextWatcher {
    private String messageString ="...";
    private EditText etEditMessage;
    private Button btnSubmitMessage;

    private SubmitClickListener submitClickListener;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup
            container, @Nullable Bundle savedInstanceState) {

        ViewGroup rootView= (ViewGroup)inflater.inflate(R.layout.fragment_input, container, false);
        etEditMessage =rootView.findViewById(R.id.etMessage);
        btnSubmitMessage =rootView.findViewById(R.id.btnSubmit);
        setUpListeners();
        return rootView;
    }

    public void setUpListeners(){

        btnSubmitMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitClickListener.onSubmitClicked(messageString);
                etEditMessage.setText("");
            }
        });
        etEditMessage.addTextChangedListener(this);
    }
    @Override
    public void onAttach(Context context){
        super.onAttach(context);
        if(context instanceof SubmitClickListener){
            this.submitClickListener=(SubmitClickListener)context;
        }
    }

    @Override
    public void onDetach(){
        super.onDetach();
        this.submitClickListener=null;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        messageString =s.toString();
    }
}
