// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: HVAC.proto

// Protobuf Java Version: 4.26.1
package com.chuntao.service;

public interface HVACResponseOrBuilder extends
    // @@protoc_insertion_point(interface_extends:com.chuntao.HVACResponse)
    com.google.protobuf.MessageOrBuilder {

  /**
   * <code>string status = 1;</code>
   * @return The status.
   */
  java.lang.String getStatus();
  /**
   * <code>string status = 1;</code>
   * @return The bytes for status.
   */
  com.google.protobuf.ByteString
      getStatusBytes();

  /**
   * <code>int32 pollution_level = 2;</code>
   * @return The pollutionLevel.
   */
  int getPollutionLevel();

  /**
   * <code>.google.protobuf.Timestamp timestamp = 3;</code>
   * @return Whether the timestamp field is set.
   */
  boolean hasTimestamp();
  /**
   * <code>.google.protobuf.Timestamp timestamp = 3;</code>
   * @return The timestamp.
   */
  com.google.protobuf.Timestamp getTimestamp();
  /**
   * <code>.google.protobuf.Timestamp timestamp = 3;</code>
   */
  com.google.protobuf.TimestampOrBuilder getTimestampOrBuilder();
}
