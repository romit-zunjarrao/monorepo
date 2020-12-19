package models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CountryDaily {
    @SerializedName("country")
    @Expose
    private String country;
    @SerializedName("provinces")
    @Expose
    private List<Province> provinces = null;
    @SerializedName("latitude")
    @Expose
    private Double latitude;
    @SerializedName("longitude")
    @Expose
    private Double longitude;
    @SerializedName("date")
    @Expose
    private String date;

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public List<Province> getProvinces() {
        return provinces;
    }

    public void setProvinces(List<Province> provinces) {
        this.provinces = provinces;
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }


    public class Province {
        @SerializedName("province")
        @Expose
        private String province;
        @SerializedName("confirmed")
        @Expose
        private Integer confirmed;
        @SerializedName("recovered")
        @Expose
        private Integer recovered;
        @SerializedName("deaths")
        @Expose
        private Integer deaths;
        @SerializedName("active")
        @Expose
        private Integer active;

        public String getProvince() {
            return province;
        }

        public void setProvince(String province) {
            this.province = province;
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

        public Integer getDeaths() {
            return deaths;
        }

        public void setDeaths(Integer deaths) {
            this.deaths = deaths;
        }

        public Integer getActive() {
            return active;
        }

        public void setActive(Integer active) {
            this.active = active;
        }
    }
}
