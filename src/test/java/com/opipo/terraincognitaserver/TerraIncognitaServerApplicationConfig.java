package com.opipo.terraincognitaserver;

import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.data.mongo.AutoConfigureDataMongo;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootConfiguration
@ComponentScan
@EnableMongoRepositories(basePackages = {"com.opipo.terraincognitaserver"})
@EnableAutoConfiguration
@AutoConfigureDataMongo
public class TerraIncognitaServerApplicationConfig {

}