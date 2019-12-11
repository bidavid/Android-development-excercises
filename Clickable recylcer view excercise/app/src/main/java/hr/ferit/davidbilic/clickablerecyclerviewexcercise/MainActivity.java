package hr.ferit.davidbilic.clickablerecyclerviewexcercise;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements PictureClickListener{///ZA INTERFACE!!!

    private RecyclerView recycler;
    private RecyclerAdapter adapter;
    private EditText etIme;
    private Button btnDodaj;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        InitializeID();
        setupRecycler();
    }

    private void InitializeID() {
        etIme=(EditText)findViewById(R.id.etIme);
        btnDodaj=(Button)findViewById(R.id.btnDodaj);
    }

    private void setupRecycler() {
        recycler=(RecyclerView)findViewById(R.id.recyclerView);
        recycler.setLayoutManager(new LinearLayoutManager(this));
        adapter=new RecyclerAdapter(this); ///ZA INTERFACE!!!
        recycler.setAdapter(adapter);
    }

    public void OnClickAddItem(View view) {
        String UneseniString = etIme.getText().toString();

        if(UneseniString.matches("")) {
            Toast.makeText(this, "Unesite ime!", Toast.LENGTH_LONG).show();
            return;
        }
        else adapter.addNewCell(UneseniString);
        etIme.getText().clear();//Nakon unosa osobe očisti edittext
    }

    @Override
    public void OnPictureClick(int position) {///ZA INTERFACE!!!
        adapter.removeCell(position);///ZA INTERFACE!!!
    }
}
