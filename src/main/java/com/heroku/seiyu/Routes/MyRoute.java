package com.heroku.seiyu.Routes;

import java.util.LinkedHashMap;
import java.util.Map;
import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.springframework.stereotype.Component;

@Component
public class MyRoute extends RouteBuilder {

  Map<String, String> sourceNameToJsonString = new LinkedHashMap<>();

  public static Map<String, Object> staticResourceMap = new LinkedHashMap<>();
  public static String jettyEndpointUriPrefix = "jetty:http://0.0.0.0:" + System.getenv("PORT");
  //public static String jettyEndpointUriPrefix = "jetty:http://0.0.0.0:2525";

  @Override
  public void configure() throws Exception {
    from(jettyEndpointUriPrefix + "/static/?matchOnUriPrefix=true")
            .process((Exchange exchange) -> {
              String path = exchange.getIn().getHeader(Exchange.HTTP_PATH, String.class);
              exchange.getIn().setBody(staticResourceMap.get(path));
            });
    from("file:public?recursive=true&noop=true")
            .process((Exchange exchange) -> {
              String camelFileName = exchange.getIn().getHeader(Exchange.FILE_NAME, String.class).replace("\\", "/");
              staticResourceMap.put(camelFileName, exchange.getIn().getBody(Object.class));
            });
    from("direct:toJson").marshal().json(JsonLibrary.Jackson)
            .process((Exchange exchange) -> {
              sourceNameToJsonString.put(exchange.getIn().getHeader("sourceName", String.class), exchange.getIn().getBody(String.class));
            });
    from(jettyEndpointUriPrefix + "/api/?matchOnUriPrefix=true")
            .process((Exchange exchange) -> {
              String path = exchange.getIn().getHeader(Exchange.HTTP_PATH, String.class);
              String sourceName = path.replaceFirst(".+?/api/", "");
              exchange.getIn().setBody(sourceNameToJsonString.get(sourceName));
            });
  }
}
