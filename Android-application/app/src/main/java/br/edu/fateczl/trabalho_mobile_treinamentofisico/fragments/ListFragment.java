package br.edu.fateczl.trabalho_mobile_treinamentofisico.fragments;

import android.os.Bundle;
import androidx.fragment.app.Fragment;

import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

import br.edu.fateczl.trabalho_mobile_treinamentofisico.R;
import br.edu.fateczl.trabalho_mobile_treinamentofisico.model.Treino;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ListFragment extends Fragment {

    private View view;
    private Button btList;
    private TextView tvResList;
    private RadioButton rbList01;
    private RadioButton rbList02;
    private RadioButton rbList03;

    public ListFragment() {
        super();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_list, container, false);

        btList = view.findViewById(R.id.btList);
        tvResList = view.findViewById(R.id.tvResList);
        tvResList.setMovementMethod(new ScrollingMovementMethod());
        rbList01 = view.findViewById(R.id.rb01List); // Todos
        rbList02 = view.findViewById(R.id.rb02List); // Home
        rbList03 = view.findViewById(R.id.rb03List); // Gym
        btList.setOnClickListener(op -> search());

        return view;
    }

    private void search() {
        OkHttpClient client = new OkHttpClient();
        String url;

        if (rbList01.isChecked()) {
            // URL para buscar todos os treinos
            url = "http://192.168.15.85:8080/api/trainings"; // Substitua pelo seu endpoint real
        } else if (rbList02.isChecked()) {
            // URL para buscar treinos do tipo "home"
            url = "http://192.168.15.85:8080/api/trainings/type/Home"; // Substitua pelo seu endpoint real
        } else if (rbList03.isChecked()) {
            // URL para buscar treinos do tipo "gym"
            url = "http://192.168.15.85:8080/api/trainings/type/Gym"; // Substitua pelo seu endpoint real
        } else {
            Toast.makeText(view.getContext(), "Selecione o tipo de treino.", Toast.LENGTH_SHORT).show();
            return;
        }

        // Criar thread para requisição
        new Thread(() -> {
            try {
                Request request = new Request.Builder()
                        .url(url)
                        .build();

                Response response = client.newCall(request).execute();

                if (response.isSuccessful() && response.body() != null) {
                    String responseData = response.body().string();

                    // Parse JSON usando Gson
                    Gson gson = new Gson();
                    Type listType = new TypeToken<List<Treino>>() {}.getType();
                    List<Treino> treinos = gson.fromJson(responseData, listType);

                    // Atualizar a UI na thread principal
                    getActivity().runOnUiThread(() -> {
                        if (treinos.isEmpty()) {
                            tvResList.setText("Nenhum treino encontrado.");
                        } else {
                            StringBuilder formattedData = new StringBuilder();
                            for (Treino treino : treinos) {
                                formattedData.append(treino.toString()).append("\n\n");
                            }
                            tvResList.setText(formattedData.toString());
                        }
                    });
                } else {
                    String errorMsg = "Erro ao carregar dados. Código: " + response.code();
                    Log.e("API_RESPONSE", errorMsg);
                    getActivity().runOnUiThread(() -> tvResList.setText(errorMsg));
                }
            } catch (Exception e) {
                Log.e("API_FAILURE", "Falha: " + e.getMessage(), e);
                getActivity().runOnUiThread(() -> {
                    tvResList.setText("Erro ao carregar os dados.");
                    Toast.makeText(view.getContext(), "Erro: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
            }
        }).start();
    }
}