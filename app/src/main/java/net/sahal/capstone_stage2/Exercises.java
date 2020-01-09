package net.sahal.capstone_stage2;

import java.io.Serializable;

public class Exercises implements Serializable {

    private String exercise;
    private String videos;

    public Exercises() {
    }

    public Exercises(String exercise, String videos) {
        this.exercise = exercise;
        this.videos = videos;
    }

    public String getExercise() {
        return exercise;
    }

    public String getVideos() {
        return videos;
    }
}
