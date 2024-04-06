package com.chuntao.service;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 * <pre>
 * Ventilation Service: automatically turn on ventilation based on air quality
 * </pre>
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.62.2)",
    comments = "Source: AirPollutionService.proto")
@io.grpc.stub.annotations.GrpcGenerated
public final class VentilationGrpc {

  private VentilationGrpc() {}

  public static final java.lang.String SERVICE_NAME = "com.chuntao.Ventilation";

  // Static method descriptors that strictly reflect the proto.
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
    if ((getVentilationControlMethod = VentilationGrpc.getVentilationControlMethod) == null) {
      synchronized (VentilationGrpc.class) {
        if ((getVentilationControlMethod = VentilationGrpc.getVentilationControlMethod) == null) {
          VentilationGrpc.getVentilationControlMethod = getVentilationControlMethod =
              io.grpc.MethodDescriptor.<com.chuntao.service.SensorResponse, com.chuntao.service.VentilationResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.BIDI_STREAMING)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "VentilationControl"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.chuntao.service.SensorResponse.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.chuntao.service.VentilationResponse.getDefaultInstance()))
              .setSchemaDescriptor(new VentilationMethodDescriptorSupplier("VentilationControl"))
              .build();
        }
      }
    }
    return getVentilationControlMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static VentilationStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<VentilationStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<VentilationStub>() {
        @java.lang.Override
        public VentilationStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new VentilationStub(channel, callOptions);
        }
      };
    return VentilationStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static VentilationBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<VentilationBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<VentilationBlockingStub>() {
        @java.lang.Override
        public VentilationBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new VentilationBlockingStub(channel, callOptions);
        }
      };
    return VentilationBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static VentilationFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<VentilationFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<VentilationFutureStub>() {
        @java.lang.Override
        public VentilationFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new VentilationFutureStub(channel, callOptions);
        }
      };
    return VentilationFutureStub.newStub(factory, channel);
  }

  /**
   * <pre>
   * Ventilation Service: automatically turn on ventilation based on air quality
   * </pre>
   */
  public interface AsyncService {

    /**
     * <pre>
     * Bidirectional streaming RPC for controlling ventilation based on sensor data
     * </pre>
     */
    default io.grpc.stub.StreamObserver<com.chuntao.service.SensorResponse> ventilationControl(
        io.grpc.stub.StreamObserver<com.chuntao.service.VentilationResponse> responseObserver) {
      return io.grpc.stub.ServerCalls.asyncUnimplementedStreamingCall(getVentilationControlMethod(), responseObserver);
    }
  }

  /**
   * Base class for the server implementation of the service Ventilation.
   * <pre>
   * Ventilation Service: automatically turn on ventilation based on air quality
   * </pre>
   */
  public static abstract class VentilationImplBase
      implements io.grpc.BindableService, AsyncService {

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return VentilationGrpc.bindService(this);
    }
  }

  /**
   * A stub to allow clients to do asynchronous rpc calls to service Ventilation.
   * <pre>
   * Ventilation Service: automatically turn on ventilation based on air quality
   * </pre>
   */
  public static final class VentilationStub
      extends io.grpc.stub.AbstractAsyncStub<VentilationStub> {
    private VentilationStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected VentilationStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new VentilationStub(channel, callOptions);
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
  }

  /**
   * A stub to allow clients to do synchronous rpc calls to service Ventilation.
   * <pre>
   * Ventilation Service: automatically turn on ventilation based on air quality
   * </pre>
   */
  public static final class VentilationBlockingStub
      extends io.grpc.stub.AbstractBlockingStub<VentilationBlockingStub> {
    private VentilationBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected VentilationBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new VentilationBlockingStub(channel, callOptions);
    }
  }

  /**
   * A stub to allow clients to do ListenableFuture-style rpc calls to service Ventilation.
   * <pre>
   * Ventilation Service: automatically turn on ventilation based on air quality
   * </pre>
   */
  public static final class VentilationFutureStub
      extends io.grpc.stub.AbstractFutureStub<VentilationFutureStub> {
    private VentilationFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected VentilationFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new VentilationFutureStub(channel, callOptions);
    }
  }

  private static final int METHODID_VENTILATION_CONTROL = 0;

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
        default:
          throw new AssertionError();
      }
    }
  }

  public static final io.grpc.ServerServiceDefinition bindService(AsyncService service) {
    return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
        .addMethod(
          getVentilationControlMethod(),
          io.grpc.stub.ServerCalls.asyncBidiStreamingCall(
            new MethodHandlers<
              com.chuntao.service.SensorResponse,
              com.chuntao.service.VentilationResponse>(
                service, METHODID_VENTILATION_CONTROL)))
        .build();
  }

  private static abstract class VentilationBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    VentilationBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return com.chuntao.service.AirPollutionService.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("Ventilation");
    }
  }

  private static final class VentilationFileDescriptorSupplier
      extends VentilationBaseDescriptorSupplier {
    VentilationFileDescriptorSupplier() {}
  }

  private static final class VentilationMethodDescriptorSupplier
      extends VentilationBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final java.lang.String methodName;

    VentilationMethodDescriptorSupplier(java.lang.String methodName) {
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
      synchronized (VentilationGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new VentilationFileDescriptorSupplier())
              .addMethod(getVentilationControlMethod())
              .build();
        }
      }
    }
    return result;
  }
}
