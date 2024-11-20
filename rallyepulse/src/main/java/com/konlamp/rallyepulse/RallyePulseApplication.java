package com.konlamp.rallyepulse;

import com.konlamp.rallyepulse.model.*;
import com.konlamp.rallyepulse.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

@SpringBootApplication
public class RallyePulseApplication {

	public static void main(String[] args) {
		SpringApplication.run(RallyePulseApplication.class, args);
	}

	@Bean
	CommandLineRunner run (CompetitorRepository competitorRepository, SpecialStageRepository specialStageRepository, TimeKeepingRepository timeKeepingRepository, PenaltyRepository penaltyRepository, RallyeInformationRepository rallyeInformationRepository) {
		return args ->{
			rallyeInformationRepository.save(new RallyeInformation(1L, "Rallye Fthiotidos", "20/10/2024", "Lamia", false));
			LocalTime TEMP=LocalTime.now();
			LocalTime TEMP2=TEMP.minusNanos(TEMP.getNano()).minusMinutes(15);
			LocalTime TEMP3=TEMP.minusNanos(TEMP2.toNanoOfDay());
			competitorRepository.save(new Competitor(1L,"Lamprini Zerva", "Konstantinos Perrakis", "rikoula4@gmail.com", "6957454125", "BMW E36", "C2", "A5"));
			competitorRepository.save(new Competitor(2L,"Lamprini Zerva", "Konstantinos Perrakis", "test@gmail.com", "6957454125", "BMW E36", "C1", "A4"));
			//competitorRepository.save(new Competitor(42L,"Iliopoulos", "Test", "rikoula4@gmail.com", "6957454125", "Ford Fiesta", "C1", "A4"));
			specialStageRepository.save(new SpecialStage(1L, "Eleftherochori I", "18,62"));
			specialStageRepository.save(new SpecialStage(2L, "Eleftherochori II", "18,62"));
			penaltyRepository.save(new Penalty(1L,LocalTime.of(0,0,0,0)));
			penaltyRepository.save(new Penalty(2L,LocalTime.of(0,0,0,0)));
			int nanos1=816000000;
			int nanos2=952000000;
			int nanos3=125000000;
			int nanos4=456000000;
			timeKeepingRepository.save(new TimeKeeping(new TimeKeepingid(1L,1L), LocalTime.of(15,23,0,0), LocalTime.of(15,23,0,0).plusMinutes(15).plusSeconds(23).plusNanos(nanos3), LocalTime.of(00,15,23,0).plusNanos(nanos3)));
			timeKeepingRepository.save(new TimeKeeping(new TimeKeepingid(2L,1L), LocalTime.of(15,25,0,0), LocalTime.of(15,25,0,0).plusMinutes(16).plusSeconds(22).plusNanos(nanos1), LocalTime.of(00,16,22,0).plusNanos(nanos1)));
			timeKeepingRepository.save(new TimeKeeping(new TimeKeepingid(1L,2L), LocalTime.of(16,45,0,0),LocalTime.of(16,45,0,0).plusMinutes(17).plusSeconds(49).plusNanos(nanos4) , LocalTime.of(00,17,49,0).plusNanos(nanos4)));
			timeKeepingRepository.save(new TimeKeeping(new TimeKeepingid(2L,2L), LocalTime.of(16,47,0,0), LocalTime.of(16,47,0,0).plusMinutes(16).plusSeconds(10).plusNanos(nanos2), LocalTime.of(00,16,10,0).plusNanos(nanos2)));


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

