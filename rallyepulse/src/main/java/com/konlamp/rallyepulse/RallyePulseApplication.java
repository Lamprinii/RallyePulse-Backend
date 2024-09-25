package com.konlamp.rallyepulse;

import com.konlamp.rallyepulse.model.Competitor;
import com.konlamp.rallyepulse.model.SpecialStage;
import com.konlamp.rallyepulse.repository.CompetitorRepository;
import com.konlamp.rallyepulse.repository.SpecialStageRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SpringBootApplication
public class RallyePulseApplication {

	public static void main(String[] args) {
		SpringApplication.run(RallyePulseApplication.class, args);
	}

	@Bean
	CommandLineRunner run (CompetitorRepository competitorRepository, SpecialStageRepository specialStageRepository) {
		return args ->{
			competitorRepository.save(new Competitor(1L,"Lamprini Zerva", "Konstantinos Perrakis", "rikoula4@gmail.com", "6957454125", "BMW E36", "C2", "A5"));
			specialStageRepository.save(new SpecialStage(1L, "Eleftherochori", 18.62F));


		};
	}

	@Bean
	public CorsFilter corsFilter() {
		CorsConfiguration corsConfiguration = new CorsConfiguration();
		corsConfiguration.setAllowCredentials(true);
		corsConfiguration.setAllowedOrigins(Arrays.asList(
						"http://localhost:3000",
						"https://accounts.google.com",
						"https://www.googleapis.com"
				)
		); 
		corsConfiguration.setAllowedHeaders(Arrays.asList("Origin", "Access-Control-Allow-Origin", "Content-Type",
				"Accept", "Authorization", "Origin, Accept", "X-Requested-With",
				"Access-Control-Request-Method", "Access-Control-Request-Headers"));
		corsConfiguration.setExposedHeaders(Arrays.asList("Origin", "Content-Type", "Accept", "Authorization",
				"Access-Control-Allow-Origin", "Access-Control-Allow-Origin", "Access-Control-Allow-Credentials"));
		corsConfiguration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
		UrlBasedCorsConfigurationSource urlBasedCorsConfigurationSource = new UrlBasedCorsConfigurationSource();
		urlBasedCorsConfigurationSource.registerCorsConfiguration("/**", corsConfiguration);
		return new CorsFilter(urlBasedCorsConfigurationSource);
	}

}

