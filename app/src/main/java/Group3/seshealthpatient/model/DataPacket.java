package group3.seshealthpatient.model;

import java.io.Serializable;

public class DataPacket  implements Serializable {

    private String id, title, description, heartRate, date, video;

    public DataPacket(String id, String title, String description, String heartRate, String date, String video) {
        this.id = id;
        this.title = title;
        this.description = title;
        this.heartRate = heartRate;
        this.date = date;
        this.video = video;
    }

    public DataPacket() { }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) { this.description = description; }

    public String getHeartRate() { return heartRate; }

    public void setHeartRate(String heartRate) { this.heartRate = heartRate; }

    public String getDate() { return date; }

    public void setDate(String date) { this.date = date; }

    public String getVideo() { return video; }

    public void setVideo(String video) { this.video = video; }
}
