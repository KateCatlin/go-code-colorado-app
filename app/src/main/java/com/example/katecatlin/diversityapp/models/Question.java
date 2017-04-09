
package com.example.katecatlin.diversityapp.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Question {

    @SerializedName("prompt")
    @Expose
    private String prompt;
    @SerializedName("response")
    @Expose
    private Response response;
    @SerializedName("followup")
    @Expose
    private List<Followup> followup = null;
    @SerializedName("server-key")
    @Expose
    private String serverKey;

    public String getPrompt() {
        return prompt;
    }

    public void setPrompt(String prompt) {
        this.prompt = prompt;
    }

    public Response getResponse() {
        return response;
    }

    public void setResponse(Response response) {
        this.response = response;
    }

    public List<Followup> getFollowup() {
        return followup;
    }

    public void setFollowup(List<Followup> followup) {
        this.followup = followup;
    }

    public String getServerKey() {
        return serverKey;
    }

    public void setServerKey(final String serverKey) {
        this.serverKey = serverKey;
    }

}
