package br.edu.fateczl.trabalho_mobile_treinamentofisico.http;

import org.json.JSONObject;

import java.util.List;
import java.util.Map;

import br.edu.fateczl.trabalho_mobile_treinamentofisico.model.Treino;
import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ApiService {
    @DELETE("trainings/{date}")
    Call<Void> deleteTraining(@Path("date") String date);

    @GET("trainings/{date}")
    Call<Map<String, Object>> getTraining(@Path("date") String date);

    @GET("api/trainings")
    Call<List<Treino>> getAllTrainings();

    @GET("api/trainings/type/{type}")
    Call<List<Treino>> getTrainingsByType(@Path("type") String type);
}