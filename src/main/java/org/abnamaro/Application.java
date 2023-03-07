package org.abnamaro;

import org.abnamaro.recipes.index.Indexer;
import org.abnamaro.recipes.entities.Recipes;
import org.abnamaro.recipes.repository.RecipesRepository;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Arrays;
import java.util.List;

@SpringBootApplication
@EnableSwagger2
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public Docket productApi() {
        return new Docket(DocumentationType.SWAGGER_2).select()
                .apis(RequestHandlerSelectors.basePackage("org.abnamaro")).build().apiInfo(metaData());

    }

    private ApiInfo metaData() {
        return new ApiInfoBuilder().title("Spring Boot REST API")
                .description("\"Spring Boot REST API for greeting invoices list\"").version("1.0.0")
                .license("Apache License Version 2.0").licenseUrl("https://www.apache.org/licenses/LICENSE-2.0\"")
                .build();
    }

    @Bean
    public ApplicationRunner initializeData(RecipesRepository recipesRepository) throws Exception {
        return (ApplicationArguments args) -> {
            List<Recipes> plants = Arrays.asList(
                    new Recipes(false, 4, "egg, potato, rice, chicken" , "oven"),
                    new Recipes(true, 2, "egg, potato, rice, meat" , "oven"),
                    new Recipes(true, 5, "egg, potato, rice, salmon" , "air fryer"),
                    new Recipes(false, 4, "rib, bean" , "cooked"),
                    new Recipes(false, 4, "potato, fish" , "air fryer")
            );
            recipesRepository.saveAll(plants);
        };
    }

    @Bean
    public ApplicationRunner buildIndex(Indexer indexer) {
        return (ApplicationArguments args) -> {
            indexer.indexPersistedData("org.abnamaro.recipes.entities.Recipes");
        };
    }
}