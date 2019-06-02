package com.purbon.kafka.connect.transformations;

import java.util.Map;
import org.apache.kafka.common.config.ConfigDef;
import org.apache.kafka.common.config.ConfigDef.Importance;
import org.apache.kafka.common.config.ConfigDef.NonEmptyString;
import org.apache.kafka.common.config.ConfigDef.Type;

public class RemoveFieldConfig extends AbstractConnectConfig {

  public static final String FIELD_TO_REMOVE_CONF = "field.to.remove";
  static final String FIELD_TO_REMOVE_DOC = "Field name to be removed in the incoming field";
  static final String FIELD_TO_REMOVE_DEFAULT = "";

  private String fieldToRemove;

  public RemoveFieldConfig(Map<?, ?> originals) {
    super(originals);

    this.fieldToRemove = getString(FIELD_TO_REMOVE_CONF);
  }

  static ConfigDef config() {
    return AbstractConnectConfig.config()
        .define(FIELD_TO_REMOVE_CONF,
            Type.STRING,
            FIELD_TO_REMOVE_DEFAULT,
            new NonEmptyString(),
            Importance.HIGH,
            FIELD_TO_REMOVE_DOC);
  }

  public String getFieldToRemove() {
    return fieldToRemove;
  }

}
