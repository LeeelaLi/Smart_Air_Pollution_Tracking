// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: Sensor.proto

// Protobuf Java Version: 4.26.1
package com.chuntao.service;

/**
 * Protobuf type {@code com.chuntao.AnalyseRequest}
 */
public final class AnalyseRequest extends
    com.google.protobuf.GeneratedMessage implements
    // @@protoc_insertion_point(message_implements:com.chuntao.AnalyseRequest)
    AnalyseRequestOrBuilder {
private static final long serialVersionUID = 0L;
  static {
    com.google.protobuf.RuntimeVersion.validateProtobufGencodeVersion(
      com.google.protobuf.RuntimeVersion.RuntimeDomain.PUBLIC,
      /* major= */ 4,
      /* minor= */ 26,
      /* patch= */ 1,
      /* suffix= */ "",
      AnalyseRequest.class.getName());
  }
  // Use AnalyseRequest.newBuilder() to construct.
  private AnalyseRequest(com.google.protobuf.GeneratedMessage.Builder<?> builder) {
    super(builder);
  }
  private AnalyseRequest() {
  }

  public static final com.google.protobuf.Descriptors.Descriptor
      getDescriptor() {
    return com.chuntao.service.SensorProto.internal_static_com_chuntao_AnalyseRequest_descriptor;
  }

  @java.lang.Override
  protected com.google.protobuf.GeneratedMessage.FieldAccessorTable
      internalGetFieldAccessorTable() {
    return com.chuntao.service.SensorProto.internal_static_com_chuntao_AnalyseRequest_fieldAccessorTable
        .ensureFieldAccessorsInitialized(
            com.chuntao.service.AnalyseRequest.class, com.chuntao.service.AnalyseRequest.Builder.class);
  }

  public static final int PM25_FIELD_NUMBER = 2;
  private float pM25_ = 0F;
  /**
   * <pre>
   * PM2.5 density in μg/m3 of the air
   * </pre>
   *
   * <code>float PM25 = 2;</code>
   * @return The pM25.
   */
  @java.lang.Override
  public float getPM25() {
    return pM25_;
  }

  public static final int TEMPERATURE_FIELD_NUMBER = 3;
  private float temperature_ = 0F;
  /**
   * <pre>
   * Air temperature in Celsius
   * </pre>
   *
   * <code>float temperature = 3;</code>
   * @return The temperature.
   */
  @java.lang.Override
  public float getTemperature() {
    return temperature_;
  }

  public static final int VOC_FIELD_NUMBER = 4;
  private float vOC_ = 0F;
  /**
   * <pre>
   * VOC level in mg/m3
   * </pre>
   *
   * <code>float VOC = 4;</code>
   * @return The vOC.
   */
  @java.lang.Override
  public float getVOC() {
    return vOC_;
  }

  public static final int HUMIDITY_FIELD_NUMBER = 5;
  private float humidity_ = 0F;
  /**
   * <pre>
   * Humidity in Percentage
   * </pre>
   *
   * <code>float humidity = 5;</code>
   * @return The humidity.
   */
  @java.lang.Override
  public float getHumidity() {
    return humidity_;
  }

  public static final int CO_FIELD_NUMBER = 6;
  private float cO_ = 0F;
  /**
   * <pre>
   * CO in ppm
   * </pre>
   *
   * <code>float CO = 6;</code>
   * @return The cO.
   */
  @java.lang.Override
  public float getCO() {
    return cO_;
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
    if (java.lang.Float.floatToRawIntBits(pM25_) != 0) {
      output.writeFloat(2, pM25_);
    }
    if (java.lang.Float.floatToRawIntBits(temperature_) != 0) {
      output.writeFloat(3, temperature_);
    }
    if (java.lang.Float.floatToRawIntBits(vOC_) != 0) {
      output.writeFloat(4, vOC_);
    }
    if (java.lang.Float.floatToRawIntBits(humidity_) != 0) {
      output.writeFloat(5, humidity_);
    }
    if (java.lang.Float.floatToRawIntBits(cO_) != 0) {
      output.writeFloat(6, cO_);
    }
    getUnknownFields().writeTo(output);
  }

  @java.lang.Override
  public int getSerializedSize() {
    int size = memoizedSize;
    if (size != -1) return size;

    size = 0;
    if (java.lang.Float.floatToRawIntBits(pM25_) != 0) {
      size += com.google.protobuf.CodedOutputStream
        .computeFloatSize(2, pM25_);
    }
    if (java.lang.Float.floatToRawIntBits(temperature_) != 0) {
      size += com.google.protobuf.CodedOutputStream
        .computeFloatSize(3, temperature_);
    }
    if (java.lang.Float.floatToRawIntBits(vOC_) != 0) {
      size += com.google.protobuf.CodedOutputStream
        .computeFloatSize(4, vOC_);
    }
    if (java.lang.Float.floatToRawIntBits(humidity_) != 0) {
      size += com.google.protobuf.CodedOutputStream
        .computeFloatSize(5, humidity_);
    }
    if (java.lang.Float.floatToRawIntBits(cO_) != 0) {
      size += com.google.protobuf.CodedOutputStream
        .computeFloatSize(6, cO_);
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
    if (!(obj instanceof com.chuntao.service.AnalyseRequest)) {
      return super.equals(obj);
    }
    com.chuntao.service.AnalyseRequest other = (com.chuntao.service.AnalyseRequest) obj;

    if (java.lang.Float.floatToIntBits(getPM25())
        != java.lang.Float.floatToIntBits(
            other.getPM25())) return false;
    if (java.lang.Float.floatToIntBits(getTemperature())
        != java.lang.Float.floatToIntBits(
            other.getTemperature())) return false;
    if (java.lang.Float.floatToIntBits(getVOC())
        != java.lang.Float.floatToIntBits(
            other.getVOC())) return false;
    if (java.lang.Float.floatToIntBits(getHumidity())
        != java.lang.Float.floatToIntBits(
            other.getHumidity())) return false;
    if (java.lang.Float.floatToIntBits(getCO())
        != java.lang.Float.floatToIntBits(
            other.getCO())) return false;
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
    hash = (37 * hash) + PM25_FIELD_NUMBER;
    hash = (53 * hash) + java.lang.Float.floatToIntBits(
        getPM25());
    hash = (37 * hash) + TEMPERATURE_FIELD_NUMBER;
    hash = (53 * hash) + java.lang.Float.floatToIntBits(
        getTemperature());
    hash = (37 * hash) + VOC_FIELD_NUMBER;
    hash = (53 * hash) + java.lang.Float.floatToIntBits(
        getVOC());
    hash = (37 * hash) + HUMIDITY_FIELD_NUMBER;
    hash = (53 * hash) + java.lang.Float.floatToIntBits(
        getHumidity());
    hash = (37 * hash) + CO_FIELD_NUMBER;
    hash = (53 * hash) + java.lang.Float.floatToIntBits(
        getCO());
    hash = (29 * hash) + getUnknownFields().hashCode();
    memoizedHashCode = hash;
    return hash;
  }

  public static com.chuntao.service.AnalyseRequest parseFrom(
      java.nio.ByteBuffer data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static com.chuntao.service.AnalyseRequest parseFrom(
      java.nio.ByteBuffer data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static com.chuntao.service.AnalyseRequest parseFrom(
      com.google.protobuf.ByteString data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static com.chuntao.service.AnalyseRequest parseFrom(
      com.google.protobuf.ByteString data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static com.chuntao.service.AnalyseRequest parseFrom(byte[] data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static com.chuntao.service.AnalyseRequest parseFrom(
      byte[] data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static com.chuntao.service.AnalyseRequest parseFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessage
        .parseWithIOException(PARSER, input);
  }
  public static com.chuntao.service.AnalyseRequest parseFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessage
        .parseWithIOException(PARSER, input, extensionRegistry);
  }

  public static com.chuntao.service.AnalyseRequest parseDelimitedFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessage
        .parseDelimitedWithIOException(PARSER, input);
  }

  public static com.chuntao.service.AnalyseRequest parseDelimitedFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessage
        .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
  }
  public static com.chuntao.service.AnalyseRequest parseFrom(
      com.google.protobuf.CodedInputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessage
        .parseWithIOException(PARSER, input);
  }
  public static com.chuntao.service.AnalyseRequest parseFrom(
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
  public static Builder newBuilder(com.chuntao.service.AnalyseRequest prototype) {
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
   * Protobuf type {@code com.chuntao.AnalyseRequest}
   */
  public static final class Builder extends
      com.google.protobuf.GeneratedMessage.Builder<Builder> implements
      // @@protoc_insertion_point(builder_implements:com.chuntao.AnalyseRequest)
      com.chuntao.service.AnalyseRequestOrBuilder {
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return com.chuntao.service.SensorProto.internal_static_com_chuntao_AnalyseRequest_descriptor;
    }

    @java.lang.Override
    protected com.google.protobuf.GeneratedMessage.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return com.chuntao.service.SensorProto.internal_static_com_chuntao_AnalyseRequest_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              com.chuntao.service.AnalyseRequest.class, com.chuntao.service.AnalyseRequest.Builder.class);
    }

    // Construct using com.chuntao.service.AnalyseRequest.newBuilder()
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
      pM25_ = 0F;
      temperature_ = 0F;
      vOC_ = 0F;
      humidity_ = 0F;
      cO_ = 0F;
      return this;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.Descriptor
        getDescriptorForType() {
      return com.chuntao.service.SensorProto.internal_static_com_chuntao_AnalyseRequest_descriptor;
    }

    @java.lang.Override
    public com.chuntao.service.AnalyseRequest getDefaultInstanceForType() {
      return com.chuntao.service.AnalyseRequest.getDefaultInstance();
    }

    @java.lang.Override
    public com.chuntao.service.AnalyseRequest build() {
      com.chuntao.service.AnalyseRequest result = buildPartial();
      if (!result.isInitialized()) {
        throw newUninitializedMessageException(result);
      }
      return result;
    }

    @java.lang.Override
    public com.chuntao.service.AnalyseRequest buildPartial() {
      com.chuntao.service.AnalyseRequest result = new com.chuntao.service.AnalyseRequest(this);
      if (bitField0_ != 0) { buildPartial0(result); }
      onBuilt();
      return result;
    }

    private void buildPartial0(com.chuntao.service.AnalyseRequest result) {
      int from_bitField0_ = bitField0_;
      if (((from_bitField0_ & 0x00000001) != 0)) {
        result.pM25_ = pM25_;
      }
      if (((from_bitField0_ & 0x00000002) != 0)) {
        result.temperature_ = temperature_;
      }
      if (((from_bitField0_ & 0x00000004) != 0)) {
        result.vOC_ = vOC_;
      }
      if (((from_bitField0_ & 0x00000008) != 0)) {
        result.humidity_ = humidity_;
      }
      if (((from_bitField0_ & 0x00000010) != 0)) {
        result.cO_ = cO_;
      }
    }

    @java.lang.Override
    public Builder mergeFrom(com.google.protobuf.Message other) {
      if (other instanceof com.chuntao.service.AnalyseRequest) {
        return mergeFrom((com.chuntao.service.AnalyseRequest)other);
      } else {
        super.mergeFrom(other);
        return this;
      }
    }

    public Builder mergeFrom(com.chuntao.service.AnalyseRequest other) {
      if (other == com.chuntao.service.AnalyseRequest.getDefaultInstance()) return this;
      if (other.getPM25() != 0F) {
        setPM25(other.getPM25());
      }
      if (other.getTemperature() != 0F) {
        setTemperature(other.getTemperature());
      }
      if (other.getVOC() != 0F) {
        setVOC(other.getVOC());
      }
      if (other.getHumidity() != 0F) {
        setHumidity(other.getHumidity());
      }
      if (other.getCO() != 0F) {
        setCO(other.getCO());
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
            case 21: {
              pM25_ = input.readFloat();
              bitField0_ |= 0x00000001;
              break;
            } // case 21
            case 29: {
              temperature_ = input.readFloat();
              bitField0_ |= 0x00000002;
              break;
            } // case 29
            case 37: {
              vOC_ = input.readFloat();
              bitField0_ |= 0x00000004;
              break;
            } // case 37
            case 45: {
              humidity_ = input.readFloat();
              bitField0_ |= 0x00000008;
              break;
            } // case 45
            case 53: {
              cO_ = input.readFloat();
              bitField0_ |= 0x00000010;
              break;
            } // case 53
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

    private float pM25_ ;
    /**
     * <pre>
     * PM2.5 density in μg/m3 of the air
     * </pre>
     *
     * <code>float PM25 = 2;</code>
     * @return The pM25.
     */
    @java.lang.Override
    public float getPM25() {
      return pM25_;
    }
    /**
     * <pre>
     * PM2.5 density in μg/m3 of the air
     * </pre>
     *
     * <code>float PM25 = 2;</code>
     * @param value The pM25 to set.
     * @return This builder for chaining.
     */
    public Builder setPM25(float value) {

      pM25_ = value;
      bitField0_ |= 0x00000001;
      onChanged();
      return this;
    }
    /**
     * <pre>
     * PM2.5 density in μg/m3 of the air
     * </pre>
     *
     * <code>float PM25 = 2;</code>
     * @return This builder for chaining.
     */
    public Builder clearPM25() {
      bitField0_ = (bitField0_ & ~0x00000001);
      pM25_ = 0F;
      onChanged();
      return this;
    }

    private float temperature_ ;
    /**
     * <pre>
     * Air temperature in Celsius
     * </pre>
     *
     * <code>float temperature = 3;</code>
     * @return The temperature.
     */
    @java.lang.Override
    public float getTemperature() {
      return temperature_;
    }
    /**
     * <pre>
     * Air temperature in Celsius
     * </pre>
     *
     * <code>float temperature = 3;</code>
     * @param value The temperature to set.
     * @return This builder for chaining.
     */
    public Builder setTemperature(float value) {

      temperature_ = value;
      bitField0_ |= 0x00000002;
      onChanged();
      return this;
    }
    /**
     * <pre>
     * Air temperature in Celsius
     * </pre>
     *
     * <code>float temperature = 3;</code>
     * @return This builder for chaining.
     */
    public Builder clearTemperature() {
      bitField0_ = (bitField0_ & ~0x00000002);
      temperature_ = 0F;
      onChanged();
      return this;
    }

    private float vOC_ ;
    /**
     * <pre>
     * VOC level in mg/m3
     * </pre>
     *
     * <code>float VOC = 4;</code>
     * @return The vOC.
     */
    @java.lang.Override
    public float getVOC() {
      return vOC_;
    }
    /**
     * <pre>
     * VOC level in mg/m3
     * </pre>
     *
     * <code>float VOC = 4;</code>
     * @param value The vOC to set.
     * @return This builder for chaining.
     */
    public Builder setVOC(float value) {

      vOC_ = value;
      bitField0_ |= 0x00000004;
      onChanged();
      return this;
    }
    /**
     * <pre>
     * VOC level in mg/m3
     * </pre>
     *
     * <code>float VOC = 4;</code>
     * @return This builder for chaining.
     */
    public Builder clearVOC() {
      bitField0_ = (bitField0_ & ~0x00000004);
      vOC_ = 0F;
      onChanged();
      return this;
    }

    private float humidity_ ;
    /**
     * <pre>
     * Humidity in Percentage
     * </pre>
     *
     * <code>float humidity = 5;</code>
     * @return The humidity.
     */
    @java.lang.Override
    public float getHumidity() {
      return humidity_;
    }
    /**
     * <pre>
     * Humidity in Percentage
     * </pre>
     *
     * <code>float humidity = 5;</code>
     * @param value The humidity to set.
     * @return This builder for chaining.
     */
    public Builder setHumidity(float value) {

      humidity_ = value;
      bitField0_ |= 0x00000008;
      onChanged();
      return this;
    }
    /**
     * <pre>
     * Humidity in Percentage
     * </pre>
     *
     * <code>float humidity = 5;</code>
     * @return This builder for chaining.
     */
    public Builder clearHumidity() {
      bitField0_ = (bitField0_ & ~0x00000008);
      humidity_ = 0F;
      onChanged();
      return this;
    }

    private float cO_ ;
    /**
     * <pre>
     * CO in ppm
     * </pre>
     *
     * <code>float CO = 6;</code>
     * @return The cO.
     */
    @java.lang.Override
    public float getCO() {
      return cO_;
    }
    /**
     * <pre>
     * CO in ppm
     * </pre>
     *
     * <code>float CO = 6;</code>
     * @param value The cO to set.
     * @return This builder for chaining.
     */
    public Builder setCO(float value) {

      cO_ = value;
      bitField0_ |= 0x00000010;
      onChanged();
      return this;
    }
    /**
     * <pre>
     * CO in ppm
     * </pre>
     *
     * <code>float CO = 6;</code>
     * @return This builder for chaining.
     */
    public Builder clearCO() {
      bitField0_ = (bitField0_ & ~0x00000010);
      cO_ = 0F;
      onChanged();
      return this;
    }

    // @@protoc_insertion_point(builder_scope:com.chuntao.AnalyseRequest)
  }

  // @@protoc_insertion_point(class_scope:com.chuntao.AnalyseRequest)
  private static final com.chuntao.service.AnalyseRequest DEFAULT_INSTANCE;
  static {
    DEFAULT_INSTANCE = new com.chuntao.service.AnalyseRequest();
  }

  public static com.chuntao.service.AnalyseRequest getDefaultInstance() {
    return DEFAULT_INSTANCE;
  }

  private static final com.google.protobuf.Parser<AnalyseRequest>
      PARSER = new com.google.protobuf.AbstractParser<AnalyseRequest>() {
    @java.lang.Override
    public AnalyseRequest parsePartialFrom(
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

  public static com.google.protobuf.Parser<AnalyseRequest> parser() {
    return PARSER;
  }

  @java.lang.Override
  public com.google.protobuf.Parser<AnalyseRequest> getParserForType() {
    return PARSER;
  }

  @java.lang.Override
  public com.chuntao.service.AnalyseRequest getDefaultInstanceForType() {
    return DEFAULT_INSTANCE;
  }

}

