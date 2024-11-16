package br.edu.fateczl.trabalho_mobile_treinamentofisico.model;

import com.google.gson.annotations.SerializedName;

public class Treino {

    @SerializedName("id")
    private Long id;

    @SerializedName("type")
    private String type;

    @SerializedName("date")
    private String date;

    @SerializedName("muscularGroup")
    private String muscularGroup;

    @SerializedName("exercises")
    private String exercises;

    @SerializedName("duration")
    private int duration;

    @SerializedName("gym")
    private String gym;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getMuscularGroup() {
        return muscularGroup;
    }

    public void setMuscularGroup(String muscularGroup) {
        this.muscularGroup = muscularGroup;
    }

    public String getExercises() {
        return exercises;
    }

    public void setExercises(String exercises) {
        this.exercises = exercises;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getGym() {
        return gym;
    }

    public void setGym(String gym) {
        this.gym = gym;
    }

    @Override
    public String toString() {
        return "Treino" +
                "\n [id=" + id +
                "\n type=" + type +
                "\n date=" + date +
                "\n muscularGroup=" + muscularGroup +
                "\n exercises=" + exercises +
                "\n duration=" + duration +
                "\n gym=" + gym +
                "\n]";
    }
}
