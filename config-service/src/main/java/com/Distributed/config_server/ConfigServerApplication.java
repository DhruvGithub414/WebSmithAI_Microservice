package com.Distributed.config_server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

@SpringBootApplication
@EnableConfigServer
public class ConfigServerApplication {

	public static void main(String[] args) {
		loadDotEnv();
		SpringApplication.run(ConfigServerApplication.class, args);
	}

	private static void loadDotEnv() {
		List<Path> candidates = List.of(
				Path.of(".env"),
				Path.of("config-service", ".env")
		);

		for (Path candidate : candidates) {
			if (!Files.exists(candidate)) {
				continue;
			}

			try {
				for (String line : Files.readAllLines(candidate)) {
					String trimmed = line.trim();
					if (trimmed.isEmpty() || trimmed.startsWith("#")) {
						continue;
					}

					int separator = trimmed.indexOf('=');
					if (separator <= 0) {
						continue;
					}

					String key = trimmed.substring(0, separator).trim();
					String value = trimmed.substring(separator + 1).trim();

					if (System.getenv(key) == null && System.getProperty(key) == null) {
						System.setProperty(key, value);
					}
				}
				return;
			}
			catch (IOException ignored) {
				// Skip unreadable .env file and continue with other configuration sources.
			}
		}
	}

}
