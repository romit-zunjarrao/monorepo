package models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CountryLatest {
    @SerializedName("country")
    @Expose
    private String country;
    @SerializedName("code")
    @Expose
    private String code;
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
    @SerializedName("latitude")
    @Expose
    private Double latitude;
    @SerializedName("longitude")
    @Expose
    private Double longitude;
    @SerializedName("lastChange")
    @Expose
    private String lastChange;
    @SerializedName("lastUpdate")
    @Expose
    private String lastUpdate;

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

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

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
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

    @Override
    public String toString() {
        return "CountryLatest{" +
                "country='" + country + '\'' +
                ", code='" + code + '\'' +
                ", confirmed=" + confirmed +
                ", recovered=" + recovered +
                ", critical=" + critical +
                ", deaths=" + deaths +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", lastChange='" + lastChange + '\'' +
                ", lastUpdate='" + lastUpdate + '\'' +
                '}';
    }
}
