package com.scribd.search.pills;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import java.util.List;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PillSuggestions {

    private List<PillObject> pillObjects;

    private int status;

    public List<PillObject> getPillObjects() {
        return pillObjects;
    }

    public void setPillObjects(List<PillObject> pillObjects) {
        this.pillObjects = pillObjects;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "PillSuggestions{" +
                "pillObjects=" + pillObjects +
                ", status=" + status +
                '}';
    }
}
