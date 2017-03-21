package org.notes.core.rest;

import graphql.ExecutionResult;
import graphql.GraphQL;
import graphql.schema.*;
import org.notes.core.service.NoteBookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

import static graphql.Scalars.GraphQLLong;
import static graphql.Scalars.GraphQLString;
import static graphql.schema.GraphQLFieldDefinition.newFieldDefinition;
import static graphql.schema.GraphQLObjectType.newObject;
import static graphql.schema.GraphQLSchema.newSchema;

/**
 * @author Timur Nasibullin
 * @since 3/21/2017
 */
@RestController
public class GraphQLController {
    @Autowired
    private NoteBookRepository noteBookRepository;

    private GraphQLObjectType noteObjectType = newObject()
            .name("Note")
            .field(newFieldDefinition()
                    .name("id")
                    .type(GraphQLLong)
                    .dataFetcher(new PropertyDataFetcher("id"))
            )
            .field(newFieldDefinition()
                    .name("header")
                    .type(GraphQLString)
                    .dataFetcher(new PropertyDataFetcher("header"))
            )
            .field(newFieldDefinition()
                    .name("body")
                    .type(GraphQLString)
                    .dataFetcher(new PropertyDataFetcher("body"))
            )
            .build();

    private GraphQLObjectType notebookObjectType = newObject()
            .name("Notebook")
            .field(newFieldDefinition()
                    .name("id")
                    .type(GraphQLLong)
                    .dataFetcher(new PropertyDataFetcher("id"))
            )
            .field(newFieldDefinition()
                    .name("name")
                    .type(GraphQLString)
                    .dataFetcher(new PropertyDataFetcher("name"))
            )
            .field(newFieldDefinition()
                    .name("notes")
                    .type(new GraphQLList((noteObjectType)))
                    .dataFetcher(new PropertyDataFetcher("notes"))
            )
            .build();
    private GraphQLObjectType notebookQueryType = newObject()
            .name("NotebookQuery")
            .field(newFieldDefinition()
                    .name("notebook")
                    .type(new GraphQLList(notebookObjectType))
                    .dataFetcher(new DataFetcher() {
                        @Override
                        public Object get(DataFetchingEnvironment environment) {
                            return noteBookRepository.findAll();
                        }
                    })
            )
            .build();
    private GraphQLSchema schema = newSchema()
            .query(notebookQueryType)
            .build();

    private GraphQL graphQL = new GraphQL(schema);

    @PostMapping(value = "graphql", produces = MediaType.APPLICATION_JSON_VALUE)
    public Object execute(@RequestBody Map body) {
        ExecutionResult executionResult = graphQL.execute((String) body.get("query"));
        Map<String, Object> result = new HashMap<>();
        if (executionResult.getErrors().size() > 0) {
            result.put("errors", executionResult.getErrors());
        }
        result.put("data", executionResult.getData());
        return result;
    }
}
