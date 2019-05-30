package pvt19grupp1.kunskapp.com.kunskapp.models;

import com.google.android.gms.maps.model.LatLng;

import java.util.List;
import java.util.UUID;

public class QuizWalk {

    private String name;
    private String description;
    private UUID id;
    private boolean running;
    private List<QuizPlace> quizPlaces;
    private List<LatLng> latLngPoints;
    private double totaldistance;

    public List<LatLng> getLatLngPoints() {
        return latLngPoints;
    }

    public void setLatLngPoints(List<LatLng> latLngPoints) {
        this.latLngPoints = latLngPoints;
    }

    public double getTotaldistance() {
        return totaldistance;
    }

    public void setTotaldistance(double totaldistance) {
        this.totaldistance = totaldistance;
    }

    public QuizWalk(String name, String description, List<QuizPlace> quizPlaces) {
        this.name = name;
        this.description = description;
        this.quizPlaces = quizPlaces;
    }

    public boolean isRunning() { return running; }

    public void setRunning(Boolean b) { this.running = b; }

    public String getName() { return name; }

    public String getDescription() { return description; }

    public List<QuizPlace> getQuizPlaces() { return quizPlaces; }

    public void setName(String n) { this.name = n; }

    public void setDescription(String desc) { this.description = description; }

}
