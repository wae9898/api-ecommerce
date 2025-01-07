package com.apex.apiecommerce.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class NasaApodResponse {
    private String date;

    private String explanation;

    private String hdurl;

    @JsonProperty("media_type")
    private String mediaType;

    @JsonProperty("service_version")
    private String serviceVersion;

    private String title;

    private String url;
}
