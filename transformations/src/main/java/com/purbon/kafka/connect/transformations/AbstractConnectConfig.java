package com.purbon.kafka.connect.transformations;

import org.apache.kafka.common.config.AbstractConfig;
import org.apache.kafka.common.config.ConfigDef;
import java.util.Map;
import org.apache.kafka.common.config.ConfigDef.Importance;
import org.apache.kafka.common.config.ConfigDef.NonEmptyString;
import org.apache.kafka.common.config.ConfigDef.Type;

public abstract class AbstractConnectConfig extends AbstractConfig {

  public static final String ERROR_HEADER_FIELD_CONF = "message.header.error.field";
  static final String ERROR_HEADER_FIELD_DOC = "Error field in the message headers used to label a message as containing some error";
  static final String ERROR_HEADER_FIELD_DEFAULT = "";

  private String errorHeaderField;

  public AbstractConnectConfig(Map<?, ?> originals) {
    super(config(), originals);
    this.errorHeaderField = getString(ERROR_HEADER_FIELD_CONF);
  }

  static ConfigDef config() {
    return new ConfigDef()
        .define(ERROR_HEADER_FIELD_CONF,
        Type.STRING,
        ERROR_HEADER_FIELD_DEFAULT,
        new NonEmptyString(),
        Importance.HIGH,
        ERROR_HEADER_FIELD_DOC);
  }

  public String getErrorHeaderField() {
    return errorHeaderField;
  }

}
