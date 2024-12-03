package com.spring_cloud.eureka.client.product;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SearchRequestDto {

    private String keyword;
    private int pageNumber;
    private boolean isAsc;

    public SearchRequestDto(String keyword, int pageNumber, boolean isAsc) {
        this.keyword = keyword;
        this.pageNumber = pageNumber;
        this.isAsc = isAsc;
    }

}
