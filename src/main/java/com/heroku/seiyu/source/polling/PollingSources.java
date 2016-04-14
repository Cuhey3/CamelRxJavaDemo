package com.heroku.seiyu.source.polling;

import com.heroku.seiyu.source.ObservableSources;
import com.heroku.seiyu.source.polling.definition.CommonMediawikiApiSourceDefinition;
import com.heroku.seiyu.source.polling.definition.ExternalSourceDefinition;
import org.apache.camel.CamelContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PollingSources {

  @Autowired
  public PollingSources(CamelContext context, ObservableSources sources) throws Exception {
    System.out.println("pollingSources initializing..." + sources);
    for (CommonMediawikiApiSourceDefinition definition : CommonMediawikiApiSourceDefinition.values()) {
      context.addRoutes(new PollingObservableRoute(definition.name(), definition.getPeriodExpression(), definition.getProcessor(), context, sources));
    }
    for (ExternalSourceDefinition definition : ExternalSourceDefinition.values()) {
      context.addRoutes(new PollingObservableRoute(definition.name(), definition.getPeriodExpression(), definition.getProcessor(), context, sources));
    }
    System.out.println("pollingSources initialized." + sources);
  }
}
