package com.heroku.seiyu.source;

import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.rx.ReactiveCamel;
import rx.Observable;
import rx.observables.ConnectableObservable;

public abstract class ObservableRoute extends RouteBuilder {

  protected String sourceName;
  protected String start_route;
  protected String end_route;
  protected ObservableSource observableSource;

  public final void sendReceiveObservable(Observable sendObservable, CamelContext context, ObservableSources sources) {
    new ReactiveCamel(context).sendTo(sendObservable, start_route);
    receiveObservable(context, sources);
  }

  public final void receiveObservable(CamelContext context, ObservableSources sources) {
    ConnectableObservable observable = new ReactiveCamel(context).toObservable(end_route, Object.class).publish();
    observable.connect();
    observableSource = new ObservableSource(sourceName, observable);
    sources.put(sourceName, observableSource);
  }

  public final void setSourceName(String sourceName) {
    this.sourceName = sourceName;
    start_route = "direct:observable_" + sourceName + "_start";
    end_route = "direct:observable_" + sourceName + "_end";
  }

  public ObservableSource getObservableSource() {
    return this.observableSource;
  }
}
