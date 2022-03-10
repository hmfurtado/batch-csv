package com.hmfurtado.csv;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class TitleDTO {

    private String titleId;
    private Integer ordering;
    private String title;
    private String region;
    private String language;
    private String types;
    private String attributes;
    private String isOriginalTitle;
}