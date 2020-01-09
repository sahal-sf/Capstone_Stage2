package net.sahal.capstone_stage2;

public class Exercises {

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

    public void setExercise(String exercise) {
        this.exercise = exercise;
    }

    public String getVideos() {
        return videos;
    }

    public void setVideos(String videos) {
        this.videos = videos;
    }
}
