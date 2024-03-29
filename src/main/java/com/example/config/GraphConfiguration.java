package com.example.config;

import com.example.builder.GraphBuilder;
import com.example.model.Node;
import com.example.validation.GraphValidator;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GraphConfiguration {

  @Bean
  public Map<String, Node> graph(final @Value("${graph}") String graph) {
    if (StringUtils.isNotBlank(graph)) {
      new GraphValidator().validate(null, graph);
    }
    return new GraphBuilder(graph).build();
  }
}
