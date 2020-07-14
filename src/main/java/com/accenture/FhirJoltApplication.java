package com.accenture;

import ca.uhn.fhir.context.FhirContext;
import com.accenture.service.AllergyIntoleranceService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class FhirJoltApplication {

    @Bean
    public FhirContext getFhirContext() {
        return FhirContext.forR4();

    }

    public static void main(String[] args) {
        //ApplicationContext ctx = null;
        AllergyIntoleranceService jolt = null;

        SpringApplication.run(FhirJoltApplication.class, args);
        //jolt = (AllergyIntoleranceService) ctx.getBean("allergyIntolerance");
        //System.out.println(jolt.getAllergyIntoleranceById("227493005"));
        //System.out.println(jolt.saveAllergyIntolerance());

    }

}
