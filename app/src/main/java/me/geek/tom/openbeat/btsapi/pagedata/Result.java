
package me.geek.tom.openbeat.btsapi.pagedata;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Result {

    @SerializedName("data")
    @Expose
    public Data data;
    @SerializedName("pageContext")
    @Expose
    public PageContext pageContext;

}
