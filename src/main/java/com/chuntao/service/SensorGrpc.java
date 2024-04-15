package com.chuntao.service;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 * <pre>
 * Sensor Service: monitor air index and analyse data
 * </pre>
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.62.2)",
    comments = "Source: Sensor.proto")
@io.grpc.stub.annotations.GrpcGenerated
public final class SensorGrpc {

  private SensorGrpc() {}

  public static final java.lang.String SERVICE_NAME = "com.chuntao.Sensor";

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
    if ((getGetSensorDataMethod = SensorGrpc.getGetSensorDataMethod) == null) {
      synchronized (SensorGrpc.class) {
        if ((getGetSensorDataMethod = SensorGrpc.getGetSensorDataMethod) == null) {
          SensorGrpc.getGetSensorDataMethod = getGetSensorDataMethod =
              io.grpc.MethodDescriptor.<com.chuntao.service.SensorRequest, com.chuntao.service.SensorResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "GetSensorData"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.chuntao.service.SensorRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.chuntao.service.SensorResponse.getDefaultInstance()))
              .setSchemaDescriptor(new SensorMethodDescriptorSupplier("GetSensorData"))
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
      methodType = io.grpc.MethodDescriptor.MethodType.SERVER_STREAMING)
  public static io.grpc.MethodDescriptor<com.chuntao.service.SensorResponse,
      com.chuntao.service.AnalyseResponse> getAnalyseSensorDataMethod() {
    io.grpc.MethodDescriptor<com.chuntao.service.SensorResponse, com.chuntao.service.AnalyseResponse> getAnalyseSensorDataMethod;
    if ((getAnalyseSensorDataMethod = SensorGrpc.getAnalyseSensorDataMethod) == null) {
      synchronized (SensorGrpc.class) {
        if ((getAnalyseSensorDataMethod = SensorGrpc.getAnalyseSensorDataMethod) == null) {
          SensorGrpc.getAnalyseSensorDataMethod = getAnalyseSensorDataMethod =
              io.grpc.MethodDescriptor.<com.chuntao.service.SensorResponse, com.chuntao.service.AnalyseResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.SERVER_STREAMING)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "AnalyseSensorData"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.chuntao.service.SensorResponse.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.chuntao.service.AnalyseResponse.getDefaultInstance()))
              .setSchemaDescriptor(new SensorMethodDescriptorSupplier("AnalyseSensorData"))
              .build();
        }
      }
    }
    return getAnalyseSensorDataMethod;
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
    default void getSensorData(com.chuntao.service.SensorRequest request,
        io.grpc.stub.StreamObserver<com.chuntao.service.SensorResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGetSensorDataMethod(), responseObserver);
    }

    /**
     * <pre>
     * Server-side streaming RPC for analysing sensor data based on sensor response
     * </pre>
     */
    default void analyseSensorData(com.chuntao.service.SensorResponse request,
        io.grpc.stub.StreamObserver<com.chuntao.service.AnalyseResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getAnalyseSensorDataMethod(), responseObserver);
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
    public void getSensorData(com.chuntao.service.SensorRequest request,
        io.grpc.stub.StreamObserver<com.chuntao.service.SensorResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetSensorDataMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * Server-side streaming RPC for analysing sensor data based on sensor response
     * </pre>
     */
    public void analyseSensorData(com.chuntao.service.SensorResponse request,
        io.grpc.stub.StreamObserver<com.chuntao.service.AnalyseResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncServerStreamingCall(
          getChannel().newCall(getAnalyseSensorDataMethod(), getCallOptions()), request, responseObserver);
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
    public com.chuntao.service.SensorResponse getSensorData(com.chuntao.service.SensorRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetSensorDataMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * Server-side streaming RPC for analysing sensor data based on sensor response
     * </pre>
     */
    public java.util.Iterator<com.chuntao.service.AnalyseResponse> analyseSensorData(
        com.chuntao.service.SensorResponse request) {
      return io.grpc.stub.ClientCalls.blockingServerStreamingCall(
          getChannel(), getAnalyseSensorDataMethod(), getCallOptions(), request);
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
    public com.google.common.util.concurrent.ListenableFuture<com.chuntao.service.SensorResponse> getSensorData(
        com.chuntao.service.SensorRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetSensorDataMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_GET_SENSOR_DATA = 0;
  private static final int METHODID_ANALYSE_SENSOR_DATA = 1;

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
        case METHODID_ANALYSE_SENSOR_DATA:
          serviceImpl.analyseSensorData((com.chuntao.service.SensorResponse) request,
              (io.grpc.stub.StreamObserver<com.chuntao.service.AnalyseResponse>) responseObserver);
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
          getGetSensorDataMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              com.chuntao.service.SensorRequest,
              com.chuntao.service.SensorResponse>(
                service, METHODID_GET_SENSOR_DATA)))
        .addMethod(
          getAnalyseSensorDataMethod(),
          io.grpc.stub.ServerCalls.asyncServerStreamingCall(
            new MethodHandlers<
              com.chuntao.service.SensorResponse,
              com.chuntao.service.AnalyseResponse>(
                service, METHODID_ANALYSE_SENSOR_DATA)))
        .build();
  }

  private static abstract class SensorBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    SensorBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return com.chuntao.service.SensorProto.getDescriptor();
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
              .addMethod(getGetSensorDataMethod())
              .addMethod(getAnalyseSensorDataMethod())
              .build();
        }
      }
    }
    return result;
  }
}
