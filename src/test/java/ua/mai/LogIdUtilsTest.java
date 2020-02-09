package ua.mai;

import org.junit.jupiter.api.Test;
import ua.telesens.plu.log.StepLogRoot;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static ua.mai.LogIdUtils.charAfterRootName;


public class LogIdUtilsTest {

  @Test
  void test_0001_formatCurrDateTimeWithSeconds() {
    String currDate = LogIdUtils.formatCurrDateTimeWithSeconds();
    assertEquals(13, currDate.length());
  }

  @Test
  void test_0010_formatCurrDateTimeWithMilliSeconds() {
    String currDate = LogIdUtils.formatCurrDateTimeWithMilliSeconds();
    assertEquals(16, currDate.length());
  }

  @Test
  void test_0030_processPrefix() {
    String prefix = LogIdUtils.processPrefix(" p R e F i x  1 0   ", null);
    assertEquals("p r e f i x  1 0" + LogIdUtils.charAfterRootName, prefix);

    prefix = LogIdUtils.processPrefix(" p R e F i x  1 0   ", "default");
    assertEquals("p r e f i x  1 0" + LogIdUtils.charAfterRootName, prefix);

    prefix = LogIdUtils.processPrefix(null, null);
    assertEquals("", prefix);

    prefix = LogIdUtils.processPrefix("", null);
    assertEquals("", prefix);

    prefix = LogIdUtils.processPrefix("", "");
    assertEquals("", prefix);

    prefix = LogIdUtils.processPrefix("   ", "  ");
    assertEquals("  " + LogIdUtils.charAfterRootName, prefix);

    prefix = LogIdUtils.processPrefix(null, " defaul t ");
    assertEquals(" defaul t " + LogIdUtils.charAfterRootName, prefix);
  }

  @Test
  void test_0040_getIdForCurrDateTimeWithSeconds() {
    String id = LogIdUtils.getIdForCurrDateTimeWithSeconds(" p REF i x  1 0   ", " pp ");
    assertTrue(id.startsWith("p ref i x  1 0" + LogIdUtils.charAfterRootName));
    assertEquals(28, id.length());

    id = LogIdUtils.getIdForCurrDateTimeWithSeconds(null, " pp ");
    assertTrue(id.startsWith(" pp " + LogIdUtils.charAfterRootName));
    assertEquals(18, id.length());

    id = LogIdUtils.getIdForCurrDateTimeWithSeconds(null, null);
    assertFalse(id.contains(Character.toString(LogIdUtils.charAfterRootName)));
    assertEquals(13, id.length());
  }

  @Test
  void test_0050_getIdForCurrDateTimeWithMilliSeconds() {
    String id = LogIdUtils.getIdForCurrDateTimeWithMilliSeconds(" p REF i x  1 0   ", " pp ");
    assertTrue(id.startsWith("p ref i x  1 0" + LogIdUtils.charAfterRootName));
    assertEquals(31, id.length());

    id = LogIdUtils.getIdForCurrDateTimeWithMilliSeconds(null, " pp ");
    assertTrue(id.startsWith(" pp " + LogIdUtils.charAfterRootName));
    assertEquals(21, id.length());

    id = LogIdUtils.getIdForCurrDateTimeWithMilliSeconds(null, null);
    assertFalse(id.contains(Character.toString(LogIdUtils.charAfterRootName)));
    assertEquals(16, id.length());
  }

  @Test
  void test_0060_getDateTimeFromStringWithSeconds() {
    LocalDateTime dateTime = LogIdUtils.getDateTimeFromStringWithSeconds("200130:170729");
    assertEquals(LocalDateTime.of(2020, 1, 30, 17, 7, 29), dateTime);
  }

  @Test
  void test_0070_getDateTimeFromStringWithMilliSeconds() {
    LocalDateTime dateTime = LogIdUtils.getDateTimeFromStringWithMilliSeconds("200130:170729835");
    assertEquals(LocalDateTime.of(2020, 1, 30, 17, 7, 29, 835_000_000), dateTime);
  }

  @Test
  void test_0075_formatDuration() {
    String duration = LogIdUtils.formatDuration(
        LocalDateTime.of(2020, 1, 30, 0, 0, 0, 0),
        LocalDateTime.of(2020, 1, 30, 1, 1, 1, 1_000_000));
    assertEquals("10101001", duration);
  }

  @Test
  void test_0080_formatDurationForCurrDateTime() {
    LocalDateTime dateTime = LocalDateTime.now();
    String duration = LogIdUtils.formatDurationForCurrDateTime(dateTime);
    assertEquals(8, duration.length());
  }

  @Test
  void test_0090_getStringAfterPrefix() {
    String str = LogIdUtils.getStringAfterPrefix(" 245 JJJ " + charAfterRootName + " y 8 + ?? ");
    assertEquals(" y 8 + ?? ", str);

    str = LogIdUtils.getStringAfterPrefix(" 245 JJJ " + charAfterRootName + " y 8 + ?? "+ charAfterRootName + "ss s");
    assertEquals(" y 8 + ?? "+ charAfterRootName + "ss s", str);

    str = LogIdUtils.getStringAfterPrefix(null);
    assertEquals(null, str);

    str = LogIdUtils.getStringAfterPrefix("");
    assertEquals("", str);

    str = LogIdUtils.getStringAfterPrefix(" 245 JJJ y 8 + ?? ");
    assertEquals(" 245 JJJ y 8 + ?? ", str);
  }

