// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: HVAC.proto

// Protobuf Java Version: 4.26.1
package com.chuntao.service;

public interface HVACResponseOrBuilder extends
    // @@protoc_insertion_point(interface_extends:com.chuntao.HVACResponse)
    com.google.protobuf.MessageOrBuilder {

  /**
   * <code>string location = 1;</code>
   * @return The location.
   */
  java.lang.String getLocation();
  /**
   * <code>string location = 1;</code>
   * @return The bytes for location.
   */
  com.google.protobuf.ByteString
      getLocationBytes();

  /**
   * <code>bool status = 2;</code>
   * @return The status.
   */
  boolean getStatus();

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
