package br.edu.fateczl.trabalho_mobile_treinamentofisico.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;



import java.util.concurrent.CompletableFuture;

import br.edu.fateczl.trabalho_mobile_treinamentofisico.R;
import br.edu.fateczl.trabalho_mobile_treinamentofisico.http.HttpHelper;

public class DeleteFragment extends Fragment {

    private View view;
    private EditText etDateDL;
    private EditText etMuscDL;
    private Button btDelete;
    private RadioButton rb01Del;
    private RadioButton rb02Del;

    public DeleteFragment() {
        super();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_delete, container, false);

        etDateDL = view.findViewById(R.id.etDateDL);
        etMuscDL = view.findViewById(R.id.etMuscDL);
        btDelete = view.findViewById(R.id.btDelete);
        rb01Del = view.findViewById(R.id.rB01Del);
        rb02Del = view.findViewById(R.id.rb02Del);

        btDelete.setOnClickListener(op -> delete());

        return view;
    }

    private void delete() {
        if (etDateDL.getText().toString().isEmpty() || etMuscDL.getText().toString().isEmpty()) {
            Toast.makeText(view.getContext(), "Preencha todos os campos.", Toast.LENGTH_SHORT).show();
            return;
        }

        String url = "";
        if (rb01Del.isChecked()) {
            url = "https://seu-api-url/gym/";
        } else if (rb02Del.isChecked()) {
            url = "https://seu-api-url/home/";
        } else {
            Toast.makeText(view.getContext(), "Selecione o tipo de treino.", Toast.LENGTH_SHORT).show();
            return;
        }

        url += etDateDL.getText().toString();

        String finalUrl = url;
        CompletableFuture.runAsync(() -> {
            try {
                HttpHelper.delete(finalUrl);
                requireActivity().runOnUiThread(() ->
                        Toast.makeText(view.getContext(), "Treino removido com sucesso.", Toast.LENGTH_SHORT).show()
                );
            } catch (Exception e) {
                requireActivity().runOnUiThread(() ->
                        Toast.makeText(view.getContext(), "Erro: " + e.getMessage(), Toast.LENGTH_SHORT).show()
                );
            }
        });
    }

}
