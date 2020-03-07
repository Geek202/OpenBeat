
package me.geek.tom.openbeat.btsapi.pagedata;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PageData {

    @SerializedName("componentChunkName")
    @Expose
    public String componentChunkName;
    @SerializedName("path")
    @Expose
    public String path;
    @SerializedName("webpackCompilationHash")
    @Expose
    public String webpackCompilationHash;
    @SerializedName("result")
    @Expose
    public Result result;

}
