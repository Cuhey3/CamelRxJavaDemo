package com.heroku.seiyu.source.polling;

import com.heroku.seiyu.source.ObservableRoute;
import com.heroku.seiyu.source.ObservableSources;
import org.apache.camel.CamelContext;
import org.apache.camel.Processor;

public class PollingObservableRoute extends ObservableRoute {

  String periodExpression;
  Processor processor;

  public PollingObservableRoute(String sourceName, String periodExpression, Processor processor, CamelContext context, ObservableSources sources) {
    setSourceName(sourceName);
    this.periodExpression = periodExpression;
    this.processor = processor;
    receiveObservable(context, sources);
  }

  @Override
  public void configure() throws Exception {
    fromF("timer:%s?period=%s", sourceName, periodExpression)
            .setHeader("sourceName", constant(sourceName))
            .process(processor)
            .to(end_route)
            .to("direct:toJson");
  }

}
