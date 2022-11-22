package org.jaguirre.springcloud.msvc.usuarios.models.dto;

import java.io.Serializable;

public class PlaceSearch implements Serializable {

    private static final long serialVersionUID = 4676905652686424400L;

    private int responseCode;
    private String description;
    private ResultSearch result;

    public int getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(int responseCode) {
        this.responseCode = responseCode;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ResultSearch getResult() {
        return result;
    }

    public void setResult(ResultSearch result) {
        this.result = result;
    }
}
