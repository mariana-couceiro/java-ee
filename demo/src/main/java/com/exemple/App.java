package com.exemple;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class App 
{
    public static void main( String[] args )
    {
        String json = "{\"id\":\"1\", \"nome\":\"Mariana\"}";

        ObjectMapper mapper = new ObjectMapper();

        JsonNode jsonNode = mapper.readTree(jsonString);

        System.out.println( "Hello World!" );
    }
}
