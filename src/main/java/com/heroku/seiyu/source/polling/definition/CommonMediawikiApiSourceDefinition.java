package com.heroku.seiyu.source.polling.definition;

import com.heroku.util.MediawikiApiRequest;
import java.util.List;
import java.util.Map;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;

public enum CommonMediawikiApiSourceDefinition {
  female_seiyu_category_members("27m", (Exchange exchange) -> {
    List<Map<String, Object>> mapList = new MediawikiApiRequest()
            .setApiParam("action=query&list=categorymembers&cmtitle=Category:%E6%97%A5%E6%9C%AC%E3%81%AE%E5%A5%B3%E6%80%A7%E5%A3%B0%E5%84%AA&cmlimit=500&cmnamespace=0&format=xml&continue=&cmprop=title|ids|sortkeyprefix")
            .setListName("categorymembers").setMapName("cm").setContinueElementName("cmcontinue")
            .setIgnoreFields("ns")
            .getResultByMapList();
    mapList.forEach((m) -> m.put("gender", "f"));
    exchange.getIn().setBody(mapList);
  }),
  male_seiyu_category_members("28m", (Exchange exchange) -> {
    List<Map<String, Object>> mapList = new MediawikiApiRequest()
            .setApiParam("action=query&list=categorymembers&cmtitle=Category:%E6%97%A5%E6%9C%AC%E3%81%AE%E7%94%B7%E6%80%A7%E5%A3%B0%E5%84%AA&&cmlimit=500&cmnamespace=0&format=xml&continue=&cmprop=title|ids|sortkeyprefix")
            .setListName("categorymembers").setMapName("cm").setContinueElementName("cmcontinue")
            .setIgnoreFields("ns")
            .getResultByMapList();
    mapList.forEach((m) -> m.put("gender", "m"));
    exchange.getIn().setBody(mapList);
  }),
  seiyu_template_include_pages("29m", (Exchange exchange) -> {
    exchange.getIn().setBody(new MediawikiApiRequest()
            .setApiParam("action=query&list=backlinks&bltitle=Template:%E5%A3%B0%E5%84%AA&format=xml&bllimit=500&blnamespace=0&continue=")
            .setListName("backlinks")
            .setMapName("bl")
            .setContinueElementName("blcontinue")
            .setIgnoreFields("ns")
            .getResultByMapList());
  });
  private final String periodExpression;
  private final Processor processor;

  private CommonMediawikiApiSourceDefinition(String periodExpression, Processor processor) {
    this.periodExpression = periodExpression;
    this.processor = processor;
  }

  public String getSourceName() {
    return this.name();
  }

  public String getPeriodExpression() {
    return this.periodExpression;
  }

  public Processor getProcessor() {
    return this.processor;
  }
}
