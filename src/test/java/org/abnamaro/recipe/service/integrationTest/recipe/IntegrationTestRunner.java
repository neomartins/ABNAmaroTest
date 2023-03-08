package org.abnamaro.recipe.service.integrationTest.recipe;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(features = "src/test/resource/Features")
public class IntegrationTestRunner {
}
