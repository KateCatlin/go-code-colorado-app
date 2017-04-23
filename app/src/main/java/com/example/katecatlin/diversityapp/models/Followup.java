
package com.example.katecatlin.diversityapp.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Followup {

    @SerializedName("matched-response")
    @Expose
    private String matchedResponse;
    @SerializedName("questions")
    @Expose
    private List<Question> questions = null;

    public String getMatchedResponse() {
        return matchedResponse;
    }

    public void setMatchedResponse(String matchedResponse) {
        this.matchedResponse = matchedResponse;
    }

    public List<Question> getFollowupQuestions() {
        return questions;
    }

    public void setFollowupQuestions(List<Question> followupQuestions) {
        this.questions = followupQuestions;
    }

}