  @Test
  void test_0100_getDateTimeFromIdWithSecondsAfterPrefix() {
    LocalDateTime dateTime =
        LogIdUtils.getDateTimeFromIdWithSecondsAfterPrefix(" 245 JJJ " + charAfterRootName + "200130:170729");
    assertEquals(LocalDateTime.of(2020, 1, 30, 17,07, 29), dateTime);

    dateTime = LogIdUtils.getDateTimeFromIdWithSecondsAfterPrefix(" 245 JJJ " + charAfterRootName + "200130: 170729");
    assertEquals(null, dateTime);

    dateTime = LogIdUtils.getDateTimeFromIdWithSecondsAfterPrefix("200130:170729");
    assertEquals(LocalDateTime.of(2020, 1, 30, 17,07, 29), dateTime);
  }

  @Test
  void test_0110_getDateTimeFromIdWithMilliSecondsAfterPrefix() {
    LocalDateTime dateTime =
      LogIdUtils.getDateTimeFromIdWithMilliSecondsAfterPrefix(" 245 JJJ " + charAfterRootName + "200130:170729001");
    assertEquals(LocalDateTime.of(2020, 1, 30, 17,07, 29, 1_000_000),
                 dateTime);

    dateTime = LogIdUtils.getDateTimeFromIdWithMilliSecondsAfterPrefix(" 245 JJJ " + charAfterRootName + "200130: 170729001");
    assertEquals(null, dateTime);

    dateTime = LogIdUtils.getDateTimeFromIdWithMilliSecondsAfterPrefix("200130:170729001");
    assertEquals(LocalDateTime.of(2020, 1, 30, 17,07, 29, 1_000_000),
                 dateTime);
  }

  @Test
  void test_0120_getRootIdFromMdc() {
    String rootId = LogIdUtils.getRootIdFromMdc();
    assertEquals(null, rootId);

    StepLogRoot.putRootIdInMdc("root_id");
    rootId = LogIdUtils.getRootIdFromMdc();
    assertEquals("root_id", rootId);
  }

  @Test
  void test_0130_createRootId() {
    String rootId = LogIdUtils.createRootId(" root_id ");
    assertTrue(rootId.startsWith("root_id" + charAfterRootName), rootId);
    assertEquals(21, rootId.length());

    rootId = LogIdUtils.createRootId(null);
    assertTrue(rootId.startsWith(LogIdUtils.DEFAULT_NAME_ROOT + charAfterRootName));
    assertEquals(18, rootId.length());
  }

  @Test
  void test_0140_getRequestIdFromMdc() {
    StepLogRoot.putRequestIdInMdc(null);
    String requestId = LogIdUtils.getRequestIdFromMdc();
    assertEquals(null, requestId);

    StepLogRoot.putRequestIdInMdc("request_id");
    requestId = LogIdUtils.getRequestIdFromMdc();
    assertEquals("request_id", requestId);
  }

  @Test
  void test_0140_createRequestId() {
    String requestId = LogIdUtils.createRequestId(" req_id ");
    assertTrue(requestId.startsWith("req_id" + charAfterRootName), requestId);
    assertEquals(23, requestId.length());

    requestId = LogIdUtils.createRequestId(null);
    assertTrue(requestId.startsWith(LogIdUtils.DEFAULT_NAME_REQUEST + charAfterRootName));
    assertEquals(24, requestId.length());
  }

  @Test
  void test_0145_createDefaultRequestId() {
    String requestId = LogIdUtils.createDefaultRequestId();
    assertTrue(requestId.startsWith(LogIdUtils.DEFAULT_NAME_REQUEST + charAfterRootName));
    assertEquals(24, requestId.length());
  }

  @Test
  void test_0150_getJobIdFromMdc() {
    String jobId = LogIdUtils.getJobIdFromMdc();
    assertEquals(null, jobId);

    StepLogRoot.putJobIdInMdc("job_id");
    jobId = LogIdUtils.getJobIdFromMdc();
    assertEquals("job_id", jobId);
  }

  @Test
  void test_0160_createJobId() {
    String jobId = LogIdUtils.createJobId();
    assertEquals(36, jobId.length());

    String requestId = LogIdUtils.createRequestId("test");
    StepLogRoot.putRequestIdInMdc(requestId);
    jobId = LogIdUtils.createJobId();
    assertTrue(jobId.startsWith(requestId + LogIdUtils.charAfterRequestId));
    assertEquals(30, jobId.length());
  }

  @Test
  void test_0160_createDetailId() {
    String detailId = LogIdUtils.createDetailId();
    assertEquals(36, detailId.length());
  }

}
