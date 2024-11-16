package br.edu.fateczl.trabalho_mobile_treinamentofisico.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;
import java.time.LocalDate;

import br.edu.fateczl.trabalho_mobile_treinamentofisico.R;
import br.edu.fateczl.trabalho_mobile_treinamentofisico.model.Treino;
import okhttp3.*;

public class TreinoCasaFragment extends Fragment {

    private View view;
    private EditText etDateTC, etMuscTC, etExTC, etTimeTC;
    private Button btRegTC, btUpTC;

    private static final String BASE_URL = "http://192.168.1.5:8080/api/home/";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_treino_casa, container, false);

        etDateTC = view.findViewById(R.id.etDateTC);
        etMuscTC = view.findViewById(R.id.etMuscTC);
        etExTC = view.findViewById(R.id.etExTC);
        etTimeTC = view.findViewById(R.id.etTimeTC);
        btRegTC = view.findViewById(R.id.btRegTC);
        btUpTC = view.findViewById(R.id.btUpTC);

        btRegTC.setOnClickListener(op -> register());
        btUpTC.setOnClickListener(op -> update());

        return view;
    }

    private void register() {
        if (validateFields()) {
            Treino tc = create();
            sendRequest("POST", tc, false);
        } else {
            Toast.makeText(view.getContext(), "Preencha todos os campos.", Toast.LENGTH_SHORT).show();
        }
    }

    private void update() {
        if (validateFields()) {
            Treino tc = create();
            sendRequest("PUT", tc, true);
        } else {
            Toast.makeText(view.getContext(), "Preencha todos os campos.", Toast.LENGTH_SHORT).show();
        }
    }


    private void sendRequest(String method, Treino treino, boolean isUpdate) {
        OkHttpClient client = new OkHttpClient();

        MediaType mediaType = MediaType.parse("application/json; charset=utf-8");
        String jsonBody = treinoToJson(treino);

        RequestBody body = RequestBody.create(mediaType, jsonBody);
        Request request;

        if (method.equals("POST")) {
            request = new Request.Builder()
                    .url(BASE_URL)
                    .post(body)
                    .build();
        } else { // PUT
            request = new Request.Builder()
                    .url(BASE_URL + treino.getId()) // Assumindo que o ID é necessário no URL para PUT
                    .put(body)
                    .build();
        }

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                getActivity().runOnUiThread(() ->
                        Toast.makeText(view.getContext(), "Erro na requisição: " + e.getMessage(), Toast.LENGTH_LONG).show()
                );
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                getActivity().runOnUiThread(() -> {
                    if (response.isSuccessful()) {
                        Toast.makeText(view.getContext(),
                                method.equals("POST") ? "Treino salvo com sucesso." : "Treino atualizado com sucesso.",
                                Toast.LENGTH_LONG
                        ).show();
                        clearFields();
                    } else {
                        Toast.makeText(view.getContext(), "Erro: " + response.message(), Toast.LENGTH_LONG).show();
                    }
                });
            }
        });
    }
    private String treinoToJson(Treino treino) {
        return String.format("{\"type\":\"Home\", \"date\":\"%s\", \"muscularGroup\":\"%s\", \"exercises\":\"%s\", \"time\":%d}",
                treino.getDate(), treino.getMuscularGroup(), treino.getExercises(), treino.getDuration());
    }

    private Treino create() {
        Treino ta = new Treino();
        ta.setDate(LocalDate.parse(etDateTC.getText().toString()));
        ta.setMuscularGroup(etMuscTC.getText().toString());
        ta.setExercises(etExTC.getText().toString());
        ta.setDuration(60);
        return ta;
    }

    private void clearFields() {
        etDateTC.setText("");
        etMuscTC.setText("");
        etExTC.setText("");
        etTimeTC.setText("");
    }

    private boolean validateFields() {
        return !etDateTC.getText().toString().isEmpty() &&
                !etMuscTC.getText().toString().isEmpty() &&
                !etExTC.getText().toString().isEmpty() &&
                !etTimeTC.getText().toString().isEmpty();
    }
}