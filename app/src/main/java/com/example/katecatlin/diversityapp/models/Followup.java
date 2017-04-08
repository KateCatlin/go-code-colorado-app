
package com.example.katecatlin.diversityapp.models;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Followup {

    @SerializedName("matched-response")
    @Expose
    private String matchedResponse;
    @SerializedName("followup-questions")
    @Expose
    private List<FollowupQuestion> followupQuestions = null;

    public String getMatchedResponse() {
        return matchedResponse;
    }

    public void setMatchedResponse(String matchedResponse) {
        this.matchedResponse = matchedResponse;
    }

    public List<FollowupQuestion> getFollowupQuestions() {
        return followupQuestions;
    }

    public void setFollowupQuestions(List<FollowupQuestion> followupQuestions) {
        this.followupQuestions = followupQuestions;
    }

}
