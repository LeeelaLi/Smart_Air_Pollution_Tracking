package com.chuntao.service;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 * <pre>
 * HVAC Service: turn on HVAC based on analyse response
 * </pre>
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.62.2)",
    comments = "Source: HVAC.proto")
@io.grpc.stub.annotations.GrpcGenerated
public final class HVACGrpc {

  private HVACGrpc() {}

  public static final java.lang.String SERVICE_NAME = "com.chuntao.HVAC";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<com.chuntao.service.AnalyseResponse,
      com.chuntao.service.HVACResponse> getHVACControlMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "HVACControl",
      requestType = com.chuntao.service.AnalyseResponse.class,
      responseType = com.chuntao.service.HVACResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.CLIENT_STREAMING)
  public static io.grpc.MethodDescriptor<com.chuntao.service.AnalyseResponse,
      com.chuntao.service.HVACResponse> getHVACControlMethod() {
    io.grpc.MethodDescriptor<com.chuntao.service.AnalyseResponse, com.chuntao.service.HVACResponse> getHVACControlMethod;
    if ((getHVACControlMethod = HVACGrpc.getHVACControlMethod) == null) {
      synchronized (HVACGrpc.class) {
        if ((getHVACControlMethod = HVACGrpc.getHVACControlMethod) == null) {
          HVACGrpc.getHVACControlMethod = getHVACControlMethod =
              io.grpc.MethodDescriptor.<com.chuntao.service.AnalyseResponse, com.chuntao.service.HVACResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.CLIENT_STREAMING)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "HVACControl"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.chuntao.service.AnalyseResponse.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.chuntao.service.HVACResponse.getDefaultInstance()))
              .setSchemaDescriptor(new HVACMethodDescriptorSupplier("HVACControl"))
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
    if ((getHVACSwitchMethod = HVACGrpc.getHVACSwitchMethod) == null) {
      synchronized (HVACGrpc.class) {
        if ((getHVACSwitchMethod = HVACGrpc.getHVACSwitchMethod) == null) {
          HVACGrpc.getHVACSwitchMethod = getHVACSwitchMethod =
              io.grpc.MethodDescriptor.<com.chuntao.service.HVACCommand, com.chuntao.service.HVACResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.BIDI_STREAMING)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "HVACSwitch"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.chuntao.service.HVACCommand.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.chuntao.service.HVACResponse.getDefaultInstance()))
              .setSchemaDescriptor(new HVACMethodDescriptorSupplier("HVACSwitch"))
              .build();
        }
      }
    }
    return getHVACSwitchMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static HVACStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<HVACStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<HVACStub>() {
        @java.lang.Override
        public HVACStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new HVACStub(channel, callOptions);
        }
      };
    return HVACStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static HVACBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<HVACBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<HVACBlockingStub>() {
        @java.lang.Override
        public HVACBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new HVACBlockingStub(channel, callOptions);
        }
      };
    return HVACBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static HVACFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<HVACFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<HVACFutureStub>() {
        @java.lang.Override
        public HVACFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new HVACFutureStub(channel, callOptions);
        }
      };
    return HVACFutureStub.newStub(factory, channel);
  }

  /**
   * <pre>
   * HVAC Service: turn on HVAC based on analyse response
   * </pre>
   */
  public interface AsyncService {

    /**
     * <pre>
     * Client-side streaming RPC for controlling HVAC
     * </pre>
     */
    default io.grpc.stub.StreamObserver<com.chuntao.service.AnalyseResponse> hVACControl(
        io.grpc.stub.StreamObserver<com.chuntao.service.HVACResponse> responseObserver) {
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
  }

  /**
   * Base class for the server implementation of the service HVAC.
   * <pre>
   * HVAC Service: turn on HVAC based on analyse response
   * </pre>
   */
  public static abstract class HVACImplBase
      implements io.grpc.BindableService, AsyncService {

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return HVACGrpc.bindService(this);
    }
  }

  /**
   * A stub to allow clients to do asynchronous rpc calls to service HVAC.
   * <pre>
   * HVAC Service: turn on HVAC based on analyse response
   * </pre>
   */
  public static final class HVACStub
      extends io.grpc.stub.AbstractAsyncStub<HVACStub> {
    private HVACStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected HVACStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new HVACStub(channel, callOptions);
    }

    /**
     * <pre>
     * Client-side streaming RPC for controlling HVAC
     * </pre>
     */
    public io.grpc.stub.StreamObserver<com.chuntao.service.AnalyseResponse> hVACControl(
        io.grpc.stub.StreamObserver<com.chuntao.service.HVACResponse> responseObserver) {
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
  }

  /**
   * A stub to allow clients to do synchronous rpc calls to service HVAC.
   * <pre>
   * HVAC Service: turn on HVAC based on analyse response
   * </pre>
   */
  public static final class HVACBlockingStub
      extends io.grpc.stub.AbstractBlockingStub<HVACBlockingStub> {
    private HVACBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected HVACBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new HVACBlockingStub(channel, callOptions);
    }
  }

  /**
   * A stub to allow clients to do ListenableFuture-style rpc calls to service HVAC.
   * <pre>
   * HVAC Service: turn on HVAC based on analyse response
   * </pre>
   */
  public static final class HVACFutureStub
      extends io.grpc.stub.AbstractFutureStub<HVACFutureStub> {
    private HVACFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected HVACFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new HVACFutureStub(channel, callOptions);
    }
  }

  private static final int METHODID_HVACCONTROL = 0;
  private static final int METHODID_HVACSWITCH = 1;

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
        case METHODID_HVACCONTROL:
          return (io.grpc.stub.StreamObserver<Req>) serviceImpl.hVACControl(
              (io.grpc.stub.StreamObserver<com.chuntao.service.HVACResponse>) responseObserver);
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
          getHVACControlMethod(),
          io.grpc.stub.ServerCalls.asyncClientStreamingCall(
            new MethodHandlers<
              com.chuntao.service.AnalyseResponse,
              com.chuntao.service.HVACResponse>(
                service, METHODID_HVACCONTROL)))
        .addMethod(
          getHVACSwitchMethod(),
          io.grpc.stub.ServerCalls.asyncBidiStreamingCall(
            new MethodHandlers<
              com.chuntao.service.HVACCommand,
              com.chuntao.service.HVACResponse>(
                service, METHODID_HVACSWITCH)))
        .build();
  }

  private static abstract class HVACBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    HVACBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return com.chuntao.service.HVACService.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("HVAC");
    }
  }

  private static final class HVACFileDescriptorSupplier
      extends HVACBaseDescriptorSupplier {
    HVACFileDescriptorSupplier() {}
  }

  private static final class HVACMethodDescriptorSupplier
      extends HVACBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final java.lang.String methodName;

    HVACMethodDescriptorSupplier(java.lang.String methodName) {
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
      synchronized (HVACGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new HVACFileDescriptorSupplier())
              .addMethod(getHVACControlMethod())
              .addMethod(getHVACSwitchMethod())
              .build();
        }
      }
    }
    return result;
  }
}
