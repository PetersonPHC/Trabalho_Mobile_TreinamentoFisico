package br.edu.fateczl.trabalho_mobile_treinamentofisico.model;

import java.time.LocalDate;
import java.util.List;

public class Treino {


    private Long id;

    private String type;

    private LocalDate date;

    private String muscularGroup;

    private String exercises;

    private int duration;

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


    public LocalDate getDate() {
        return date;
    }


    public void setDate(LocalDate date) {
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
                "\n duration=" + duration +
                "\n gym=" + gym +
                "\n]";
    }
}
