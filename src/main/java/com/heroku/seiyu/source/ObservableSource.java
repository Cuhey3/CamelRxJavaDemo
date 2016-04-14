package com.heroku.seiyu.source;

import java.util.List;
import java.util.Map;
import java.util.Set;
import rx.observables.ConnectableObservable;

public class ObservableSource {

  public ObservableSource(String sourceName, ConnectableObservable observable) {
    this.observable = observable;
    this.sourceName = sourceName;
  }

  protected ConnectableObservable observable;
  protected String sourceName;

  public final ConnectableObservable<List<Map<String, Object>>> byMapList() {
    return (ConnectableObservable<List<Map<String, Object>>>) observable;
  }

  public final ConnectableObservable<Set<String>> byStringSet() {
    return (ConnectableObservable<Set<String>>) observable;
  }
}
