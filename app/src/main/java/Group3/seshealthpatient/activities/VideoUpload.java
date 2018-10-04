package group3.seshealthpatient.activities;

import android.net.Uri;

public class VideoUpload {

    public String name;
    public String url;

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }

    public VideoUpload(String name, String url) {
        this.name = name;
        this.url = url;
    }

    public VideoUpload(){}
}
