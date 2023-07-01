package com.example.modulesrecommender.models;

import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Property;

@Node
public class Topic {

    @Id
    private String name;

    public Topic(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
