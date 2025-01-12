package com.pjff.report_listener.repositories;

import com.pjff.report_listener.documents.ReportDocument;
import org.springframework.data.mongodb.repository.MongoRepository;

//Vid 86
public interface ReportRepository extends MongoRepository<ReportDocument, String> {
}

