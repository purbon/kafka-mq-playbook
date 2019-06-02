package com.purbon.kafka.connect.transformations;

import java.util.Map;
import org.apache.kafka.common.config.ConfigDef;
import org.apache.kafka.connect.connector.ConnectRecord;
import org.apache.kafka.connect.header.Header;
import org.apache.kafka.connect.transforms.Transformation;

public class SelectorRouter<R extends ConnectRecord<R>> implements Transformation<R> {

  private SelectorRouterConfig config;

  public R apply(R record) {

    Header header = record.headers().lastWithName(config.getErrorHeaderField());

    if ((Boolean)header.value()) {
      return record.newRecord(config.getDLQTopicName(), record.kafkaPartition(), record.keySchema(),
          record.key(), record.valueSchema(), record.value(), record.timestamp());
    } else {
      return record;
    }
  }

  public ConfigDef config() {
    return SelectorRouterConfig.config();
  }

  public void close() {

  }

  public void configure(Map<String, ?> map) {
    this.config = new SelectorRouterConfig(map);
  }
}
