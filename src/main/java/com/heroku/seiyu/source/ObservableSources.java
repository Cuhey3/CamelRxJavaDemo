package com.heroku.seiyu.source;

import java.util.LinkedHashMap;
import org.springframework.stereotype.Component;

@Component
public class ObservableSources extends LinkedHashMap<String, ObservableSource> {

  public ObservableSource getObservable(String key) {
    return this.get(key);
  }
}
