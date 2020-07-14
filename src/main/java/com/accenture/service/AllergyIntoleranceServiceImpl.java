package com.accenture.service;

import ca.uhn.fhir.context.FhirContext;

import ca.uhn.fhir.model.api.Include;
import ca.uhn.fhir.parser.IParser;
import ca.uhn.fhir.rest.annotation.IncludeParam;
import ca.uhn.fhir.rest.api.MethodOutcome;
import ca.uhn.fhir.rest.client.api.IGenericClient;
import ca.uhn.fhir.rest.param.ReferenceParam;
import ca.uhn.fhir.rest.param.StringParam;
import com.accenture.custom.restfulclient.ICustomRestfulClient;
import com.bazaarvoice.jolt.Chainr;
import com.bazaarvoice.jolt.JsonUtils;
import org.hl7.fhir.instance.model.api.IIdType;
import org.hl7.fhir.r4.model.AllergyIntolerance;
import org.hl7.fhir.r4.model.Bundle;
import org.hl7.fhir.r4.model.IdType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service("allergyIntolerance")
public class AllergyIntoleranceServiceImpl implements AllergyIntoleranceService {

    private static final String FHIR_BASE_URL = "http://fhir.hl7fundamentals.org/r4";
    private static final String FHIR_CUSTOM_URL = "http://localhost:8080/hapi-fhir-jpaserver/fhir";

    @Autowired
    private FhirContext context;


    @Override
    public String saveAllergyIntolerance(String inputBody) {
        AllergyIntolerance allergyIntolerance = null;
        MethodOutcome outcome = null;
        IIdType id = null;
        String expectedOutput = null;
        IParser parser = context.newJsonParser();
        IGenericClient client = context.newRestfulGenericClient(FHIR_BASE_URL);

        expectedOutput = joltTransform(inputBody);
        allergyIntolerance = parser.parseResource(AllergyIntolerance.class, expectedOutput);
        outcome = client.create()
                .resource(allergyIntolerance)
                .prettyPrint()
                .encodedJson()
                .execute();

        allergyIntolerance = (AllergyIntolerance) outcome.getResource();
        id = outcome.getId();
        System.out.println("Got ID: " + id.getValue());
        return parser.setPrettyPrint(true).encodeResourceToString(allergyIntolerance);

    }

    @Override
    public String getAllergyIntoleranceById(String resourceId) {
        AllergyIntolerance allergyIntolerance = null;
        Bundle entries = null;
        IParser parser = context.newJsonParser();
        IGenericClient client = context.newRestfulGenericClient(FHIR_BASE_URL);

        allergyIntolerance = client.read()
                .resource(AllergyIntolerance.class)
                .withId(resourceId)
                .encodedJson()
                .execute();

        return parser.setPrettyPrint(true).encodeResourceToString(allergyIntolerance);

    }

    private String joltTransform(String inputBody) {
        List<Object> specJSON = JsonUtils.classpathToList("/fhir/spec.json");
        Chainr chainr = Chainr.fromSpec(specJSON);
        Object transformedOutput = chainr.transform(JsonUtils.jsonToObject(inputBody));
        return JsonUtils.toJsonString(transformedOutput);

    }


    //calling custom fhir server with IRestfulClient
    @Override
    public String fetchAllergyIntoleranceById(IdType idType) {
        ICustomRestfulClient iCustomRestfulClient = null;
        AllergyIntolerance allergyIntolerance = null;

        iCustomRestfulClient = context.newRestfulClient(ICustomRestfulClient.class, FHIR_CUSTOM_URL);
        allergyIntolerance = iCustomRestfulClient.fetchAllergyIntoleranceById(idType);
        return context.newJsonParser().setPrettyPrint(true).encodeResourceToString(allergyIntolerance);
    }

    //calling custom fhir server with IRestfulClient
    @Override
    public String fetchAllergyIntoleranceByIdWithVersion(IdType id, String version) {
        ICustomRestfulClient iCustomRestfulClient = null;
        AllergyIntolerance allergyIntolerance = null;

        iCustomRestfulClient = context.newRestfulClient(ICustomRestfulClient.class, FHIR_CUSTOM_URL);
        if (id.hasVersionIdPart()) {
            allergyIntolerance = iCustomRestfulClient.fetchAllergyIntoleranceById(id);
        }

        allergyIntolerance = iCustomRestfulClient.fetchAllergyIntoleranceById(id.withVersion(version));

        return context.newJsonParser().setPrettyPrint(true).encodeResourceToString(allergyIntolerance);

    }


    //calling custom fhir server with IRestfulClient
    @Override
    public String fetchAllergyIntoleranceByCategory(String category) {
        ICustomRestfulClient iCustomRestfulClient = null;
        Bundle entries = null;

        iCustomRestfulClient = context.newRestfulClient(ICustomRestfulClient.class, FHIR_CUSTOM_URL);
        entries = iCustomRestfulClient.
                fetchAllergyIntoleranceByCategory(new StringParam(category));
        return context.newJsonParser().setPrettyPrint(true).encodeResourceToString(entries);
    }

    //calling custom fhir server with IRestfulClient
    @Override
    public String fetchAllergyIntoleranceByPatientReference(String patient) {
        ICustomRestfulClient iCustomRestfulClient = null;
        Bundle entries = null;

        iCustomRestfulClient = context.newRestfulClient(ICustomRestfulClient.class, FHIR_CUSTOM_URL);
        entries = iCustomRestfulClient.
                fetchAllergyIntoleranceByPatientReference(new ReferenceParam(patient));
        return context.newJsonParser().setPrettyPrint(true).encodeResourceToString(entries);
    }


}
