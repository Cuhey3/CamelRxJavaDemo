package com.heroku.seiyu.source.combined;

import com.heroku.seiyu.source.ObservableRoute;
import com.heroku.seiyu.source.ObservableSources;
import java.util.List;
import java.util.Map;
import org.apache.camel.CamelContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import rx.Observable;
import rx.observables.ConnectableObservable;

@Component
public class SourceG extends ObservableRoute {

  @Autowired
  public SourceG(ObservableSources sources, SourceE sourceE, CamelContext context) {
    setSourceName("source_g");
    ConnectableObservable<List<Map<String, Object>>> list1 = sourceE.getObservableSource().byMapList();
    ConnectableObservable<List<Map<String, Object>>> list2 = sources.get("male_seiyu_category_members").byMapList();
    Observable<List<Map<String, Object>>> combineLatest = Observable.combineLatest(list1, list2, (l1, l2) -> l1);
    sendReceiveObservable(combineLatest, context, sources);
  }

  @Override
  public void configure() throws Exception {
    from(start_route)
            .to("log:" + this.sourceName)
            .to(end_route);
  }

}
