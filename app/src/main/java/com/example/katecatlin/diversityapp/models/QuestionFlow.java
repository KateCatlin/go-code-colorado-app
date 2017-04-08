
package com.example.katecatlin.diversityapp.models;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class QuestionFlow {

    @SerializedName("version")
    @Expose
    private double version;
    @SerializedName("data")
    @Expose
    private List<Datum> data = null;

    public double getVersion() {
        return version;
    }

    public void setVersion(double version) {
        this.version = version;
    }

    public List<Datum> getData() {
        return data;
    }

    public void setData(List<Datum> data) {
        this.data = data;
    }

}
