package br.edu.fateczl.trabalho_mobile_treinamentofisico;

import br.edu.fateczl.trabalho_mobile_treinamentofisico.http.HttpHelper;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.concurrent.CompletableFuture;

import org.json.JSONArray;
import org.json.JSONObject;



public class ListFragment extends Fragment {

    private View view;
    private Button btList;
    private TextView tvResList;
    private RadioButton rbList01;
    private RadioButton rbList02;

    public ListFragment() {
        super();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_list, container, false);

        btList = view.findViewById(R.id.btList);
        tvResList = view.findViewById(R.id.tvResList);
        rbList01 = view.findViewById(R.id.rb01List);
        rbList02 = view.findViewById(R.id.rb02List);

        btList.setOnClickListener(op -> search());

        return view;
    }

    private void search() {
        String url = "";

        if (rbList01.isChecked()) {
            url = "https://seu-api-url/gym";
        } else if (rbList02.isChecked()) {
            url = "https://seu-api-url/home";
        } else {
            Toast.makeText(view.getContext(), "Selecione o tipo de treino.", Toast.LENGTH_SHORT).show();
            return;
        }

        String finalUrl = url;
        CompletableFuture.runAsync(() -> {
            try {
                String response = HttpHelper.get(finalUrl);
                JSONArray jsonArray = new JSONArray(response);
                StringBuilder buffer = new StringBuilder();

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject treino = jsonArray.getJSONObject(i);
                    buffer.append(treino.toString()).append("\n");
                }

                requireActivity().runOnUiThread(() -> {
                    if (buffer.length() > 0) {
                        tvResList.setText(buffer.toString());
                    } else {
                        Toast.makeText(view.getContext(), "Nenhum treino encontrado.", Toast.LENGTH_SHORT).show();
                    }
                });
            } catch (Exception e) {
                requireActivity().runOnUiThread(() ->
                        Toast.makeText(view.getContext(), "Erro: " + e.getMessage(), Toast.LENGTH_SHORT).show()
                );
            }
        });
    }

}