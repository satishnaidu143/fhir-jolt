package com.accenture.custom.restfulclient;

import ca.uhn.fhir.model.api.Include;
import ca.uhn.fhir.rest.annotation.*;
import ca.uhn.fhir.rest.client.api.IRestfulClient;
import ca.uhn.fhir.rest.param.ReferenceParam;
import ca.uhn.fhir.rest.param.StringParam;
import org.hl7.fhir.r4.model.AllergyIntolerance;
import org.hl7.fhir.r4.model.Bundle;
import org.hl7.fhir.r4.model.IdType;

import java.util.List;
import java.util.Set;

public interface ICustomRestfulClient extends IRestfulClient {


    @Read(version = true,
            type = AllergyIntolerance.class)
    AllergyIntolerance fetchAllergyIntoleranceById(@IdParam IdType id);

    @Search(type = AllergyIntolerance.class)
    Bundle fetchAllergyIntoleranceByCategory(@RequiredParam(name =
            AllergyIntolerance.SP_CATEGORY) StringParam Cateogry);

    @Search(type = AllergyIntolerance.class)
    Bundle fetchAllergyIntoleranceByPatientReference(@RequiredParam(name =
            AllergyIntolerance.SP_PATIENT) ReferenceParam patient);
}
