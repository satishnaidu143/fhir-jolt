package com.accenture.controller;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.rest.annotation.Search;
import com.accenture.service.AllergyIntoleranceService;
import org.hl7.fhir.r4.model.AllergyIntolerance;
import org.hl7.fhir.r4.model.IdType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/fhir")
public class AllergyIntoleranceController {

    @Autowired
    private AllergyIntoleranceService service;

    @Autowired
    private FhirContext context;


    @RequestMapping(value = "/AllergyIntolerance",
            method = RequestMethod.POST, consumes = "application/json")
    public ResponseEntity<String> saveAllergyIntolerance(@RequestBody String inputBody) {

        String response = service.saveAllergyIntolerance(inputBody);
        return new ResponseEntity<String>(response, HttpStatus.CREATED);

    }

    @RequestMapping(value = "/AllergyIntolerance/{resourceId}",
            method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<String> getAllergyIntoleranceById(@PathVariable("resourceId") String resourceId) {
        String response = service.getAllergyIntoleranceById(resourceId);
        return new ResponseEntity<String>(response, HttpStatus.OK);
    }


    //custom fhir server call
    @RequestMapping(value = "/custom/AllergyIntolerance/{id}",
            method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<String> fetchAllergyIntoleranceById(@PathVariable("id") String id) {
        String allergyIntolerance = null;
        allergyIntolerance = service.fetchAllergyIntoleranceById(new IdType(id));
        return new ResponseEntity<String>(allergyIntolerance, HttpStatus.OK);
    }

    //custom fhir server call
    @RequestMapping(value = "/custom/AllergyIntolerance/{id}/_history/{vId}",
            method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<String> fetchAllergyIntoleranceByIdWithVersion(@PathVariable("id") String id,
                                                                         @PathVariable("vId") String version) {
        String allergyIntolerance = null;
        allergyIntolerance = service.fetchAllergyIntoleranceByIdWithVersion(new IdType(id), version);
        return new ResponseEntity<String>(allergyIntolerance, HttpStatus.OK);
    }

    //custom fhir server call
    @RequestMapping(value = "/custom/AllergyIntolerance", method = RequestMethod.GET,
            produces = "application/json")
    public ResponseEntity<String> fetchAllergyIntoleranceByCategory(@RequestParam("category") String category) {
        String allergyIntolerance = null;

        allergyIntolerance = service.fetchAllergyIntoleranceByCategory(category);
        return new ResponseEntity<String>(allergyIntolerance, HttpStatus.OK);

    }

    //custom fhir server call
    @RequestMapping(value = "/custom/AllergyIntolerance/patient", method = RequestMethod.GET,
            produces = "application/json")
    public ResponseEntity<String> fetchAllergyIntoleranceByPatientReference(@RequestParam("patient") String patient) {
        String allergyIntolerance = null;
        allergyIntolerance = service.fetchAllergyIntoleranceByPatientReference(patient);
        return new ResponseEntity<String>(allergyIntolerance, HttpStatus.OK);

    }
}
