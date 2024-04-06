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
public final class SensorGrpc {

  private SensorGrpc() {}

  public static final java.lang.String SERVICE_NAME = "com.chuntao.Sensor";

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
    if ((getGetAllSensorDataMethod = SensorGrpc.getGetAllSensorDataMethod) == null) {
      synchronized (SensorGrpc.class) {
        if ((getGetAllSensorDataMethod = SensorGrpc.getGetAllSensorDataMethod) == null) {
          SensorGrpc.getGetAllSensorDataMethod = getGetAllSensorDataMethod =
              io.grpc.MethodDescriptor.<com.chuntao.service.SensorRequest, com.chuntao.service.SensorResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "GetAllSensorData"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.chuntao.service.SensorRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.chuntao.service.SensorResponse.getDefaultInstance()))
              .setSchemaDescriptor(new SensorMethodDescriptorSupplier("GetAllSensorData"))
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
    if ((getGetSingleSensorDataMethod = SensorGrpc.getGetSingleSensorDataMethod) == null) {
      synchronized (SensorGrpc.class) {
        if ((getGetSingleSensorDataMethod = SensorGrpc.getGetSingleSensorDataMethod) == null) {
          SensorGrpc.getGetSingleSensorDataMethod = getGetSingleSensorDataMethod =
              io.grpc.MethodDescriptor.<com.chuntao.service.SensorRequest, com.chuntao.service.SensorResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.SERVER_STREAMING)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "GetSingleSensorData"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.chuntao.service.SensorRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.chuntao.service.SensorResponse.getDefaultInstance()))
              .setSchemaDescriptor(new SensorMethodDescriptorSupplier("GetSingleSensorData"))
              .build();
        }
      }
    }
    return getGetSingleSensorDataMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static SensorStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<SensorStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<SensorStub>() {
        @java.lang.Override
        public SensorStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new SensorStub(channel, callOptions);
        }
      };
    return SensorStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static SensorBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<SensorBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<SensorBlockingStub>() {
        @java.lang.Override
        public SensorBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new SensorBlockingStub(channel, callOptions);
        }
      };
    return SensorBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static SensorFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<SensorFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<SensorFutureStub>() {
        @java.lang.Override
        public SensorFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new SensorFutureStub(channel, callOptions);
        }
      };
    return SensorFutureStub.newStub(factory, channel);
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
     * Server-side streaming for updated sensor data
     * </pre>
     */
    default void getSingleSensorData(com.chuntao.service.SensorRequest request,
        io.grpc.stub.StreamObserver<com.chuntao.service.SensorResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGetSingleSensorDataMethod(), responseObserver);
    }
  }

  /**
   * Base class for the server implementation of the service Sensor.
   * <pre>
   * Sensor Service: monitor air index and analyse data
   * </pre>
   */
  public static abstract class SensorImplBase
      implements io.grpc.BindableService, AsyncService {

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return SensorGrpc.bindService(this);
    }
  }

  /**
   * A stub to allow clients to do asynchronous rpc calls to service Sensor.
   * <pre>
   * Sensor Service: monitor air index and analyse data
   * </pre>
   */
  public static final class SensorStub
      extends io.grpc.stub.AbstractAsyncStub<SensorStub> {
    private SensorStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected SensorStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new SensorStub(channel, callOptions);
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
     * Server-side streaming for updated sensor data
     * </pre>
     */
    public void getSingleSensorData(com.chuntao.service.SensorRequest request,
        io.grpc.stub.StreamObserver<com.chuntao.service.SensorResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncServerStreamingCall(
          getChannel().newCall(getGetSingleSensorDataMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   * A stub to allow clients to do synchronous rpc calls to service Sensor.
   * <pre>
   * Sensor Service: monitor air index and analyse data
   * </pre>
   */
  public static final class SensorBlockingStub
      extends io.grpc.stub.AbstractBlockingStub<SensorBlockingStub> {
    private SensorBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected SensorBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new SensorBlockingStub(channel, callOptions);
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
     * Server-side streaming for updated sensor data
     * </pre>
     */
    public java.util.Iterator<com.chuntao.service.SensorResponse> getSingleSensorData(
        com.chuntao.service.SensorRequest request) {
      return io.grpc.stub.ClientCalls.blockingServerStreamingCall(
          getChannel(), getGetSingleSensorDataMethod(), getCallOptions(), request);
    }
  }

  /**
   * A stub to allow clients to do ListenableFuture-style rpc calls to service Sensor.
   * <pre>
   * Sensor Service: monitor air index and analyse data
   * </pre>
   */
  public static final class SensorFutureStub
      extends io.grpc.stub.AbstractFutureStub<SensorFutureStub> {
    private SensorFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected SensorFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new SensorFutureStub(channel, callOptions);
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
        .build();
  }

  private static abstract class SensorBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    SensorBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return com.chuntao.service.AirPollutionService.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("Sensor");
    }
  }

  private static final class SensorFileDescriptorSupplier
      extends SensorBaseDescriptorSupplier {
    SensorFileDescriptorSupplier() {}
  }

  private static final class SensorMethodDescriptorSupplier
      extends SensorBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final java.lang.String methodName;

    SensorMethodDescriptorSupplier(java.lang.String methodName) {
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
      synchronized (SensorGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new SensorFileDescriptorSupplier())
              .addMethod(getGetAllSensorDataMethod())
              .addMethod(getGetSingleSensorDataMethod())
              .build();
        }
      }
    }
    return result;
  }
}
