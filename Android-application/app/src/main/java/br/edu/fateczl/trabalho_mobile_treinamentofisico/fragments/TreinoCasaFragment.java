package br.edu.fateczl.trabalho_mobile_treinamentofisico.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

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

import java.io.IOException;

import br.edu.fateczl.trabalho_mobile_treinamentofisico.R;
import br.edu.fateczl.trabalho_mobile_treinamentofisico.model.Treino;
import okhttp3.*;

public class TreinoCasaFragment extends Fragment {

    private View view;
    private EditText etDateTC, etMuscTC, etExTC, etTimeTC;
    private Button btRegTC, btUpTC;

    private static final String BASE_URL = "http://192.168.15.85:8080/api/trainings";

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
            String json = treinoToJson(tc);

            Log.d("JSONFormatado", json);
            System.out.println(json);

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

        // Esconda o teclado antes de iniciar a requisição
        InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null && getView() != null) {
            imm.hideSoftInputFromWindow(getView().getWindowToken(), 0);
        }

        MediaType mediaType = MediaType.parse("application/json; charset=utf-8");
        String jsonBody = treinoToJson(treino);
        RequestBody body = RequestBody.create(mediaType, jsonBody);

        Request request;
        if (method.equals("POST")) {
            request = new Request.Builder()
                    .url(BASE_URL)
                    .post(body)
                    .build();
        } else {
            request = new Request.Builder()
                    .url(BASE_URL + "/" + treino.getDate())
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
                                        method.equals("POST") ? "Treino salvo com sucesso." : "Treino atualizado com sucesso.",
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
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(treino);
    }

    private Treino create() {
        Treino tc = new Treino();
        tc.setType("Home");
        tc.setDate(etDateTC.getText().toString());
        tc.setMuscularGroup(etMuscTC.getText().toString());
        tc.setExercises(etExTC.getText().toString());
        tc.setDuration(Integer.parseInt(etTimeTC.getText().toString()));
        return tc;
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