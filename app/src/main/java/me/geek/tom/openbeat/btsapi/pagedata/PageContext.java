
package me.geek.tom.openbeat.btsapi.pagedata;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PageContext {

    @SerializedName("isCreatedByStatefulCreatePages")
    @Expose
    public Boolean isCreatedByStatefulCreatePages;
    @SerializedName("footerWithBackground")
    @Expose
    public Boolean footerWithBackground;
    @SerializedName("headerLogoShown")
    @Expose
    public Boolean headerLogoShown;
    @SerializedName("headerNavigationShown")
    @Expose
    public Boolean headerNavigationShown;
    @SerializedName("homepageId")
    @Expose
    public String homepageId;
    @SerializedName("showGameLinksInHeader")
    @Expose
    public Boolean showGameLinksInHeader;
    @SerializedName("headerBigLogoShown")
    @Expose
    public Boolean headerBigLogoShown;
    @SerializedName("shownInSiteMap")
    @Expose
    public Boolean shownInSiteMap;

}
