package org.abnamaro.recipe.service.integrationTest.runner;

import org.abnamaro.Application;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.RestTemplate;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(
        classes = Application.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public abstract class SpringIntegrationTest {
    protected RestTemplate restTemplate = new RestTemplate();
    protected final String DEFAULT_URL = "http://localhost:9000/";

}
