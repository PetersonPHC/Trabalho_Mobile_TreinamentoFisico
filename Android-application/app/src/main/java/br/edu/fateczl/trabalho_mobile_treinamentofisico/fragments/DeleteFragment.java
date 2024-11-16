package br.edu.fateczl.trabalho_mobile_treinamentofisico.fragments;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import br.edu.fateczl.trabalho_mobile_treinamentofisico.R;
import br.edu.fateczl.trabalho_mobile_treinamentofisico.http.ApiService;
import br.edu.fateczl.trabalho_mobile_treinamentofisico.http.RetrofitClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DeleteFragment extends Fragment {

    private View view;
    private EditText etDateDL;
    private Button btDelete;

    public DeleteFragment() {
        super();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_delete, container, false);

        etDateDL = view.findViewById(R.id.etDateDL);
        btDelete = view.findViewById(R.id.btDelete);

        btDelete.setOnClickListener(op -> delete());

        return view;
    }

    private void delete() {
        String date = etDateDL.getText().toString().trim();
        if (date.isEmpty()) {
            Toast.makeText(view.getContext(), "Preencha todos os campos.", Toast.LENGTH_SHORT).show();
            return;
        }

        ApiService apiService = RetrofitClient.getClient().create(ApiService.class);
        Call<Void> call = apiService.deleteTraining(date);

        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(view.getContext(), "Treino removido com sucesso.", Toast.LENGTH_SHORT).show();
                    clearFields();
                } else {
                    Toast.makeText(view.getContext(), "Erro: " + response.code(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(view.getContext(), "Erro: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void clearFields() {
        etDateDL.setText("");
    }
}