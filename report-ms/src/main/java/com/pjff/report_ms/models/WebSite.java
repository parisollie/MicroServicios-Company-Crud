package com.pjff.report_ms.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

//V-46, copiamos la mayoria de los archivos
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WebSite implements Serializable {
    // V-56, solo se queda con el name
    private String name;

}
