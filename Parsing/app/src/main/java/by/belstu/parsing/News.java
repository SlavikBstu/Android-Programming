package by.belstu.parsing;

import android.net.Uri;

import java.net.URL;

/**
 * Created by Владислав on 07.12.2016.
 */
public class News {
    private String content;
    private Uri url;

    public News(String content, Uri url) {
        this.content = content;
        this.url = url;
    }

    public String getContent() {
        return content;
    }

    public Uri getUrl() {
        return url;
    }
}
