//Create a new class to map table_id to JPA entities, enabling dynamic queries for any table. Place in src/main/java/com/example/metadata_service/config.

package com.example.metadata_service.config;

import com.example.metadata_service.entity.Employee;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class EntityRegistry {

    @Bean
    public Map<Integer, Class<?>> tableEntityMap() {
        Map<Integer, Class<?>> map = new HashMap<>();
        map.put(3004, Employee.class); // employees table
        // Add future tables, e.g., map.put(3005, Department.class);
        return map;
    }
}