package com.purbon.kafka.connect.transformations;

import java.util.Map;
import org.apache.kafka.common.config.AbstractConfig;
import org.apache.kafka.common.config.ConfigDef;
import org.apache.kafka.common.config.ConfigDef.Importance;
import org.apache.kafka.common.config.ConfigDef.NonEmptyString;
import org.apache.kafka.common.config.ConfigDef.Type;

public class SelectorRouterConfig extends AbstractConnectConfig {

  public static final String DLQ_FIELD_CONF = "connector.source.dlq";
  static final String DLQ_FIELD_DOC = "DLQ topic used to drop wrongful message incoming from the connectors";
  static final String DLQ_FIELD_DEFAULT = "connector-dlq";



  private String dlqTopicName;

  public SelectorRouterConfig(Map<?, ?> originals) {
    super(originals);

    this.dlqTopicName = getString(DLQ_FIELD_CONF);
  }

  static ConfigDef config() {
    return AbstractConnectConfig.config()
        .define(DLQ_FIELD_CONF,
            Type.STRING,
            DLQ_FIELD_DEFAULT,
            Importance.MEDIUM,
            DLQ_FIELD_DOC);
  }

  public String getDLQTopicName() {
    return dlqTopicName;
  }


}
