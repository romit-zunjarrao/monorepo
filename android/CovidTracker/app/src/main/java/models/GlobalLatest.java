package models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GlobalLatest {

    @SerializedName("confirmed")
    @Expose
    private Integer confirmed;
    @SerializedName("recovered")
    @Expose
    private Integer recovered;
    @SerializedName("critical")
    @Expose
    private Integer critical;
    @SerializedName("deaths")
    @Expose
    private Integer deaths;
    @SerializedName("lastChange")
    @Expose
    private String lastChange;
    @SerializedName("lastUpdate")
    @Expose
    private String lastUpdate;

    public Integer getConfirmed() {
        return confirmed;
    }

    public void setConfirmed(Integer confirmed) {
        this.confirmed = confirmed;
    }

    public Integer getRecovered() {
        return recovered;
    }

    public void setRecovered(Integer recovered) {
        this.recovered = recovered;
    }

    public Integer getCritical() {
        return critical;
    }

    public void setCritical(Integer critical) {
        this.critical = critical;
    }

    public Integer getDeaths() {
        return deaths;
    }

    public void setDeaths(Integer deaths) {
        this.deaths = deaths;
    }

    public String getLastChange() {
        return lastChange;
    }

    public void setLastChange(String lastChange) {
        this.lastChange = lastChange;
    }

    public String getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(String lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

}
