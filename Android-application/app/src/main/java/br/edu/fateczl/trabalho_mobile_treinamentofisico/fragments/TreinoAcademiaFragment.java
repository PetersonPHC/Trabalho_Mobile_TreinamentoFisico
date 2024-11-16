package br.edu.fateczl.trabalho_mobile_treinamentofisico.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import com.google.gson.GsonBuilder;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.Console;
import java.io.IOException;
import java.time.LocalDate;

import br.edu.fateczl.trabalho_mobile_treinamentofisico.R;
import br.edu.fateczl.trabalho_mobile_treinamentofisico.model.Treino;
import okhttp3.*;


public class TreinoAcademiaFragment extends Fragment {

    private View view;
    private EditText etDateTA, etMuscTA, etLocalTA, etExTA;
    private Button btRegTA, btUpTA;
    private static final String BASE_URL = "http://192.168.72.240:8080/api/trainings";

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
            Treino ta = create(); // Cria o objeto treino
            String json = treinoToJson(ta);

            Log.d("JSONFormatado", json); // Exibe no Logcat
            System.out.println(json);

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

        // Esconda o teclado antes de iniciar a requisição
        InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null && getView() != null) {
            imm.hideSoftInputFromWindow(getView().getWindowToken(), 0);
        }

        MediaType mediaType = MediaType.parse("application/json; charset=utf-8" );
        String jsonBody = treinoToJson(treino);
        RequestBody body = RequestBody.create(mediaType, jsonBody);

        Request request;
        if (method.equals("POST" )) {
            request = new Request.Builder()
                    .url(BASE_URL)
                    .post(body)
                    .build();
        } else {
            request = new Request.Builder()
                    .url(BASE_URL + "/" + treino.getId())
                    .put(body)
                    .build();
        }

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

                if (getActivity() != null && !getActivity().isFinishing()) {
                    getActivity().runOnUiThread(() ->
                            Toast.makeText(getContext(), "Erro na requisição: " + e.getMessage(), Toast.LENGTH_LONG).show()
                    );
                }
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try (ResponseBody responseBody = response.body()) {
                    String responseText = responseBody != null ? responseBody.string() : "Resposta vazia";
                    Log.d("ServerResponse", "Código de resposta: " + response.code());
                    if (getActivity() != null && !getActivity().isFinishing()) {
                        getActivity().runOnUiThread(() -> {
                            if (response.isSuccessful()) {
                                Toast.makeText(getContext(),
                                        method.equals("POST" ) ? "Treino salvo com sucesso." : "Treino atualizado com sucesso.",
                                        Toast.LENGTH_LONG
                                ).show();
                                clearFields();
                            } else {
                                Toast.makeText(getContext(),
                                        "Erro: Código " + response.code() + " - " + responseText,
                                        Toast.LENGTH_LONG
                                ).show();
                            }
                        });
                    }
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                    System.out.println(e.getStackTrace());
                }
            }
        });
    }

    private String treinoToJson(Treino treino) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create(); // Cria o Gson com formatação
        return gson.toJson(treino);
    }

    private Treino create() {
        Treino ta = new Treino();
        ta.setType("Gym" );
        ta.setDate(etDateTA.getText().toString());
        ta.setMuscularGroup(etMuscTA.getText().toString());
        ta.setExercises(etExTA.getText().toString());
        ta.setDuration(60);
        ta.setGym(etLocalTA.getText().toString());
        return ta;
    }

    private void clearFields() {
        etDateTA.setText("" );
        etMuscTA.setText("" );
        etLocalTA.setText("" );
        etExTA.setText("" );
    }

    private boolean validateFields() {
        return !etDateTA.getText().toString().isEmpty() &&
                !etMuscTA.getText().toString().isEmpty() &&
                !etLocalTA.getText().toString().isEmpty() &&
                !etExTA.getText().toString().isEmpty();
    }
}
