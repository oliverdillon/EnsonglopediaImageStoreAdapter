package com.ensonglodpedia.adapters.ensonglopedia.image.store.adapter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class EnsonglopediaImageStoreAdapterApplication {

	public static void main(String[] args) throws ClassNotFoundException {
		Class.forName("org.postgresql.Driver");
		SpringApplication.run(EnsonglopediaImageStoreAdapterApplication.class, args);
	}

}
