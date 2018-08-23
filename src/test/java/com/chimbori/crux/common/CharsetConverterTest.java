package com.chimbori.crux.common;

import org.jsoup.Jsoup;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class CharsetConverterTest {
  @Test
  public void testDetermineEncoding() {
    assertEncodingEquals("shift_jis", "yomiuri.html");
    assertEncodingEquals("shift_jis", "yomiuri2.html");
    assertEncodingEquals("iso-8859-1", "spiegel.html");
    assertEncodingEquals("utf-8", "itunes.html");
    assertEncodingEquals("utf-8", "twitter.html");
    assertEncodingEquals("utf-8", "youtube.html");
    assertEncodingEquals("utf-8", "nyt.html");
    assertEncodingEquals("utf-8", "badenc.html");
    assertEncodingEquals("iso-8859-15", "br-online.html");
  }

  @Test
  public void testMaxBytesExceedingButGetTitleNevertheless() throws Exception {
    CharsetConverter.StringWithEncoding parsed = CharsetConverter.readStream(
        new FileInputStream(new File("test_data/bbc.html")));
    assertEquals("utf-8", parsed.encoding);
    assertEquals("Baby born on Mediterranean rescue ship - BBC News BBC News", Jsoup.parse(parsed.content).select("title").text());
  }

  private void assertEncodingEquals(String encoding, String testFile) {
    try {
      assertEquals(encoding, CharsetConverter.readStream(new FileInputStream(new File("test_data/" + testFile))).encoding);
    } catch (FileNotFoundException e) {
      fail(e.getMessage());
    }
  }
}
