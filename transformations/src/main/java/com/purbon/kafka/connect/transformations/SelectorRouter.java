package com.purbon.kafka.connect.transformations;

import java.util.Map;
import org.apache.kafka.common.config.ConfigDef;
import org.apache.kafka.connect.connector.ConnectRecord;
import org.apache.kafka.connect.header.Header;
import org.apache.kafka.connect.transforms.Transformation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SelectorRouter<R extends ConnectRecord<R>> implements Transformation<R> {

  private SelectorRouterConfig config;

  private Logger logger = LoggerFactory.getLogger(FlagMessagesWithErrors.class);

  public R apply(R record) {

    Header header = record.headers().lastWithName(config.getErrorHeaderField());

    logger.debug("Record: "+record.toString());

    if (header != null)
      logger.debug(" with header: "+header.toString()+" header.value: "+header.value());

    if (header != null && (Boolean)header.value()) {
      logger.debug("Sending data to the new topic: "+config.getDLQTopicName());
      return record.newRecord(config.getDLQTopicName(), record.kafkaPartition(), record.keySchema(),
          record.key(), record.valueSchema(), record.value(), record.timestamp());
    } else {
      logger.debug("Sending incoming record to the original destination");
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
