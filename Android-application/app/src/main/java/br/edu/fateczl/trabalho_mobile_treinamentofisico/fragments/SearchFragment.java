package br.edu.fateczl.trabalho_mobile_treinamentofisico.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.util.Map;

import br.edu.fateczl.trabalho_mobile_treinamentofisico.R;
import br.edu.fateczl.trabalho_mobile_treinamentofisico.http.ApiService;
import br.edu.fateczl.trabalho_mobile_treinamentofisico.http.RetrofitClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchFragment extends Fragment {

    private View view;
    private EditText etDateSC;
    private Button btSearch;
    private TextView tvResSC;

    public SearchFragment() {
        super();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_search, container, false);

        etDateSC = view.findViewById(R.id.etDateSC);
        btSearch = view.findViewById(R.id.btSearch);
        tvResSC = view.findViewById(R.id.tvResSC);

        btSearch.setOnClickListener(op -> search());

        return view;
    }

    private void search() {
        String date = etDateSC.getText().toString().trim();
        if (date.isEmpty()) {
            Toast.makeText(view.getContext(), "Insira a data do treino.", Toast.LENGTH_SHORT).show();
            return;
        }

        ApiService apiService = RetrofitClient.getClient().create(ApiService.class);
        Call<Map<String, Object>> call = apiService.getTraining(date);

        call.enqueue(new Callback<Map<String, Object>>() {
            @Override
            public void onResponse(Call<Map<String, Object>> call, Response<Map<String, Object>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    // Formata os dados retornados
                    Map<String, Object> responseData = response.body();
                    if (!responseData.isEmpty()) {
                        StringBuilder formattedData = new StringBuilder();
                        for (Map.Entry<String, Object> entry : responseData.entrySet()) {
                            formattedData.append(entry.getKey())
                                    .append(": ")
                                    .append(entry.getValue())
                                    .append("\n");
                        }
                        tvResSC.setText(formattedData.toString());
                    } else {
                        tvResSC.setText("Nenhum dado encontrado para a data informada.");
                    }
                } else {
                    Toast.makeText(view.getContext(), "Treino não encontrado. Código: " + response.code(), Toast.LENGTH_SHORT).show();
                    tvResSC.setText("");
                }
            }

            @Override
            public void onFailure(Call<Map<String, Object>> call, Throwable t) {
                Toast.makeText(view.getContext(), "Erro: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                tvResSC.setText("");
            }
        });
    }
}