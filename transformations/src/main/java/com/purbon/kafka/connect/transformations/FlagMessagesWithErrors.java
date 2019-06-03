package com.purbon.kafka.connect.transformations;

import java.util.Map;
import java.util.Random;
import org.apache.kafka.common.config.ConfigDef;
import org.apache.kafka.connect.connector.ConnectRecord;
import org.apache.kafka.connect.data.Field;
import org.apache.kafka.connect.data.Schema;
import org.apache.kafka.connect.data.Schema.Type;
import org.apache.kafka.connect.data.SchemaBuilder;
import org.apache.kafka.connect.data.Struct;
import org.apache.kafka.connect.transforms.Transformation;
import org.apache.kafka.connect.transforms.util.SchemaUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FlagMessagesWithErrors<R extends ConnectRecord<R>> implements Transformation<R> {


  private Random rand;

  private class FlagMessageWithErrorsConfig extends AbstractConnectConfig {

    public FlagMessageWithErrorsConfig(Map<?, ?> originals) {
      super(AbstractConnectConfig.config(), originals);
    }
  }

  private Logger logger = LoggerFactory.getLogger(FlagMessagesWithErrors.class);

  private FlagMessageWithErrorsConfig config;

  public R apply(R record) {

    if (rand.nextBoolean()) {
      record.headers().addBoolean(config.getErrorHeaderField(), true);
    }
    logger.debug("Flagging message "+record.toString()+" as containing errors");

    return record;
  }

  public ConfigDef config() {
    return FlagMessageWithErrorsConfig.config();
  }

  public void close() {

  }

  public void configure(Map<String, ?> map)
  {
    this.config = new FlagMessageWithErrorsConfig(map);
    this.rand = new Random(System.currentTimeMillis());
  }
}
