package com.sacidpak.playground;

import com.sacidpak.playground.common.dto.BaseEntityDTO;
import com.sacidpak.playground.common.dto.PlayDTO;
import com.sacidpak.playground.common.repository.BaseRepositoryImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

@SpringBootApplication
@Import({com.sacidpak.playground.SwaggerConfig.class})
@EntityScan("com.sacidpak.playground.common.domain")
@EnableJpaRepositories(basePackages = "com.sacidpak.playground.common.repository", repositoryBaseClass = BaseRepositoryImpl.class)
public class PlaygroundCommonApplication {

	public static void main(String[] args) {
		SpringApplication.run(PlaygroundCommonApplication.class, args);
	}

}
