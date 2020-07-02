package com.wearewaes.hashcomparator;

import com.wearewaes.hashcomparator.service.HashCompareService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class HashComparatorApplication {

	private static HashCompareService hashCompareService = new HashCompareService();

	public static void main(String[] args) {
		SpringApplication.run(HashComparatorApplication.class, args);
	}

}
