package com.chimbori.crux.common;

import org.junit.Test;

import java.net.MalformedURLException;
import java.net.URL;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class StringUtilsTest {
  @Test
  public void testInnerTrim() {
    assertEquals("", StringUtils.innerTrim("   "));
    assertEquals("t", StringUtils.innerTrim("  t "));
    assertEquals("t t t", StringUtils.innerTrim("t t t "));
    assertEquals("t t", StringUtils.innerTrim("t    \nt "));
    assertEquals("t peter", StringUtils.innerTrim("t  peter "));
    assertEquals("t t", StringUtils.innerTrim("t    \n     t "));
  }

  @Test
  public void testCount() {
    assertEquals(1, StringUtils.countMatches("hi wie &test; gehts", "&test;"));
    assertEquals(1, StringUtils.countMatches("&test;", "&test;"));
    assertEquals(2, StringUtils.countMatches("&test;&test;", "&test;"));
    assertEquals(2, StringUtils.countMatches("&test; &test;", "&test;"));
    assertEquals(3, StringUtils.countMatches("&test; test; &test; plu &test;", "&test;"));
  }

  @Test
  public void longestSubstring() {
//        assertEquals(9, ContentExtractor.longestSubstring("hi hello how are you?", "hello how"));
    assertEquals("hello how", StringUtils.getLongestSubstring("hi hello how are you?", "hello how"));
    assertEquals(" people if ", StringUtils.getLongestSubstring("x now if people if todo?", "I know people if you"));
    assertEquals("", StringUtils.getLongestSubstring("?", "people"));
    assertEquals("people", StringUtils.getLongestSubstring(" people ", "people"));
  }

  @Test
  public void testImageProtocolRelative() {
    String result;
    try {
      result = new URL(new URL("http://de.wikipedia.org/wiki/Griechenland"), "//upload.wikimedia.org/wikipedia/commons/thumb/5/5c/Flag_of_Greece.svg/150px-Flag_of_Greece.svg.png").toString();
    } catch (MalformedURLException e) {
      result = "//upload.wikimedia.org/wikipedia/commons/thumb/5/5c/Flag_of_Greece.svg/150px-Flag_of_Greece.svg.png";
    }
    assertEquals("http://upload.wikimedia.org/wikipedia/commons/thumb/5/5c/Flag_of_Greece.svg/150px-Flag_of_Greece.svg.png",
        result);
  }

  @Test
  public void testEncodingCleanup() {
    assertEquals("utf-8", StringUtils.encodingCleanup("utf-8"));
    assertEquals("utf-8", StringUtils.encodingCleanup("utf-8\""));
    assertEquals("utf-8", StringUtils.encodingCleanup("utf-8'"));
    assertEquals("test-8", StringUtils.encodingCleanup(" test-8 &amp;"));
  }

  @Test
  public void testEstimateDate() {
    assertNull(StringUtils.estimateDate("http://www.facebook.com/l.php?u=http%3A%2F%2Fwww.bet.com%2Fcollegemarketin"));
    assertEquals("2010/02/15", StringUtils.estimateDate("http://www.vogella.de/blog/2010/02/15/twitter-android/"));
    assertEquals("2010/02", StringUtils.estimateDate("http://www.vogella.de/blog/2010/02/twitter-android/12"));
    assertEquals("2009/11/05", StringUtils.estimateDate("http://cagataycivici.wordpress.com/2009/11/05/mobile-twitter-client-with-jsf/"));
    assertEquals("2009", StringUtils.estimateDate("http://cagataycivici.wordpress.com/2009/sf/12/1/"));
    assertEquals("2011/06", StringUtils.estimateDate("http://bdoughan.blogspot.com/2011/06/using-jaxbs-xmlaccessortype-to.html"));
    assertEquals("2011", StringUtils.estimateDate("http://bdoughan.blogspot.com/2011/13/using-jaxbs-xmlaccessortype-to.html"));
  }

  @Test
  public void testCompleteDate() {
    assertNull(StringUtils.completeDate(null));
    assertEquals("2001/01/01", StringUtils.completeDate("2001"));
    assertEquals("2001/11/01", StringUtils.completeDate("2001/11"));
    assertEquals("2001/11/02", StringUtils.completeDate("2001/11/02"));
  }

  @Test
  public void testMakeAbsoluteUrl() {
    assertEquals("http://example.com/test", StringUtils.makeAbsoluteUrl("http://example.com", "/test"));
    assertEquals("http://example.com/test", StringUtils.makeAbsoluteUrl("http\\3a //example.com", "/test"));
  }

  @Test
  public void testUnescapeBackslashHexUrl() {
    assertEquals(null, StringUtils.unescapeBackslashHex(null));
    assertEquals("", StringUtils.unescapeBackslashHex(""));
    assertEquals(":", StringUtils.unescapeBackslashHex("\\3a "));
    assertEquals("::", StringUtils.unescapeBackslashHex("\\3a \\3a "));
    assertEquals("=:", StringUtils.unescapeBackslashHex("\\3d \\3a "));
    assertEquals("https://scontent-sjc2-1.xx.fbcdn.net/v/t1.0-1/cp0/e15/q65/p120x120/00000000_00000000000000000_0000000000000000000_n.jpg?efg=aaaaaaaaaaaa&oh=aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa&oe=abcdefgh",
        StringUtils.unescapeBackslashHex("https\\3a //scontent-sjc2-1.xx.fbcdn.net/v/t1.0-1/cp0/e15/q65/p120x120/00000000_00000000000000000_0000000000000000000_n.jpg?efg\\3d aaaaaaaaaaaa\\26 oh\\3d aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa\\26 oe\\3d abcdefgh"));
  }
}
