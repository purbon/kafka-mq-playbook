package com.purbon.kafka.connect.transformations;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;
import org.apache.kafka.connect.data.Schema;
import org.apache.kafka.connect.data.SchemaBuilder;
import org.apache.kafka.connect.data.Struct;
import org.apache.kafka.connect.source.SourceRecord;
import org.junit.After;
import org.junit.Test;

public class SelectorRouterTest {

  private SelectorRouter<SourceRecord> router = new SelectorRouter<SourceRecord>();

  private final String ErrorHeaderField = "headers.error.field";
  private final String DLQ_TOPIC = "SelectorRouterTest.DLQ";

  @After
  public void teardown() {
    router.close();
  }

  @Test
  public void testTopicChangeInCaseOfFlaggedMessage() {

    String incomingTopicName = "incomingTopic";
    final Schema schema = SchemaBuilder
        .struct()
        .name("foo")
        .version(1)
        .doc("doc")
        .field("foo", Schema.STRING_SCHEMA)
        .build();

    Struct value = new Struct(schema);
    value.put("foo", "bar");

    SourceRecord record = new SourceRecord(null, null, incomingTopicName, 0, schema, value);
    record.headers().addBoolean(ErrorHeaderField, true);

    Map<String, String> config = new HashMap<String, String>();
    config.put(AbstractConnectConfig.ERROR_HEADER_FIELD_CONF, ErrorHeaderField);
    config.put(SelectorRouterConfig.DLQ_FIELD_CONF, DLQ_TOPIC);

    router.configure(config);

    SourceRecord newRecord = router.apply(record);

    assertEquals(DLQ_TOPIC, newRecord.topic());

  }

  @Test
  public void testTopicChangeInCaseOfNonFlaggedMessage() {

    String incomingTopicName = "incomingTopic";
    final Schema schema = SchemaBuilder
        .struct()
        .name("foo")
        .version(1)
        .doc("doc")
        .field("foo", Schema.STRING_SCHEMA)
        .build();

    Struct value = new Struct(schema);
    value.put("foo", "bar");

    SourceRecord record = new SourceRecord(null, null, incomingTopicName, 0, schema, value);

    Map<String, String> config = new HashMap<>();
    config.put(AbstractConnectConfig.ERROR_HEADER_FIELD_CONF, ErrorHeaderField);
    config.put(SelectorRouterConfig.DLQ_FIELD_CONF, DLQ_TOPIC);

    router.configure(config);

    SourceRecord newRecord = router.apply(record);

    assertNotEquals(DLQ_TOPIC, newRecord.topic());

  }
}
