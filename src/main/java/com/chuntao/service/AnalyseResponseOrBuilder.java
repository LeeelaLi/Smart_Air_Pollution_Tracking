// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: sensor.proto

// Protobuf Java Version: 4.26.1
package com.chuntao.service;

public interface AnalyseResponseOrBuilder extends
    // @@protoc_insertion_point(interface_extends:com.chuntao.AnalyseResponse)
    com.google.protobuf.MessageOrBuilder {

  /**
   * <code>string analyse = 1;</code>
   * @return The analyse.
   */
  java.lang.String getAnalyse();
  /**
   * <code>string analyse = 1;</code>
   * @return The bytes for analyse.
   */
  com.google.protobuf.ByteString
      getAnalyseBytes();

  /**
   * <code>string location = 2;</code>
   * @return The location.
   */
  java.lang.String getLocation();
  /**
   * <code>string location = 2;</code>
   * @return The bytes for location.
   */
  com.google.protobuf.ByteString
      getLocationBytes();

  /**
   * <code>int32 pollution_level = 3;</code>
   * @return The pollutionLevel.
   */
  int getPollutionLevel();

  /**
   * <code>string message = 4;</code>
   * @return The message.
   */
  java.lang.String getMessage();
  /**
   * <code>string message = 4;</code>
   * @return The bytes for message.
   */
  com.google.protobuf.ByteString
      getMessageBytes();

  /**
   * <code>.google.protobuf.Timestamp timestamp = 5;</code>
   * @return Whether the timestamp field is set.
   */
  boolean hasTimestamp();
  /**
   * <code>.google.protobuf.Timestamp timestamp = 5;</code>
   * @return The timestamp.
   */
  com.google.protobuf.Timestamp getTimestamp();
  /**
   * <code>.google.protobuf.Timestamp timestamp = 5;</code>
   */
  com.google.protobuf.TimestampOrBuilder getTimestampOrBuilder();
}
