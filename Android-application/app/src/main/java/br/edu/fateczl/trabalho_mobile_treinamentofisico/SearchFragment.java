package br.edu.fateczl.trabalho_mobile_treinamentofisico;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.concurrent.CompletableFuture;

import org.json.JSONObject;

import br.edu.fateczl.trabalho_mobile_treinamentofisico.http.HttpHelper;
public class SearchFragment extends Fragment {

    private View view;
    private EditText etDateSC;
    private Button btSearch;
    private TextView tvResSC;
    private RadioButton rb01Search;
    private RadioButton rb02Search;

    public SearchFragment() {
        super();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_search, container, false);

        etDateSC = view.findViewById(R.id.etDateSC);
        btSearch = view.findViewById(R.id.btSearch);
        tvResSC = view.findViewById(R.id.tvResSC);
        rb01Search = view.findViewById(R.id.rb01Search);
        rb02Search = view.findViewById(R.id.rb02Search);

        btSearch.setOnClickListener(op -> search());

        return view;
    }

    private void search() {
        if (etDateSC.getText().toString().isEmpty()) {
            Toast.makeText(view.getContext(), "Insira a data do treino.", Toast.LENGTH_SHORT).show();
            return;
        }

        String url = "";
        if (rb01Search.isChecked()) {
            url = "https://seu-api-url/gym/";
        } else if (rb02Search.isChecked()) {
            url = "https://seu-api-url/home/";
        } else {
            Toast.makeText(view.getContext(), "Selecione o tipo de treino.", Toast.LENGTH_SHORT).show();
            return;
        }

        url += etDateSC.getText().toString();

        String finalUrl = url;
        CompletableFuture.runAsync(() -> {
            try {
                String response = HttpHelper.get(finalUrl);
                JSONObject treino = new JSONObject(response);

                requireActivity().runOnUiThread(() ->
                        tvResSC.setText(treino.toString())
                );
            } catch (Exception e) {
                requireActivity().runOnUiThread(() ->
                        Toast.makeText(view.getContext(), "Erro: " + e.getMessage(), Toast.LENGTH_SHORT).show()
                );
            }
        });
    }

}