package com.example.micromasters.Entity;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonRootName;

@JsonRootName(value = "detail")
public class Detail {
    public Map<String, Object> detail;

    public Detail(Map<String, Object> detail) {
        this.detail = detail;
    }
}
