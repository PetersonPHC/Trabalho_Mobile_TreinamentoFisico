package br.edu.fateczl.trabalho_mobile_treinamentofisico;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import br.edu.fateczl.trabalho_mobile_treinamentofisico.fragments.DeleteFragment;
import br.edu.fateczl.trabalho_mobile_treinamentofisico.fragments.ListFragment;
import br.edu.fateczl.trabalho_mobile_treinamentofisico.fragments.SearchFragment;
import br.edu.fateczl.trabalho_mobile_treinamentofisico.fragments.TreinoAcademiaFragment;
import br.edu.fateczl.trabalho_mobile_treinamentofisico.fragments.TreinoCasaFragment;

public class MainActivity extends AppCompatActivity {

    private Fragment fragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        Bundle bundle = getIntent().getExtras();
        if(bundle !=null){
            loadFragment(bundle);
        }

    }

    private void loadFragment(Bundle bundle) {
        String type = bundle.getString("type");
        if(type.equals("Academia")){
            fragment = new TreinoAcademiaFragment();
        }else if (type.equals("Casa")){
            fragment = new TreinoCasaFragment();
        } else if (type.equals("Delete")) {
            fragment = new DeleteFragment();
        }else if (type.equals("List")){
            fragment = new ListFragment();
        } else if (type.equals("Search")) {
            fragment = new SearchFragment();
        }
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment, fragment);
        fragmentTransaction.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        Intent i = new Intent(this, MainActivity.class);
        Bundle bundle = new Bundle();
        if(id == R.id.item_01){
            bundle.putString("type","Academia");
            i.putExtras(bundle);
            this.startActivity(i);
            this.finish();
            return true;
        }
        if(id == R.id.item_02){
            bundle.putString("type","Casa");
            i.putExtras(bundle);
            this.startActivity(i);
            this.finish();
            return true;
        }
        if(id == R.id.item_03){
            bundle.putString("type","Delete");
            i.putExtras(bundle);
            this.startActivity(i);
            this.finish();
            return true;
        }
        if (id == R.id.item_04){
            bundle.putString("type","Search");
            i.putExtras(bundle);
            this.startActivity(i);
            this.finish();
            return true;
        }
        if (id == R.id.item_05){
            bundle.putString("type","List");
            i.putExtras(bundle);
            this.startActivity(i);
            this.finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}