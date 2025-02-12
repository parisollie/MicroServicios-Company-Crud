package com.pjff.companies_crud_fallback;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

//V-67 ,se copian tal cual
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WebSite implements Serializable {

    private String name;
}

