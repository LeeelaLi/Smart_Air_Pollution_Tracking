package com.chuntao.service;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 * <pre>
 * Sensor Service: monitor air index and analyse data
 * </pre>
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.62.2)",
    comments = "Source: AirPollutionService.proto")
@io.grpc.stub.annotations.GrpcGenerated
public final class AirPollutionTrackGrpc {

  private AirPollutionTrackGrpc() {}

  public static final java.lang.String SERVICE_NAME = "com.chuntao.AirPollutionTrack";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<com.chuntao.service.SensorRequest,
      com.chuntao.service.SensorResponse> getGetAllSensorDataMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetAllSensorData",
      requestType = com.chuntao.service.SensorRequest.class,
      responseType = com.chuntao.service.SensorResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.chuntao.service.SensorRequest,
      com.chuntao.service.SensorResponse> getGetAllSensorDataMethod() {
    io.grpc.MethodDescriptor<com.chuntao.service.SensorRequest, com.chuntao.service.SensorResponse> getGetAllSensorDataMethod;
    if ((getGetAllSensorDataMethod = AirPollutionTrackGrpc.getGetAllSensorDataMethod) == null) {
      synchronized (AirPollutionTrackGrpc.class) {
        if ((getGetAllSensorDataMethod = AirPollutionTrackGrpc.getGetAllSensorDataMethod) == null) {
          AirPollutionTrackGrpc.getGetAllSensorDataMethod = getGetAllSensorDataMethod =
              io.grpc.MethodDescriptor.<com.chuntao.service.SensorRequest, com.chuntao.service.SensorResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "GetAllSensorData"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.chuntao.service.SensorRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.chuntao.service.SensorResponse.getDefaultInstance()))
              .setSchemaDescriptor(new AirPollutionTrackMethodDescriptorSupplier("GetAllSensorData"))
              .build();
        }
      }
    }
    return getGetAllSensorDataMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.chuntao.service.SensorRequest,
      com.chuntao.service.SensorResponse> getGetSingleSensorDataMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetSingleSensorData",
      requestType = com.chuntao.service.SensorRequest.class,
      responseType = com.chuntao.service.SensorResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.SERVER_STREAMING)
  public static io.grpc.MethodDescriptor<com.chuntao.service.SensorRequest,
      com.chuntao.service.SensorResponse> getGetSingleSensorDataMethod() {
    io.grpc.MethodDescriptor<com.chuntao.service.SensorRequest, com.chuntao.service.SensorResponse> getGetSingleSensorDataMethod;
    if ((getGetSingleSensorDataMethod = AirPollutionTrackGrpc.getGetSingleSensorDataMethod) == null) {
      synchronized (AirPollutionTrackGrpc.class) {
        if ((getGetSingleSensorDataMethod = AirPollutionTrackGrpc.getGetSingleSensorDataMethod) == null) {
          AirPollutionTrackGrpc.getGetSingleSensorDataMethod = getGetSingleSensorDataMethod =
              io.grpc.MethodDescriptor.<com.chuntao.service.SensorRequest, com.chuntao.service.SensorResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.SERVER_STREAMING)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "GetSingleSensorData"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.chuntao.service.SensorRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.chuntao.service.SensorResponse.getDefaultInstance()))
              .setSchemaDescriptor(new AirPollutionTrackMethodDescriptorSupplier("GetSingleSensorData"))
              .build();
        }
      }
    }
    return getGetSingleSensorDataMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.chuntao.service.SensorResponse,
      com.chuntao.service.VentilationResponse> getVentilationControlMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "VentilationControl",
      requestType = com.chuntao.service.SensorResponse.class,
      responseType = com.chuntao.service.VentilationResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.BIDI_STREAMING)
  public static io.grpc.MethodDescriptor<com.chuntao.service.SensorResponse,
      com.chuntao.service.VentilationResponse> getVentilationControlMethod() {
    io.grpc.MethodDescriptor<com.chuntao.service.SensorResponse, com.chuntao.service.VentilationResponse> getVentilationControlMethod;
    if ((getVentilationControlMethod = AirPollutionTrackGrpc.getVentilationControlMethod) == null) {
      synchronized (AirPollutionTrackGrpc.class) {
        if ((getVentilationControlMethod = AirPollutionTrackGrpc.getVentilationControlMethod) == null) {
          AirPollutionTrackGrpc.getVentilationControlMethod = getVentilationControlMethod =
              io.grpc.MethodDescriptor.<com.chuntao.service.SensorResponse, com.chuntao.service.VentilationResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.BIDI_STREAMING)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "VentilationControl"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.chuntao.service.SensorResponse.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.chuntao.service.VentilationResponse.getDefaultInstance()))
              .setSchemaDescriptor(new AirPollutionTrackMethodDescriptorSupplier("VentilationControl"))
              .build();
        }
      }
    }
    return getVentilationControlMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.chuntao.service.VentilationResponse,
      com.chuntao.service.NotificationMessage> getReceiveNotificationsMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "ReceiveNotifications",
      requestType = com.chuntao.service.VentilationResponse.class,
      responseType = com.chuntao.service.NotificationMessage.class,
      methodType = io.grpc.MethodDescriptor.MethodType.CLIENT_STREAMING)
  public static io.grpc.MethodDescriptor<com.chuntao.service.VentilationResponse,
      com.chuntao.service.NotificationMessage> getReceiveNotificationsMethod() {
    io.grpc.MethodDescriptor<com.chuntao.service.VentilationResponse, com.chuntao.service.NotificationMessage> getReceiveNotificationsMethod;
    if ((getReceiveNotificationsMethod = AirPollutionTrackGrpc.getReceiveNotificationsMethod) == null) {
      synchronized (AirPollutionTrackGrpc.class) {
        if ((getReceiveNotificationsMethod = AirPollutionTrackGrpc.getReceiveNotificationsMethod) == null) {
          AirPollutionTrackGrpc.getReceiveNotificationsMethod = getReceiveNotificationsMethod =
              io.grpc.MethodDescriptor.<com.chuntao.service.VentilationResponse, com.chuntao.service.NotificationMessage>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.CLIENT_STREAMING)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "ReceiveNotifications"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.chuntao.service.VentilationResponse.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.chuntao.service.NotificationMessage.getDefaultInstance()))
              .setSchemaDescriptor(new AirPollutionTrackMethodDescriptorSupplier("ReceiveNotifications"))
              .build();
        }
      }
    }
    return getReceiveNotificationsMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static AirPollutionTrackStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<AirPollutionTrackStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<AirPollutionTrackStub>() {
        @java.lang.Override
        public AirPollutionTrackStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new AirPollutionTrackStub(channel, callOptions);
        }
      };
    return AirPollutionTrackStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static AirPollutionTrackBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<AirPollutionTrackBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<AirPollutionTrackBlockingStub>() {
        @java.lang.Override
        public AirPollutionTrackBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new AirPollutionTrackBlockingStub(channel, callOptions);
        }
      };
    return AirPollutionTrackBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static AirPollutionTrackFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<AirPollutionTrackFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<AirPollutionTrackFutureStub>() {
        @java.lang.Override
        public AirPollutionTrackFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new AirPollutionTrackFutureStub(channel, callOptions);
        }
      };
    return AirPollutionTrackFutureStub.newStub(factory, channel);
  }

  /**
   * <pre>
   * Sensor Service: monitor air index and analyse data
   * </pre>
   */
  public interface AsyncService {

    /**
     * <pre>
     * Simple RPC for collecting sensor data
     * </pre>
     */
    default void getAllSensorData(com.chuntao.service.SensorRequest request,
        io.grpc.stub.StreamObserver<com.chuntao.service.SensorResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGetAllSensorDataMethod(), responseObserver);
    }

    /**
     * <pre>
     * Server-side streaming for requesting single detected item
     * </pre>
     */
    default void getSingleSensorData(com.chuntao.service.SensorRequest request,
        io.grpc.stub.StreamObserver<com.chuntao.service.SensorResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGetSingleSensorDataMethod(), responseObserver);
    }

    /**
     * <pre>
     * Bidirectional streaming RPC for controlling ventilation based on sensor data
     * </pre>
     */
    default io.grpc.stub.StreamObserver<com.chuntao.service.SensorResponse> ventilationControl(
        io.grpc.stub.StreamObserver<com.chuntao.service.VentilationResponse> responseObserver) {
      return io.grpc.stub.ServerCalls.asyncUnimplementedStreamingCall(getVentilationControlMethod(), responseObserver);
    }

    /**
     * <pre>
     * Client-side streaming RPC for receiving notifications
     * </pre>
     */
    default io.grpc.stub.StreamObserver<com.chuntao.service.VentilationResponse> receiveNotifications(
        io.grpc.stub.StreamObserver<com.chuntao.service.NotificationMessage> responseObserver) {
      return io.grpc.stub.ServerCalls.asyncUnimplementedStreamingCall(getReceiveNotificationsMethod(), responseObserver);
    }
  }

  /**
   * Base class for the server implementation of the service AirPollutionTrack.
   * <pre>
   * Sensor Service: monitor air index and analyse data
   * </pre>
   */
  public static abstract class AirPollutionTrackImplBase
      implements io.grpc.BindableService, AsyncService {

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return AirPollutionTrackGrpc.bindService(this);
    }
  }

  /**
   * A stub to allow clients to do asynchronous rpc calls to service AirPollutionTrack.
   * <pre>
   * Sensor Service: monitor air index and analyse data
   * </pre>
   */
  public static final class AirPollutionTrackStub
      extends io.grpc.stub.AbstractAsyncStub<AirPollutionTrackStub> {
    private AirPollutionTrackStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected AirPollutionTrackStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new AirPollutionTrackStub(channel, callOptions);
    }

    /**
     * <pre>
     * Simple RPC for collecting sensor data
     * </pre>
     */
    public void getAllSensorData(com.chuntao.service.SensorRequest request,
        io.grpc.stub.StreamObserver<com.chuntao.service.SensorResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetAllSensorDataMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * Server-side streaming for requesting single detected item
     * </pre>
     */
    public void getSingleSensorData(com.chuntao.service.SensorRequest request,
        io.grpc.stub.StreamObserver<com.chuntao.service.SensorResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncServerStreamingCall(
          getChannel().newCall(getGetSingleSensorDataMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * Bidirectional streaming RPC for controlling ventilation based on sensor data
     * </pre>
     */
    public io.grpc.stub.StreamObserver<com.chuntao.service.SensorResponse> ventilationControl(
        io.grpc.stub.StreamObserver<com.chuntao.service.VentilationResponse> responseObserver) {
      return io.grpc.stub.ClientCalls.asyncBidiStreamingCall(
          getChannel().newCall(getVentilationControlMethod(), getCallOptions()), responseObserver);
    }

    /**
     * <pre>
     * Client-side streaming RPC for receiving notifications
     * </pre>
     */
    public io.grpc.stub.StreamObserver<com.chuntao.service.VentilationResponse> receiveNotifications(
        io.grpc.stub.StreamObserver<com.chuntao.service.NotificationMessage> responseObserver) {
      return io.grpc.stub.ClientCalls.asyncClientStreamingCall(
          getChannel().newCall(getReceiveNotificationsMethod(), getCallOptions()), responseObserver);
    }
  }

  /**
   * A stub to allow clients to do synchronous rpc calls to service AirPollutionTrack.
   * <pre>
   * Sensor Service: monitor air index and analyse data
   * </pre>
   */
  public static final class AirPollutionTrackBlockingStub
      extends io.grpc.stub.AbstractBlockingStub<AirPollutionTrackBlockingStub> {
    private AirPollutionTrackBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected AirPollutionTrackBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new AirPollutionTrackBlockingStub(channel, callOptions);
    }

    /**
     * <pre>
     * Simple RPC for collecting sensor data
     * </pre>
     */
    public com.chuntao.service.SensorResponse getAllSensorData(com.chuntao.service.SensorRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetAllSensorDataMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * Server-side streaming for requesting single detected item
     * </pre>
     */
    public java.util.Iterator<com.chuntao.service.SensorResponse> getSingleSensorData(
        com.chuntao.service.SensorRequest request) {
      return io.grpc.stub.ClientCalls.blockingServerStreamingCall(
          getChannel(), getGetSingleSensorDataMethod(), getCallOptions(), request);
    }
  }

  /**
   * A stub to allow clients to do ListenableFuture-style rpc calls to service AirPollutionTrack.
   * <pre>
   * Sensor Service: monitor air index and analyse data
   * </pre>
   */
  public static final class AirPollutionTrackFutureStub
      extends io.grpc.stub.AbstractFutureStub<AirPollutionTrackFutureStub> {
    private AirPollutionTrackFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected AirPollutionTrackFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new AirPollutionTrackFutureStub(channel, callOptions);
    }

    /**
     * <pre>
     * Simple RPC for collecting sensor data
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.chuntao.service.SensorResponse> getAllSensorData(
        com.chuntao.service.SensorRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetAllSensorDataMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_GET_ALL_SENSOR_DATA = 0;
  private static final int METHODID_GET_SINGLE_SENSOR_DATA = 1;
  private static final int METHODID_VENTILATION_CONTROL = 2;
  private static final int METHODID_RECEIVE_NOTIFICATIONS = 3;

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
        case METHODID_GET_ALL_SENSOR_DATA:
          serviceImpl.getAllSensorData((com.chuntao.service.SensorRequest) request,
              (io.grpc.stub.StreamObserver<com.chuntao.service.SensorResponse>) responseObserver);
          break;
        case METHODID_GET_SINGLE_SENSOR_DATA:
          serviceImpl.getSingleSensorData((com.chuntao.service.SensorRequest) request,
              (io.grpc.stub.StreamObserver<com.chuntao.service.SensorResponse>) responseObserver);
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
        case METHODID_VENTILATION_CONTROL:
          return (io.grpc.stub.StreamObserver<Req>) serviceImpl.ventilationControl(
              (io.grpc.stub.StreamObserver<com.chuntao.service.VentilationResponse>) responseObserver);
        case METHODID_RECEIVE_NOTIFICATIONS:
          return (io.grpc.stub.StreamObserver<Req>) serviceImpl.receiveNotifications(
              (io.grpc.stub.StreamObserver<com.chuntao.service.NotificationMessage>) responseObserver);
        default:
          throw new AssertionError();
      }
    }
  }

  public static final io.grpc.ServerServiceDefinition bindService(AsyncService service) {
    return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
        .addMethod(
          getGetAllSensorDataMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              com.chuntao.service.SensorRequest,
              com.chuntao.service.SensorResponse>(
                service, METHODID_GET_ALL_SENSOR_DATA)))
        .addMethod(
          getGetSingleSensorDataMethod(),
          io.grpc.stub.ServerCalls.asyncServerStreamingCall(
            new MethodHandlers<
              com.chuntao.service.SensorRequest,
              com.chuntao.service.SensorResponse>(
                service, METHODID_GET_SINGLE_SENSOR_DATA)))
        .addMethod(
          getVentilationControlMethod(),
          io.grpc.stub.ServerCalls.asyncBidiStreamingCall(
            new MethodHandlers<
              com.chuntao.service.SensorResponse,
              com.chuntao.service.VentilationResponse>(
                service, METHODID_VENTILATION_CONTROL)))
        .addMethod(
          getReceiveNotificationsMethod(),
          io.grpc.stub.ServerCalls.asyncClientStreamingCall(
            new MethodHandlers<
              com.chuntao.service.VentilationResponse,
              com.chuntao.service.NotificationMessage>(
                service, METHODID_RECEIVE_NOTIFICATIONS)))
        .build();
  }

  private static abstract class AirPollutionTrackBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    AirPollutionTrackBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return com.chuntao.service.AirPollutionService.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("AirPollutionTrack");
    }
  }

  private static final class AirPollutionTrackFileDescriptorSupplier
      extends AirPollutionTrackBaseDescriptorSupplier {
    AirPollutionTrackFileDescriptorSupplier() {}
  }

  private static final class AirPollutionTrackMethodDescriptorSupplier
      extends AirPollutionTrackBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final java.lang.String methodName;

    AirPollutionTrackMethodDescriptorSupplier(java.lang.String methodName) {
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
      synchronized (AirPollutionTrackGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new AirPollutionTrackFileDescriptorSupplier())
              .addMethod(getGetAllSensorDataMethod())
              .addMethod(getGetSingleSensorDataMethod())
              .addMethod(getVentilationControlMethod())
              .addMethod(getReceiveNotificationsMethod())
              .build();
        }
      }
    }
    return result;
  }
}
