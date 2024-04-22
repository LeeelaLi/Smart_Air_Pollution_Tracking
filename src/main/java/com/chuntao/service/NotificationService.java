// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: Notification.proto
// Protobuf Java Version: 4.26.1

package com.chuntao.service;

public final class NotificationService {
  private NotificationService() {}
  static {
    com.google.protobuf.RuntimeVersion.validateProtobufGencodeVersion(
      com.google.protobuf.RuntimeVersion.RuntimeDomain.PUBLIC,
      /* major= */ 4,
      /* minor= */ 26,
      /* patch= */ 1,
      /* suffix= */ "",
      NotificationService.class.getName());
  }
  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistryLite registry) {
  }

  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistry registry) {
    registerAllExtensions(
        (com.google.protobuf.ExtensionRegistryLite) registry);
  }
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_com_chuntao_service_SensorMessage_descriptor;
  static final 
    com.google.protobuf.GeneratedMessage.FieldAccessorTable
      internal_static_com_chuntao_service_SensorMessage_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_com_chuntao_service_HVACMessage_descriptor;
  static final 
    com.google.protobuf.GeneratedMessage.FieldAccessorTable
      internal_static_com_chuntao_service_HVACMessage_fieldAccessorTable;

  public static com.google.protobuf.Descriptors.FileDescriptor
      getDescriptor() {
    return descriptor;
  }
  private static  com.google.protobuf.Descriptors.FileDescriptor
      descriptor;
  static {
    java.lang.String[] descriptorData = {
      "\n\022Notification.proto\022\023com.chuntao.servic" +
      "e\032\037google/protobuf/timestamp.proto\032\014Sens" +
      "or.proto\032\nHVAC.proto\"\204\001\n\rSensorMessage\022\020" +
      "\n\010location\030\001 \001(\t\022\023\n\013air_quality\030\002 \001(\t\022\r\n" +
      "\005alert\030\003 \001(\t\022\016\n\006advice\030\004 \001(\t\022-\n\ttimestam" +
      "p\030\005 \001(\0132\032.google.protobuf.Timestamp\"`\n\013H" +
      "VACMessage\022\016\n\006status\030\001 \001(\010\022\017\n\007message\030\002 " +
      "\001(\t\0220\n\014updated_time\030\003 \001(\0132\032.google.proto" +
      "buf.Timestamp2\301\001\n\014Notification\022[\n\023Sensor" +
      "Notifications\022\034.com.chuntao.AnalyseRespo" +
      "nse\032\".com.chuntao.service.SensorMessage\"" +
      "\0000\001\022T\n\021HVACNotifications\022\031.com.chuntao.H" +
      "VACResponse\032 .com.chuntao.service.HVACMe" +
      "ssage\"\0000\001B,\n\023com.chuntao.serviceB\023Notifi" +
      "cationServiceP\001b\006proto3"
    };
    descriptor = com.google.protobuf.Descriptors.FileDescriptor
      .internalBuildGeneratedFileFrom(descriptorData,
        new com.google.protobuf.Descriptors.FileDescriptor[] {
          com.google.protobuf.TimestampProto.getDescriptor(),
          com.chuntao.service.SensorService.getDescriptor(),
          com.chuntao.service.HVACService.getDescriptor(),
        });
    internal_static_com_chuntao_service_SensorMessage_descriptor =
      getDescriptor().getMessageTypes().get(0);
    internal_static_com_chuntao_service_SensorMessage_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessage.FieldAccessorTable(
        internal_static_com_chuntao_service_SensorMessage_descriptor,
        new java.lang.String[] { "Location", "AirQuality", "Alert", "Advice", "Timestamp", });
    internal_static_com_chuntao_service_HVACMessage_descriptor =
      getDescriptor().getMessageTypes().get(1);
    internal_static_com_chuntao_service_HVACMessage_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessage.FieldAccessorTable(
        internal_static_com_chuntao_service_HVACMessage_descriptor,
        new java.lang.String[] { "Status", "Message", "UpdatedTime", });
    descriptor.resolveAllFeaturesImmutable();
    com.google.protobuf.TimestampProto.getDescriptor();
    com.chuntao.service.SensorService.getDescriptor();
    com.chuntao.service.HVACService.getDescriptor();
  }

  // @@protoc_insertion_point(outer_class_scope)
}
