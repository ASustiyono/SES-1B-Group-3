package group3.seshealthpatient.activities;

import android.net.Uri;

public class VideoUpload {

    public String name;
    public String url;
    private String userId;
    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }

    public String getUserId() {
        return userId;
    }

    public VideoUpload(String name, String url,String userId) {
        this.name = name;
        this.url = url;
        this.userId = userId;
    }

    public VideoUpload(){}
}
