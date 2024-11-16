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


public class TreinoAcademiaFragment extends Fragment {

    private View view;
    private EditText etDateTA, etMuscTA, etLocalTA, etExTA;
    private Button btRegTA, btUpTA;
    private static final String BASE_URL = "http://192.168.72.240:8080/api/gym/";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_treino_academia, container, false);

        etDateTA = view.findViewById(R.id.etDateTA);
        etMuscTA = view.findViewById(R.id.etMuscTA);
        etLocalTA = view.findViewById(R.id.etLocalTA);
        etExTA = view.findViewById(R.id.etExTA);
        btRegTA = view.findViewById(R.id.btRegTA);
        btUpTA = view.findViewById(R.id.btUpTA);

        btRegTA.setOnClickListener(op -> register());
        btUpTA.setOnClickListener(op -> update());

        return view;
    }

    private void register() {
        if (validateFields()) {
            Treino ta = create();
            sendRequest("POST", ta, false);
        } else {
            Toast.makeText(view.getContext(), "Preencha todos os campos.", Toast.LENGTH_SHORT).show();
        }
    }

    private void update() {
        if (validateFields()) {
            Treino ta = create();
            sendRequest("PUT", ta, true);
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
        return String.format("{\"type\":\"Gym\", \"date\":\"%s\", \"muscularGroup\":\"%s\", \"exercises\":\"%s\", \"duration\":\"%s\", \"gym\":\"%s\"}",
                treino.getDate(), treino.getMuscularGroup(), treino.getExercises(), treino.getDuration(), treino.getGym());
    }

    private Treino create() {
        Treino ta = new Treino();
        ta.setDate(LocalDate.parse(etDateTA.getText().toString()));
        ta.setMuscularGroup(etMuscTA.getText().toString());
        ta.setExercises(etExTA.getText().toString());
        ta.setDuration(60);
        ta.setGym(etLocalTA.getText().toString());
        return ta;
    }

    private void clearFields() {
        etDateTA.setText("");
        etMuscTA.setText("");
        etLocalTA.setText("");
        etExTA.setText("");
    }

    private boolean validateFields() {
        return !etDateTA.getText().toString().isEmpty() &&
                !etMuscTA.getText().toString().isEmpty() &&
                !etLocalTA.getText().toString().isEmpty() &&
                !etExTA.getText().toString().isEmpty();
    }
}