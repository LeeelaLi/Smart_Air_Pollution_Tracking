package com.chuntao.service;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.62.2)",
    comments = "Source: SmartAirPollution.proto")
@io.grpc.stub.annotations.GrpcGenerated
public final class SmartAirPollutionGrpc {

  private SmartAirPollutionGrpc() {}

  public static final java.lang.String SERVICE_NAME = "com.chuntao.SmartAirPollution";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<com.chuntao.service.SensorRequest,
      com.chuntao.service.SensorResponse> getGetSensorDataMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetSensorData",
      requestType = com.chuntao.service.SensorRequest.class,
      responseType = com.chuntao.service.SensorResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.chuntao.service.SensorRequest,
      com.chuntao.service.SensorResponse> getGetSensorDataMethod() {
    io.grpc.MethodDescriptor<com.chuntao.service.SensorRequest, com.chuntao.service.SensorResponse> getGetSensorDataMethod;
    if ((getGetSensorDataMethod = SmartAirPollutionGrpc.getGetSensorDataMethod) == null) {
      synchronized (SmartAirPollutionGrpc.class) {
        if ((getGetSensorDataMethod = SmartAirPollutionGrpc.getGetSensorDataMethod) == null) {
          SmartAirPollutionGrpc.getGetSensorDataMethod = getGetSensorDataMethod =
              io.grpc.MethodDescriptor.<com.chuntao.service.SensorRequest, com.chuntao.service.SensorResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "GetSensorData"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.chuntao.service.SensorRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.chuntao.service.SensorResponse.getDefaultInstance()))
              .setSchemaDescriptor(new SmartAirPollutionMethodDescriptorSupplier("GetSensorData"))
              .build();
        }
      }
    }
    return getGetSensorDataMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.chuntao.service.SensorResponse,
      com.chuntao.service.AnalyseResponse> getAnalyseSensorDataMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "AnalyseSensorData",
      requestType = com.chuntao.service.SensorResponse.class,
      responseType = com.chuntao.service.AnalyseResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.CLIENT_STREAMING)
  public static io.grpc.MethodDescriptor<com.chuntao.service.SensorResponse,
      com.chuntao.service.AnalyseResponse> getAnalyseSensorDataMethod() {
    io.grpc.MethodDescriptor<com.chuntao.service.SensorResponse, com.chuntao.service.AnalyseResponse> getAnalyseSensorDataMethod;
    if ((getAnalyseSensorDataMethod = SmartAirPollutionGrpc.getAnalyseSensorDataMethod) == null) {
      synchronized (SmartAirPollutionGrpc.class) {
        if ((getAnalyseSensorDataMethod = SmartAirPollutionGrpc.getAnalyseSensorDataMethod) == null) {
          SmartAirPollutionGrpc.getAnalyseSensorDataMethod = getAnalyseSensorDataMethod =
              io.grpc.MethodDescriptor.<com.chuntao.service.SensorResponse, com.chuntao.service.AnalyseResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.CLIENT_STREAMING)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "AnalyseSensorData"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.chuntao.service.SensorResponse.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.chuntao.service.AnalyseResponse.getDefaultInstance()))
              .setSchemaDescriptor(new SmartAirPollutionMethodDescriptorSupplier("AnalyseSensorData"))
              .build();
        }
      }
    }
    return getAnalyseSensorDataMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.chuntao.service.AnalyseResponse,
      com.chuntao.service.HVACCommand> getHVACControlMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "HVACControl",
      requestType = com.chuntao.service.AnalyseResponse.class,
      responseType = com.chuntao.service.HVACCommand.class,
      methodType = io.grpc.MethodDescriptor.MethodType.CLIENT_STREAMING)
  public static io.grpc.MethodDescriptor<com.chuntao.service.AnalyseResponse,
      com.chuntao.service.HVACCommand> getHVACControlMethod() {
    io.grpc.MethodDescriptor<com.chuntao.service.AnalyseResponse, com.chuntao.service.HVACCommand> getHVACControlMethod;
    if ((getHVACControlMethod = SmartAirPollutionGrpc.getHVACControlMethod) == null) {
      synchronized (SmartAirPollutionGrpc.class) {
        if ((getHVACControlMethod = SmartAirPollutionGrpc.getHVACControlMethod) == null) {
          SmartAirPollutionGrpc.getHVACControlMethod = getHVACControlMethod =
              io.grpc.MethodDescriptor.<com.chuntao.service.AnalyseResponse, com.chuntao.service.HVACCommand>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.CLIENT_STREAMING)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "HVACControl"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.chuntao.service.AnalyseResponse.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.chuntao.service.HVACCommand.getDefaultInstance()))
              .setSchemaDescriptor(new SmartAirPollutionMethodDescriptorSupplier("HVACControl"))
              .build();
        }
      }
    }
    return getHVACControlMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.chuntao.service.HVACCommand,
      com.chuntao.service.HVACResponse> getHVACSwitchMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "HVACSwitch",
      requestType = com.chuntao.service.HVACCommand.class,
      responseType = com.chuntao.service.HVACResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.BIDI_STREAMING)
  public static io.grpc.MethodDescriptor<com.chuntao.service.HVACCommand,
      com.chuntao.service.HVACResponse> getHVACSwitchMethod() {
    io.grpc.MethodDescriptor<com.chuntao.service.HVACCommand, com.chuntao.service.HVACResponse> getHVACSwitchMethod;
    if ((getHVACSwitchMethod = SmartAirPollutionGrpc.getHVACSwitchMethod) == null) {
      synchronized (SmartAirPollutionGrpc.class) {
        if ((getHVACSwitchMethod = SmartAirPollutionGrpc.getHVACSwitchMethod) == null) {
          SmartAirPollutionGrpc.getHVACSwitchMethod = getHVACSwitchMethod =
              io.grpc.MethodDescriptor.<com.chuntao.service.HVACCommand, com.chuntao.service.HVACResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.BIDI_STREAMING)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "HVACSwitch"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.chuntao.service.HVACCommand.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.chuntao.service.HVACResponse.getDefaultInstance()))
              .setSchemaDescriptor(new SmartAirPollutionMethodDescriptorSupplier("HVACSwitch"))
              .build();
        }
      }
    }
    return getHVACSwitchMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.chuntao.service.AnalyseResponse,
      com.chuntao.service.SensorMessage> getSensorNotificationsMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "SensorNotifications",
      requestType = com.chuntao.service.AnalyseResponse.class,
      responseType = com.chuntao.service.SensorMessage.class,
      methodType = io.grpc.MethodDescriptor.MethodType.SERVER_STREAMING)
  public static io.grpc.MethodDescriptor<com.chuntao.service.AnalyseResponse,
      com.chuntao.service.SensorMessage> getSensorNotificationsMethod() {
    io.grpc.MethodDescriptor<com.chuntao.service.AnalyseResponse, com.chuntao.service.SensorMessage> getSensorNotificationsMethod;
    if ((getSensorNotificationsMethod = SmartAirPollutionGrpc.getSensorNotificationsMethod) == null) {
      synchronized (SmartAirPollutionGrpc.class) {
        if ((getSensorNotificationsMethod = SmartAirPollutionGrpc.getSensorNotificationsMethod) == null) {
          SmartAirPollutionGrpc.getSensorNotificationsMethod = getSensorNotificationsMethod =
              io.grpc.MethodDescriptor.<com.chuntao.service.AnalyseResponse, com.chuntao.service.SensorMessage>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.SERVER_STREAMING)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "SensorNotifications"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.chuntao.service.AnalyseResponse.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.chuntao.service.SensorMessage.getDefaultInstance()))
              .setSchemaDescriptor(new SmartAirPollutionMethodDescriptorSupplier("SensorNotifications"))
              .build();
        }
      }
    }
    return getSensorNotificationsMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.chuntao.service.HVACResponse,
      com.chuntao.service.HVACMessage> getHVACNotificationsMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "HVACNotifications",
      requestType = com.chuntao.service.HVACResponse.class,
      responseType = com.chuntao.service.HVACMessage.class,
      methodType = io.grpc.MethodDescriptor.MethodType.SERVER_STREAMING)
  public static io.grpc.MethodDescriptor<com.chuntao.service.HVACResponse,
      com.chuntao.service.HVACMessage> getHVACNotificationsMethod() {
    io.grpc.MethodDescriptor<com.chuntao.service.HVACResponse, com.chuntao.service.HVACMessage> getHVACNotificationsMethod;
    if ((getHVACNotificationsMethod = SmartAirPollutionGrpc.getHVACNotificationsMethod) == null) {
      synchronized (SmartAirPollutionGrpc.class) {
        if ((getHVACNotificationsMethod = SmartAirPollutionGrpc.getHVACNotificationsMethod) == null) {
          SmartAirPollutionGrpc.getHVACNotificationsMethod = getHVACNotificationsMethod =
              io.grpc.MethodDescriptor.<com.chuntao.service.HVACResponse, com.chuntao.service.HVACMessage>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.SERVER_STREAMING)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "HVACNotifications"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.chuntao.service.HVACResponse.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.chuntao.service.HVACMessage.getDefaultInstance()))
              .setSchemaDescriptor(new SmartAirPollutionMethodDescriptorSupplier("HVACNotifications"))
              .build();
        }
      }
    }
    return getHVACNotificationsMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static SmartAirPollutionStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<SmartAirPollutionStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<SmartAirPollutionStub>() {
        @java.lang.Override
        public SmartAirPollutionStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new SmartAirPollutionStub(channel, callOptions);
        }
      };
    return SmartAirPollutionStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static SmartAirPollutionBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<SmartAirPollutionBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<SmartAirPollutionBlockingStub>() {
        @java.lang.Override
        public SmartAirPollutionBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new SmartAirPollutionBlockingStub(channel, callOptions);
        }
      };
    return SmartAirPollutionBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static SmartAirPollutionFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<SmartAirPollutionFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<SmartAirPollutionFutureStub>() {
        @java.lang.Override
        public SmartAirPollutionFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new SmartAirPollutionFutureStub(channel, callOptions);
        }
      };
    return SmartAirPollutionFutureStub.newStub(factory, channel);
  }

  /**
   */
  public interface AsyncService {

    /**
     * <pre>
     * Sensor Service: monitor air index and analyse data
     * Simple RPC for collecting sensor data
     * </pre>
     */
    default void getSensorData(com.chuntao.service.SensorRequest request,
        io.grpc.stub.StreamObserver<com.chuntao.service.SensorResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGetSensorDataMethod(), responseObserver);
    }

    /**
     * <pre>
     * Client-side streaming RPC for analysing sensor data based on sensor response
     * </pre>
     */
    default io.grpc.stub.StreamObserver<com.chuntao.service.SensorResponse> analyseSensorData(
        io.grpc.stub.StreamObserver<com.chuntao.service.AnalyseResponse> responseObserver) {
      return io.grpc.stub.ServerCalls.asyncUnimplementedStreamingCall(getAnalyseSensorDataMethod(), responseObserver);
    }

    /**
     * <pre>
     * HVAC Service: turn on HVAC based on analyse response
     * Client-side streaming RPC for controlling HVAC
     * </pre>
     */
    default io.grpc.stub.StreamObserver<com.chuntao.service.AnalyseResponse> hVACControl(
        io.grpc.stub.StreamObserver<com.chuntao.service.HVACCommand> responseObserver) {
      return io.grpc.stub.ServerCalls.asyncUnimplementedStreamingCall(getHVACControlMethod(), responseObserver);
    }

    /**
     * <pre>
     * Bidirectional streaming RPC for HVAC switching
     * </pre>
     */
    default io.grpc.stub.StreamObserver<com.chuntao.service.HVACCommand> hVACSwitch(
        io.grpc.stub.StreamObserver<com.chuntao.service.HVACResponse> responseObserver) {
      return io.grpc.stub.ServerCalls.asyncUnimplementedStreamingCall(getHVACSwitchMethod(), responseObserver);
    }

    /**
     * <pre>
     * Notification Service: monitor air index and analyse data
     * Server-side streaming RPC for receiving notifications based on analyze response
     * </pre>
     */
    default void sensorNotifications(com.chuntao.service.AnalyseResponse request,
        io.grpc.stub.StreamObserver<com.chuntao.service.SensorMessage> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getSensorNotificationsMethod(), responseObserver);
    }

    /**
     * <pre>
     * Server-side streaming RPC for receiving notifications based on HVAC response
     * </pre>
     */
    default void hVACNotifications(com.chuntao.service.HVACResponse request,
        io.grpc.stub.StreamObserver<com.chuntao.service.HVACMessage> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getHVACNotificationsMethod(), responseObserver);
    }
  }

  /**
   * Base class for the server implementation of the service SmartAirPollution.
   */
  public static abstract class SmartAirPollutionImplBase
      implements io.grpc.BindableService, AsyncService {

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return SmartAirPollutionGrpc.bindService(this);
    }
  }

  /**
   * A stub to allow clients to do asynchronous rpc calls to service SmartAirPollution.
   */
  public static final class SmartAirPollutionStub
      extends io.grpc.stub.AbstractAsyncStub<SmartAirPollutionStub> {
    private SmartAirPollutionStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected SmartAirPollutionStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new SmartAirPollutionStub(channel, callOptions);
    }

    /**
     * <pre>
     * Sensor Service: monitor air index and analyse data
     * Simple RPC for collecting sensor data
     * </pre>
     */
    public void getSensorData(com.chuntao.service.SensorRequest request,
        io.grpc.stub.StreamObserver<com.chuntao.service.SensorResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetSensorDataMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * Client-side streaming RPC for analysing sensor data based on sensor response
     * </pre>
     */
    public io.grpc.stub.StreamObserver<com.chuntao.service.SensorResponse> analyseSensorData(
        io.grpc.stub.StreamObserver<com.chuntao.service.AnalyseResponse> responseObserver) {
      return io.grpc.stub.ClientCalls.asyncClientStreamingCall(
          getChannel().newCall(getAnalyseSensorDataMethod(), getCallOptions()), responseObserver);
    }

    /**
     * <pre>
     * HVAC Service: turn on HVAC based on analyse response
     * Client-side streaming RPC for controlling HVAC
     * </pre>
     */
    public io.grpc.stub.StreamObserver<com.chuntao.service.AnalyseResponse> hVACControl(
        io.grpc.stub.StreamObserver<com.chuntao.service.HVACCommand> responseObserver) {
      return io.grpc.stub.ClientCalls.asyncClientStreamingCall(
          getChannel().newCall(getHVACControlMethod(), getCallOptions()), responseObserver);
    }

    /**
     * <pre>
     * Bidirectional streaming RPC for HVAC switching
     * </pre>
     */
    public io.grpc.stub.StreamObserver<com.chuntao.service.HVACCommand> hVACSwitch(
        io.grpc.stub.StreamObserver<com.chuntao.service.HVACResponse> responseObserver) {
      return io.grpc.stub.ClientCalls.asyncBidiStreamingCall(
          getChannel().newCall(getHVACSwitchMethod(), getCallOptions()), responseObserver);
    }

    /**
     * <pre>
     * Notification Service: monitor air index and analyse data
     * Server-side streaming RPC for receiving notifications based on analyze response
     * </pre>
     */
    public void sensorNotifications(com.chuntao.service.AnalyseResponse request,
        io.grpc.stub.StreamObserver<com.chuntao.service.SensorMessage> responseObserver) {
      io.grpc.stub.ClientCalls.asyncServerStreamingCall(
          getChannel().newCall(getSensorNotificationsMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * Server-side streaming RPC for receiving notifications based on HVAC response
     * </pre>
     */
    public void hVACNotifications(com.chuntao.service.HVACResponse request,
        io.grpc.stub.StreamObserver<com.chuntao.service.HVACMessage> responseObserver) {
      io.grpc.stub.ClientCalls.asyncServerStreamingCall(
          getChannel().newCall(getHVACNotificationsMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   * A stub to allow clients to do synchronous rpc calls to service SmartAirPollution.
   */
  public static final class SmartAirPollutionBlockingStub
      extends io.grpc.stub.AbstractBlockingStub<SmartAirPollutionBlockingStub> {
    private SmartAirPollutionBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected SmartAirPollutionBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new SmartAirPollutionBlockingStub(channel, callOptions);
    }

    /**
     * <pre>
     * Sensor Service: monitor air index and analyse data
     * Simple RPC for collecting sensor data
     * </pre>
     */
    public com.chuntao.service.SensorResponse getSensorData(com.chuntao.service.SensorRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetSensorDataMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * Notification Service: monitor air index and analyse data
     * Server-side streaming RPC for receiving notifications based on analyze response
     * </pre>
     */
    public java.util.Iterator<com.chuntao.service.SensorMessage> sensorNotifications(
        com.chuntao.service.AnalyseResponse request) {
      return io.grpc.stub.ClientCalls.blockingServerStreamingCall(
          getChannel(), getSensorNotificationsMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * Server-side streaming RPC for receiving notifications based on HVAC response
     * </pre>
     */
    public java.util.Iterator<com.chuntao.service.HVACMessage> hVACNotifications(
        com.chuntao.service.HVACResponse request) {
      return io.grpc.stub.ClientCalls.blockingServerStreamingCall(
          getChannel(), getHVACNotificationsMethod(), getCallOptions(), request);
    }
  }

  /**
   * A stub to allow clients to do ListenableFuture-style rpc calls to service SmartAirPollution.
   */
  public static final class SmartAirPollutionFutureStub
      extends io.grpc.stub.AbstractFutureStub<SmartAirPollutionFutureStub> {
    private SmartAirPollutionFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected SmartAirPollutionFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new SmartAirPollutionFutureStub(channel, callOptions);
    }

    /**
     * <pre>
     * Sensor Service: monitor air index and analyse data
     * Simple RPC for collecting sensor data
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.chuntao.service.SensorResponse> getSensorData(
        com.chuntao.service.SensorRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetSensorDataMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_GET_SENSOR_DATA = 0;
  private static final int METHODID_SENSOR_NOTIFICATIONS = 1;
  private static final int METHODID_HVACNOTIFICATIONS = 2;
  private static final int METHODID_ANALYSE_SENSOR_DATA = 3;
  private static final int METHODID_HVACCONTROL = 4;
  private static final int METHODID_HVACSWITCH = 5;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final AsyncService serviceImpl;
    private final int methodId;

    MethodHandlers(AsyncService serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_GET_SENSOR_DATA:
          serviceImpl.getSensorData((com.chuntao.service.SensorRequest) request,
              (io.grpc.stub.StreamObserver<com.chuntao.service.SensorResponse>) responseObserver);
          break;
        case METHODID_SENSOR_NOTIFICATIONS:
          serviceImpl.sensorNotifications((com.chuntao.service.AnalyseResponse) request,
              (io.grpc.stub.StreamObserver<com.chuntao.service.SensorMessage>) responseObserver);
          break;
        case METHODID_HVACNOTIFICATIONS:
          serviceImpl.hVACNotifications((com.chuntao.service.HVACResponse) request,
              (io.grpc.stub.StreamObserver<com.chuntao.service.HVACMessage>) responseObserver);
          break;
        default:
          throw new AssertionError();
      }
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public io.grpc.stub.StreamObserver<Req> invoke(
        io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_ANALYSE_SENSOR_DATA:
          return (io.grpc.stub.StreamObserver<Req>) serviceImpl.analyseSensorData(
              (io.grpc.stub.StreamObserver<com.chuntao.service.AnalyseResponse>) responseObserver);
        case METHODID_HVACCONTROL:
          return (io.grpc.stub.StreamObserver<Req>) serviceImpl.hVACControl(
              (io.grpc.stub.StreamObserver<com.chuntao.service.HVACCommand>) responseObserver);
        case METHODID_HVACSWITCH:
          return (io.grpc.stub.StreamObserver<Req>) serviceImpl.hVACSwitch(
              (io.grpc.stub.StreamObserver<com.chuntao.service.HVACResponse>) responseObserver);
        default:
          throw new AssertionError();
      }
    }
  }

  public static final io.grpc.ServerServiceDefinition bindService(AsyncService service) {
    return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
        .addMethod(
          getGetSensorDataMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              com.chuntao.service.SensorRequest,
              com.chuntao.service.SensorResponse>(
                service, METHODID_GET_SENSOR_DATA)))
        .addMethod(
          getAnalyseSensorDataMethod(),
          io.grpc.stub.ServerCalls.asyncClientStreamingCall(
            new MethodHandlers<
              com.chuntao.service.SensorResponse,
              com.chuntao.service.AnalyseResponse>(
                service, METHODID_ANALYSE_SENSOR_DATA)))
        .addMethod(
          getHVACControlMethod(),
          io.grpc.stub.ServerCalls.asyncClientStreamingCall(
            new MethodHandlers<
              com.chuntao.service.AnalyseResponse,
              com.chuntao.service.HVACCommand>(
                service, METHODID_HVACCONTROL)))
        .addMethod(
          getHVACSwitchMethod(),
          io.grpc.stub.ServerCalls.asyncBidiStreamingCall(
            new MethodHandlers<
              com.chuntao.service.HVACCommand,
              com.chuntao.service.HVACResponse>(
                service, METHODID_HVACSWITCH)))
        .addMethod(
          getSensorNotificationsMethod(),
          io.grpc.stub.ServerCalls.asyncServerStreamingCall(
            new MethodHandlers<
              com.chuntao.service.AnalyseResponse,
              com.chuntao.service.SensorMessage>(
                service, METHODID_SENSOR_NOTIFICATIONS)))
        .addMethod(
          getHVACNotificationsMethod(),
          io.grpc.stub.ServerCalls.asyncServerStreamingCall(
            new MethodHandlers<
              com.chuntao.service.HVACResponse,
              com.chuntao.service.HVACMessage>(
                service, METHODID_HVACNOTIFICATIONS)))
        .build();
  }

  private static abstract class SmartAirPollutionBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    SmartAirPollutionBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return com.chuntao.service.SensorService.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("SmartAirPollution");
    }
  }

  private static final class SmartAirPollutionFileDescriptorSupplier
      extends SmartAirPollutionBaseDescriptorSupplier {
    SmartAirPollutionFileDescriptorSupplier() {}
  }

  private static final class SmartAirPollutionMethodDescriptorSupplier
      extends SmartAirPollutionBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final java.lang.String methodName;

    SmartAirPollutionMethodDescriptorSupplier(java.lang.String methodName) {
      this.methodName = methodName;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.MethodDescriptor getMethodDescriptor() {
      return getServiceDescriptor().findMethodByName(methodName);
    }
  }

  private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    io.grpc.ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (SmartAirPollutionGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new SmartAirPollutionFileDescriptorSupplier())
              .addMethod(getGetSensorDataMethod())
              .addMethod(getAnalyseSensorDataMethod())
              .addMethod(getHVACControlMethod())
              .addMethod(getHVACSwitchMethod())
              .addMethod(getSensorNotificationsMethod())
              .addMethod(getHVACNotificationsMethod())
              .build();
        }
      }
    }
    return result;
  }
}
