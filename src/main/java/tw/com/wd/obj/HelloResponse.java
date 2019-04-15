package tw.com.wd.obj;

import com.google.gson.annotations.SerializedName;


public class HelloResponse {
    @SerializedName("m")
    private String meta;
    @SerializedName("c")
    private String content;


    public String getMeta() {
        return meta;
    }

    public HelloResponse setMeta(String meta) {
        this.meta = meta;
        return this;
    }

    public String getContent() {
        return content;
    }

    public HelloResponse setContent(String content) {
        this.content = content;
        return this;
    }
}
