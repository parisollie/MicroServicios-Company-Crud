package com.pjff.report_listener.documents;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

//Vid 86
@Document
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReportDocument {

    @Id
    public String id;
    private String content;
}
