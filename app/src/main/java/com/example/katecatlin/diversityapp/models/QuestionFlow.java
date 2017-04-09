
package com.example.katecatlin.diversityapp.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class QuestionFlow {

    @SerializedName("version")
    @Expose
    private double version;
    @SerializedName("questions")
    @Expose
    private List<Question> questions = null;

    public double getVersion() {
        return version;
    }

    public void setVersion(double version) {
        this.version = version;
    }

    public List<Question> getData() {
        return questions;
    }

    public void setData(List<Question> data) {
        this.questions = data;
    }

}
