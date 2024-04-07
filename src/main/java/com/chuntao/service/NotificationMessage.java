// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: AirPollutionService.proto

// Protobuf Java Version: 4.26.1
package com.chuntao.service;

/**
 * <pre>
 * Notification Service:
 * </pre>
 *
 * Protobuf type {@code com.chuntao.NotificationMessage}
 */
public final class NotificationMessage extends
    com.google.protobuf.GeneratedMessage implements
    // @@protoc_insertion_point(message_implements:com.chuntao.NotificationMessage)
    NotificationMessageOrBuilder {
private static final long serialVersionUID = 0L;
  static {
    com.google.protobuf.RuntimeVersion.validateProtobufGencodeVersion(
      com.google.protobuf.RuntimeVersion.RuntimeDomain.PUBLIC,
      /* major= */ 4,
      /* minor= */ 26,
      /* patch= */ 1,
      /* suffix= */ "",
      NotificationMessage.class.getName());
  }
  // Use NotificationMessage.newBuilder() to construct.
  private NotificationMessage(com.google.protobuf.GeneratedMessage.Builder<?> builder) {
    super(builder);
  }
  private NotificationMessage() {
    location_ = "";
    airQuality_ = "";
    ventControl_ = "";
  }

  public static final com.google.protobuf.Descriptors.Descriptor
      getDescriptor() {
    return com.chuntao.service.AirPollutionService.internal_static_com_chuntao_NotificationMessage_descriptor;
  }

  @java.lang.Override
  protected com.google.protobuf.GeneratedMessage.FieldAccessorTable
      internalGetFieldAccessorTable() {
    return com.chuntao.service.AirPollutionService.internal_static_com_chuntao_NotificationMessage_fieldAccessorTable
        .ensureFieldAccessorsInitialized(
            com.chuntao.service.NotificationMessage.class, com.chuntao.service.NotificationMessage.Builder.class);
  }

  public static final int LOCATION_FIELD_NUMBER = 1;
  @SuppressWarnings("serial")
  private volatile java.lang.Object location_ = "";
  /**
   * <code>string location = 1;</code>
   * @return The location.
   */
  @java.lang.Override
  public java.lang.String getLocation() {
    java.lang.Object ref = location_;
    if (ref instanceof java.lang.String) {
      return (java.lang.String) ref;
    } else {
      com.google.protobuf.ByteString bs = 
          (com.google.protobuf.ByteString) ref;
      java.lang.String s = bs.toStringUtf8();
      location_ = s;
      return s;
    }
  }
  /**
   * <code>string location = 1;</code>
   * @return The bytes for location.
   */
  @java.lang.Override
  public com.google.protobuf.ByteString
      getLocationBytes() {
    java.lang.Object ref = location_;
    if (ref instanceof java.lang.String) {
      com.google.protobuf.ByteString b = 
          com.google.protobuf.ByteString.copyFromUtf8(
              (java.lang.String) ref);
      location_ = b;
      return b;
    } else {
      return (com.google.protobuf.ByteString) ref;
    }
  }

  public static final int AIR_QUALITY_FIELD_NUMBER = 2;
  @SuppressWarnings("serial")
  private volatile java.lang.Object airQuality_ = "";
  /**
   * <code>string air_quality = 2;</code>
   * @return The airQuality.
   */
  @java.lang.Override
  public java.lang.String getAirQuality() {
    java.lang.Object ref = airQuality_;
    if (ref instanceof java.lang.String) {
      return (java.lang.String) ref;
    } else {
      com.google.protobuf.ByteString bs = 
          (com.google.protobuf.ByteString) ref;
      java.lang.String s = bs.toStringUtf8();
      airQuality_ = s;
      return s;
    }
  }
  /**
   * <code>string air_quality = 2;</code>
   * @return The bytes for airQuality.
   */
  @java.lang.Override
  public com.google.protobuf.ByteString
      getAirQualityBytes() {
    java.lang.Object ref = airQuality_;
    if (ref instanceof java.lang.String) {
      com.google.protobuf.ByteString b = 
          com.google.protobuf.ByteString.copyFromUtf8(
              (java.lang.String) ref);
      airQuality_ = b;
      return b;
    } else {
      return (com.google.protobuf.ByteString) ref;
    }
  }

  public static final int VENT_CONTROL_FIELD_NUMBER = 3;
  @SuppressWarnings("serial")
  private volatile java.lang.Object ventControl_ = "";
  /**
   * <code>string vent_control = 3;</code>
   * @return The ventControl.
   */
  @java.lang.Override
  public java.lang.String getVentControl() {
    java.lang.Object ref = ventControl_;
    if (ref instanceof java.lang.String) {
      return (java.lang.String) ref;
    } else {
      com.google.protobuf.ByteString bs = 
          (com.google.protobuf.ByteString) ref;
      java.lang.String s = bs.toStringUtf8();
      ventControl_ = s;
      return s;
    }
  }
  /**
   * <code>string vent_control = 3;</code>
   * @return The bytes for ventControl.
   */
  @java.lang.Override
  public com.google.protobuf.ByteString
      getVentControlBytes() {
    java.lang.Object ref = ventControl_;
    if (ref instanceof java.lang.String) {
      com.google.protobuf.ByteString b = 
          com.google.protobuf.ByteString.copyFromUtf8(
              (java.lang.String) ref);
      ventControl_ = b;
      return b;
    } else {
      return (com.google.protobuf.ByteString) ref;
    }
  }

  private byte memoizedIsInitialized = -1;
  @java.lang.Override
  public final boolean isInitialized() {
    byte isInitialized = memoizedIsInitialized;
    if (isInitialized == 1) return true;
    if (isInitialized == 0) return false;

    memoizedIsInitialized = 1;
    return true;
  }

  @java.lang.Override
  public void writeTo(com.google.protobuf.CodedOutputStream output)
                      throws java.io.IOException {
    if (!com.google.protobuf.GeneratedMessage.isStringEmpty(location_)) {
      com.google.protobuf.GeneratedMessage.writeString(output, 1, location_);
    }
    if (!com.google.protobuf.GeneratedMessage.isStringEmpty(airQuality_)) {
      com.google.protobuf.GeneratedMessage.writeString(output, 2, airQuality_);
    }
    if (!com.google.protobuf.GeneratedMessage.isStringEmpty(ventControl_)) {
      com.google.protobuf.GeneratedMessage.writeString(output, 3, ventControl_);
    }
    getUnknownFields().writeTo(output);
  }

  @java.lang.Override
  public int getSerializedSize() {
    int size = memoizedSize;
    if (size != -1) return size;

    size = 0;
    if (!com.google.protobuf.GeneratedMessage.isStringEmpty(location_)) {
      size += com.google.protobuf.GeneratedMessage.computeStringSize(1, location_);
    }
    if (!com.google.protobuf.GeneratedMessage.isStringEmpty(airQuality_)) {
      size += com.google.protobuf.GeneratedMessage.computeStringSize(2, airQuality_);
    }
    if (!com.google.protobuf.GeneratedMessage.isStringEmpty(ventControl_)) {
      size += com.google.protobuf.GeneratedMessage.computeStringSize(3, ventControl_);
    }
    size += getUnknownFields().getSerializedSize();
    memoizedSize = size;
    return size;
  }

  @java.lang.Override
  public boolean equals(final java.lang.Object obj) {
    if (obj == this) {
     return true;
    }
    if (!(obj instanceof com.chuntao.service.NotificationMessage)) {
      return super.equals(obj);
    }
    com.chuntao.service.NotificationMessage other = (com.chuntao.service.NotificationMessage) obj;

    if (!getLocation()
        .equals(other.getLocation())) return false;
    if (!getAirQuality()
        .equals(other.getAirQuality())) return false;
    if (!getVentControl()
        .equals(other.getVentControl())) return false;
    if (!getUnknownFields().equals(other.getUnknownFields())) return false;
    return true;
  }

  @java.lang.Override
  public int hashCode() {
    if (memoizedHashCode != 0) {
      return memoizedHashCode;
    }
    int hash = 41;
    hash = (19 * hash) + getDescriptor().hashCode();
    hash = (37 * hash) + LOCATION_FIELD_NUMBER;
    hash = (53 * hash) + getLocation().hashCode();
    hash = (37 * hash) + AIR_QUALITY_FIELD_NUMBER;
    hash = (53 * hash) + getAirQuality().hashCode();
    hash = (37 * hash) + VENT_CONTROL_FIELD_NUMBER;
    hash = (53 * hash) + getVentControl().hashCode();
    hash = (29 * hash) + getUnknownFields().hashCode();
    memoizedHashCode = hash;
    return hash;
  }

  public static com.chuntao.service.NotificationMessage parseFrom(
      java.nio.ByteBuffer data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static com.chuntao.service.NotificationMessage parseFrom(
      java.nio.ByteBuffer data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static com.chuntao.service.NotificationMessage parseFrom(
      com.google.protobuf.ByteString data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static com.chuntao.service.NotificationMessage parseFrom(
      com.google.protobuf.ByteString data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static com.chuntao.service.NotificationMessage parseFrom(byte[] data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static com.chuntao.service.NotificationMessage parseFrom(
      byte[] data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static com.chuntao.service.NotificationMessage parseFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessage
        .parseWithIOException(PARSER, input);
  }
  public static com.chuntao.service.NotificationMessage parseFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessage
        .parseWithIOException(PARSER, input, extensionRegistry);
  }

  public static com.chuntao.service.NotificationMessage parseDelimitedFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessage
        .parseDelimitedWithIOException(PARSER, input);
  }

  public static com.chuntao.service.NotificationMessage parseDelimitedFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessage
        .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
  }
  public static com.chuntao.service.NotificationMessage parseFrom(
      com.google.protobuf.CodedInputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessage
        .parseWithIOException(PARSER, input);
  }
  public static com.chuntao.service.NotificationMessage parseFrom(
      com.google.protobuf.CodedInputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessage
        .parseWithIOException(PARSER, input, extensionRegistry);
  }

  @java.lang.Override
  public Builder newBuilderForType() { return newBuilder(); }
  public static Builder newBuilder() {
    return DEFAULT_INSTANCE.toBuilder();
  }
  public static Builder newBuilder(com.chuntao.service.NotificationMessage prototype) {
    return DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
  }
  @java.lang.Override
  public Builder toBuilder() {
    return this == DEFAULT_INSTANCE
        ? new Builder() : new Builder().mergeFrom(this);
  }

  @java.lang.Override
  protected Builder newBuilderForType(
      com.google.protobuf.GeneratedMessage.BuilderParent parent) {
    Builder builder = new Builder(parent);
    return builder;
  }
  /**
   * <pre>
   * Notification Service:
   * </pre>
   *
   * Protobuf type {@code com.chuntao.NotificationMessage}
   */
  public static final class Builder extends
      com.google.protobuf.GeneratedMessage.Builder<Builder> implements
      // @@protoc_insertion_point(builder_implements:com.chuntao.NotificationMessage)
      com.chuntao.service.NotificationMessageOrBuilder {
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return com.chuntao.service.AirPollutionService.internal_static_com_chuntao_NotificationMessage_descriptor;
    }

    @java.lang.Override
    protected com.google.protobuf.GeneratedMessage.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return com.chuntao.service.AirPollutionService.internal_static_com_chuntao_NotificationMessage_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              com.chuntao.service.NotificationMessage.class, com.chuntao.service.NotificationMessage.Builder.class);
    }

    // Construct using com.chuntao.service.NotificationMessage.newBuilder()
    private Builder() {

    }

    private Builder(
        com.google.protobuf.GeneratedMessage.BuilderParent parent) {
      super(parent);

    }
    @java.lang.Override
    public Builder clear() {
      super.clear();
      bitField0_ = 0;
      location_ = "";
      airQuality_ = "";
      ventControl_ = "";
      return this;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.Descriptor
        getDescriptorForType() {
      return com.chuntao.service.AirPollutionService.internal_static_com_chuntao_NotificationMessage_descriptor;
    }

    @java.lang.Override
    public com.chuntao.service.NotificationMessage getDefaultInstanceForType() {
      return com.chuntao.service.NotificationMessage.getDefaultInstance();
    }

    @java.lang.Override
    public com.chuntao.service.NotificationMessage build() {
      com.chuntao.service.NotificationMessage result = buildPartial();
      if (!result.isInitialized()) {
        throw newUninitializedMessageException(result);
      }
      return result;
    }

    @java.lang.Override
    public com.chuntao.service.NotificationMessage buildPartial() {
      com.chuntao.service.NotificationMessage result = new com.chuntao.service.NotificationMessage(this);
      if (bitField0_ != 0) { buildPartial0(result); }
      onBuilt();
      return result;
    }

    private void buildPartial0(com.chuntao.service.NotificationMessage result) {
      int from_bitField0_ = bitField0_;
      if (((from_bitField0_ & 0x00000001) != 0)) {
        result.location_ = location_;
      }
      if (((from_bitField0_ & 0x00000002) != 0)) {
        result.airQuality_ = airQuality_;
      }
      if (((from_bitField0_ & 0x00000004) != 0)) {
        result.ventControl_ = ventControl_;
      }
    }

    @java.lang.Override
    public Builder mergeFrom(com.google.protobuf.Message other) {
      if (other instanceof com.chuntao.service.NotificationMessage) {
        return mergeFrom((com.chuntao.service.NotificationMessage)other);
      } else {
        super.mergeFrom(other);
        return this;
      }
    }

    public Builder mergeFrom(com.chuntao.service.NotificationMessage other) {
      if (other == com.chuntao.service.NotificationMessage.getDefaultInstance()) return this;
      if (!other.getLocation().isEmpty()) {
        location_ = other.location_;
        bitField0_ |= 0x00000001;
        onChanged();
      }
      if (!other.getAirQuality().isEmpty()) {
        airQuality_ = other.airQuality_;
        bitField0_ |= 0x00000002;
        onChanged();
      }
      if (!other.getVentControl().isEmpty()) {
        ventControl_ = other.ventControl_;
        bitField0_ |= 0x00000004;
        onChanged();
      }
      this.mergeUnknownFields(other.getUnknownFields());
      onChanged();
      return this;
    }

    @java.lang.Override
    public final boolean isInitialized() {
      return true;
    }

    @java.lang.Override
    public Builder mergeFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      if (extensionRegistry == null) {
        throw new java.lang.NullPointerException();
      }
      try {
        boolean done = false;
        while (!done) {
          int tag = input.readTag();
          switch (tag) {
            case 0:
              done = true;
              break;
            case 10: {
              location_ = input.readStringRequireUtf8();
              bitField0_ |= 0x00000001;
              break;
            } // case 10
            case 18: {
              airQuality_ = input.readStringRequireUtf8();
              bitField0_ |= 0x00000002;
              break;
            } // case 18
            case 26: {
              ventControl_ = input.readStringRequireUtf8();
              bitField0_ |= 0x00000004;
              break;
            } // case 26
            default: {
              if (!super.parseUnknownField(input, extensionRegistry, tag)) {
                done = true; // was an endgroup tag
              }
              break;
            } // default:
          } // switch (tag)
        } // while (!done)
      } catch (com.google.protobuf.InvalidProtocolBufferException e) {
        throw e.unwrapIOException();
      } finally {
        onChanged();
      } // finally
      return this;
    }
    private int bitField0_;

    private java.lang.Object location_ = "";
    /**
     * <code>string location = 1;</code>
     * @return The location.
     */
    public java.lang.String getLocation() {
      java.lang.Object ref = location_;
      if (!(ref instanceof java.lang.String)) {
        com.google.protobuf.ByteString bs =
            (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        location_ = s;
        return s;
      } else {
        return (java.lang.String) ref;
      }
    }
    /**
     * <code>string location = 1;</code>
     * @return The bytes for location.
     */
    public com.google.protobuf.ByteString
        getLocationBytes() {
      java.lang.Object ref = location_;
      if (ref instanceof String) {
        com.google.protobuf.ByteString b = 
            com.google.protobuf.ByteString.copyFromUtf8(
                (java.lang.String) ref);
        location_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }
    /**
     * <code>string location = 1;</code>
     * @param value The location to set.
     * @return This builder for chaining.
     */
    public Builder setLocation(
        java.lang.String value) {
      if (value == null) { throw new NullPointerException(); }
      location_ = value;
      bitField0_ |= 0x00000001;
      onChanged();
      return this;
    }
    /**
     * <code>string location = 1;</code>
     * @return This builder for chaining.
     */
    public Builder clearLocation() {
      location_ = getDefaultInstance().getLocation();
      bitField0_ = (bitField0_ & ~0x00000001);
      onChanged();
      return this;
    }
    /**
     * <code>string location = 1;</code>
     * @param value The bytes for location to set.
     * @return This builder for chaining.
     */
    public Builder setLocationBytes(
        com.google.protobuf.ByteString value) {
      if (value == null) { throw new NullPointerException(); }
      checkByteStringIsUtf8(value);
      location_ = value;
      bitField0_ |= 0x00000001;
      onChanged();
      return this;
    }

    private java.lang.Object airQuality_ = "";
    /**
     * <code>string air_quality = 2;</code>
     * @return The airQuality.
     */
    public java.lang.String getAirQuality() {
      java.lang.Object ref = airQuality_;
      if (!(ref instanceof java.lang.String)) {
        com.google.protobuf.ByteString bs =
            (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        airQuality_ = s;
        return s;
      } else {
        return (java.lang.String) ref;
      }
    }
    /**
     * <code>string air_quality = 2;</code>
     * @return The bytes for airQuality.
     */
    public com.google.protobuf.ByteString
        getAirQualityBytes() {
      java.lang.Object ref = airQuality_;
      if (ref instanceof String) {
        com.google.protobuf.ByteString b = 
            com.google.protobuf.ByteString.copyFromUtf8(
                (java.lang.String) ref);
        airQuality_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }
    /**
     * <code>string air_quality = 2;</code>
     * @param value The airQuality to set.
     * @return This builder for chaining.
     */
    public Builder setAirQuality(
        java.lang.String value) {
      if (value == null) { throw new NullPointerException(); }
      airQuality_ = value;
      bitField0_ |= 0x00000002;
      onChanged();
      return this;
    }
    /**
     * <code>string air_quality = 2;</code>
     * @return This builder for chaining.
     */
    public Builder clearAirQuality() {
      airQuality_ = getDefaultInstance().getAirQuality();
      bitField0_ = (bitField0_ & ~0x00000002);
      onChanged();
      return this;
    }
    /**
     * <code>string air_quality = 2;</code>
     * @param value The bytes for airQuality to set.
     * @return This builder for chaining.
     */
    public Builder setAirQualityBytes(
        com.google.protobuf.ByteString value) {
      if (value == null) { throw new NullPointerException(); }
      checkByteStringIsUtf8(value);
      airQuality_ = value;
      bitField0_ |= 0x00000002;
      onChanged();
      return this;
    }

    private java.lang.Object ventControl_ = "";
    /**
     * <code>string vent_control = 3;</code>
     * @return The ventControl.
     */
    public java.lang.String getVentControl() {
      java.lang.Object ref = ventControl_;
      if (!(ref instanceof java.lang.String)) {
        com.google.protobuf.ByteString bs =
            (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        ventControl_ = s;
        return s;
      } else {
        return (java.lang.String) ref;
      }
    }
    /**
     * <code>string vent_control = 3;</code>
     * @return The bytes for ventControl.
     */
    public com.google.protobuf.ByteString
        getVentControlBytes() {
      java.lang.Object ref = ventControl_;
      if (ref instanceof String) {
        com.google.protobuf.ByteString b = 
            com.google.protobuf.ByteString.copyFromUtf8(
                (java.lang.String) ref);
        ventControl_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }
    /**
     * <code>string vent_control = 3;</code>
     * @param value The ventControl to set.
     * @return This builder for chaining.
     */
    public Builder setVentControl(
        java.lang.String value) {
      if (value == null) { throw new NullPointerException(); }
      ventControl_ = value;
      bitField0_ |= 0x00000004;
      onChanged();
      return this;
    }
    /**
     * <code>string vent_control = 3;</code>
     * @return This builder for chaining.
     */
    public Builder clearVentControl() {
      ventControl_ = getDefaultInstance().getVentControl();
      bitField0_ = (bitField0_ & ~0x00000004);
      onChanged();
      return this;
    }
    /**
     * <code>string vent_control = 3;</code>
     * @param value The bytes for ventControl to set.
     * @return This builder for chaining.
     */
    public Builder setVentControlBytes(
        com.google.protobuf.ByteString value) {
      if (value == null) { throw new NullPointerException(); }
      checkByteStringIsUtf8(value);
      ventControl_ = value;
      bitField0_ |= 0x00000004;
      onChanged();
      return this;
    }

    // @@protoc_insertion_point(builder_scope:com.chuntao.NotificationMessage)
  }

  // @@protoc_insertion_point(class_scope:com.chuntao.NotificationMessage)
  private static final com.chuntao.service.NotificationMessage DEFAULT_INSTANCE;
  static {
    DEFAULT_INSTANCE = new com.chuntao.service.NotificationMessage();
  }

  public static com.chuntao.service.NotificationMessage getDefaultInstance() {
    return DEFAULT_INSTANCE;
  }

  private static final com.google.protobuf.Parser<NotificationMessage>
      PARSER = new com.google.protobuf.AbstractParser<NotificationMessage>() {
    @java.lang.Override
    public NotificationMessage parsePartialFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      Builder builder = newBuilder();
      try {
        builder.mergeFrom(input, extensionRegistry);
      } catch (com.google.protobuf.InvalidProtocolBufferException e) {
        throw e.setUnfinishedMessage(builder.buildPartial());
      } catch (com.google.protobuf.UninitializedMessageException e) {
        throw e.asInvalidProtocolBufferException().setUnfinishedMessage(builder.buildPartial());
      } catch (java.io.IOException e) {
        throw new com.google.protobuf.InvalidProtocolBufferException(e)
            .setUnfinishedMessage(builder.buildPartial());
      }
      return builder.buildPartial();
    }
  };

  public static com.google.protobuf.Parser<NotificationMessage> parser() {
    return PARSER;
  }

  @java.lang.Override
  public com.google.protobuf.Parser<NotificationMessage> getParserForType() {
    return PARSER;
  }

  @java.lang.Override
  public com.chuntao.service.NotificationMessage getDefaultInstanceForType() {
    return DEFAULT_INSTANCE;
  }

}

