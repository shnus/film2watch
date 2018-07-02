package com.shnus.film2watch;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.sql.DriverManager;
import java.sql.SQLException;

@SpringBootApplication
public class Film2watchApplication {
	public static void main(String[] args) throws SQLException {
		DriverManager.getConnection("jdbc:mysql://localhost:3306/film2watch?" +
				"user=root&password=5555");
		SpringApplication.run(Film2watchApplication.class, args);
	}
}
