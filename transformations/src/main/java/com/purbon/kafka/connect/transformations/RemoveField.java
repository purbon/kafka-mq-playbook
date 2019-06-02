package com.purbon.kafka.connect.transformations;

import java.util.Map;
import java.util.function.Consumer;
import org.apache.kafka.common.config.ConfigDef;
import org.apache.kafka.connect.connector.ConnectRecord;
import org.apache.kafka.connect.data.ConnectSchema;
import org.apache.kafka.connect.data.Field;
import org.apache.kafka.connect.data.Schema;
import org.apache.kafka.connect.data.Schema.Type;
import org.apache.kafka.connect.data.SchemaBuilder;
import org.apache.kafka.connect.data.Struct;
import org.apache.kafka.connect.transforms.Transformation;
import org.apache.kafka.connect.transforms.util.SchemaUtil;

public class RemoveField<R extends ConnectRecord<R>> implements Transformation<R> {

  private RemoveFieldConfig config;

  public R apply(R record) {

    Struct recordValue = (Struct) record.value();
    Schema recordSchema = record.valueSchema();

    if (recordValue.getWithoutDefault(config.getFieldToRemove()) != null) {
      // field could be removed

      final SchemaBuilder builder = SchemaUtil.copySchemaBasics(recordSchema, SchemaBuilder.struct());
      Struct defaultValue = (Struct) recordSchema.defaultValue();

      for(Field field : recordSchema.fields()) {
        if (field.name() == config.getFieldToRemove()) {
          continue;
        }
        if (field.schema().type() == Type.STRUCT) {
          builder.field(field.name(), field.schema());
        } else {
          builder.field(field.name(), field.schema());
        }
      }

      Schema newRecordSchema = builder.schema();
      Struct newRecordValue = new Struct(newRecordSchema);

      for(Field field : newRecordSchema.fields()) {
        newRecordValue.put(field, recordValue.get(field));
      }

      return record.newRecord(record.topic(), record.kafkaPartition(), record.keySchema(),
          record.key(), newRecordSchema, newRecordValue, record.timestamp());
    } else {
      // field does not exist
      record.headers().addBoolean(config.getErrorHeaderField(), true);
    }

    return record;
  }

  public ConfigDef config() {
    return RemoveFieldConfig.config();
  }

  public void close() {

  }

  public void configure(Map<String, ?> map) {
    this.config = new RemoveFieldConfig(map);
  }
}
