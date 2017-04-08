
package com.example.katecatlin.diversityapp.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FollowupQuestion {

    @SerializedName("prompt")
    @Expose
    private String prompt;
    @SerializedName("response")
    @Expose
    private Response response;

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

}
