package com.accenture.service;


import org.hl7.fhir.instance.model.api.IIdType;
import org.hl7.fhir.r4.model.IdType;

public interface AllergyIntoleranceService {

    String getAllergyIntoleranceById(String resourceId);

    String saveAllergyIntolerance(String inputBody);

    String fetchAllergyIntoleranceByIdWithVersion(IdType id, String version);

    String fetchAllergyIntoleranceById(IdType idType);

    String fetchAllergyIntoleranceByCategory(String category);

    String fetchAllergyIntoleranceByPatientReference(String patient);


}
