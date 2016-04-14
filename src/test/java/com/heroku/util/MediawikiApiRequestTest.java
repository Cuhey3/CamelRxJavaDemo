/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.heroku.util;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class MediawikiApiRequestTest {

  public MediawikiApiRequestTest() {
  }

  @BeforeClass
  public static void setUpClass() {
  }

  @AfterClass
  public static void tearDownClass() {
  }

  @Before
  public void setUp() {
  }

  @After
  public void tearDown() {
  }

  /**
   * Test of getResultByMapList method, of class MediawikiApiRequest.
   *
   * @throws java.io.IOException
   */
  @Test
  public void testGetResultByMapList() throws IOException {
    System.out.println("getResultByMapList");
    MediawikiApiRequest instance = new MediawikiApiRequest();
    instance.setApiParam("action=query&list=categorymembers&cmtitle=Category:" + URLEncoder.encode("日本の女性声優", "UTF-8")
            + "&cmlimit=500&cmnamespace=0&format=xml&continue=&cmprop=title|ids|sortkeyprefix")
            .setListName("categorymembers")
            .setMapName("cm")
            .setContinueElementName("cmcontinue")
            .setIgnoreFields("ns");
    List<Map<String, Object>> resultByMapList = instance.getResultByMapList();
    assertNotEquals(resultByMapList, null);
    assertNotEquals(resultByMapList.size(), 0);
    assertEquals(resultByMapList.get(0) instanceof Map, true);
    assertEquals(resultByMapList.get(0).containsKey("ns"), false);
    System.out.println(resultByMapList);
  }
}
