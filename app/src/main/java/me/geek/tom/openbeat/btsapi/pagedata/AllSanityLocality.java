
package me.geek.tom.openbeat.btsapi.pagedata;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AllSanityLocality {

    @SerializedName("edges")
    @Expose
    public List<Edge> edges = null;

}
