// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: Sensor.proto

// Protobuf Java Version: 4.26.1
package com.chuntao.service;

public interface SensorResponseOrBuilder extends
    // @@protoc_insertion_point(interface_extends:com.chuntao.SensorResponse)
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
   * <pre>
   * PM2.5 density in μg/m3 of the air
   * </pre>
   *
   * <code>float PM25 = 2;</code>
   * @return The pM25.
   */
  float getPM25();

  /**
   * <pre>
   * Air temperature in Celsius
   * </pre>
   *
   * <code>float temperature = 3;</code>
   * @return The temperature.
   */
  float getTemperature();

  /**
   * <pre>
   * VOC level in mg/m3
   * </pre>
   *
   * <code>float VOC = 4;</code>
   * @return The vOC.
   */
  float getVOC();

  /**
   * <pre>
   * Humidity in Percentage
   * </pre>
   *
   * <code>float humidity = 5;</code>
   * @return The humidity.
   */
  float getHumidity();

  /**
   * <pre>
   * CO in ppm
   * </pre>
   *
   * <code>float CO = 6;</code>
   * @return The cO.
   */
  float getCO();
}
