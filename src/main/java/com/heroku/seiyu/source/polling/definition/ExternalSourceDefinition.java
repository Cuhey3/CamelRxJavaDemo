package com.heroku.seiyu.source.polling.definition;

import com.heroku.seiyu.entity.KoepotaEvent;
import java.util.List;
import java.util.stream.Collectors;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public enum ExternalSourceDefinition{
  koepota_events("1h", (Exchange exchange) -> {
    Document doc = Jsoup.connect("http://www.koepota.jp/eventschedule/").maxBodySize(Integer.MAX_VALUE).timeout(Integer.MAX_VALUE).get();
    doc.select("#eventschedule tr:eq(0)").remove();
    Elements select = doc.select("#eventschedule tr");
    List<org.bson.Document> collect = select.stream().map((el) -> {
      return new KoepotaEvent(
              el.select("td.title a").attr("href").replace("http://www.koepota.jp/eventschedule/", ""),
              el.select("td.day").get(0).text(),
              el.select("td.title").text(),
              el.select("td.hall").text(),
              el.select("td.number").text(),
              el.select("td.day").get(1).text()
      ).getDocument();
    }).collect(Collectors.toList());
    exchange.getIn().setBody(collect);
  });
  private final String periodExpression;
  private final Processor processor;

  private ExternalSourceDefinition(String periodExpression, Processor processor) {
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
