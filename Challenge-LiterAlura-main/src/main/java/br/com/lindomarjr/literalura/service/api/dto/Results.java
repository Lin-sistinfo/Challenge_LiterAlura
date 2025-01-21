package br.com.lindomarjr.literalura.service.api.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record Results(Long id,
                      String title,
                      List<AuthorsData> authors,
                      List<String> languages,
                      @JsonProperty("download_count")
                      Long downloadCount) {
}
