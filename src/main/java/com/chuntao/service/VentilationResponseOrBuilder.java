// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: AirPollutionService.proto

// Protobuf Java Version: 4.26.1
package com.chuntao.service;

public interface VentilationResponseOrBuilder extends
    // @@protoc_insertion_point(interface_extends:com.chuntao.VentilationResponse)
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
   * <code>string pollution_item = 2;</code>
   * @return The pollutionItem.
   */
  java.lang.String getPollutionItem();
  /**
   * <code>string pollution_item = 2;</code>
   * @return The bytes for pollutionItem.
   */
  com.google.protobuf.ByteString
      getPollutionItemBytes();

  /**
   * <code>bool turn_on = 3;</code>
   * @return The turnOn.
   */
  boolean getTurnOn();

  /**
   * <code>int64 timestamp = 4;</code>
   * @return The timestamp.
   */
  long getTimestamp();
}
