package com.chuntao.service;

import io.grpc.stub.StreamObserver;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 * <pre>
 * Notification Service: monitor air index and analyse data
 * </pre>
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.62.2)",
    comments = "Source: Notification.proto")
@io.grpc.stub.annotations.GrpcGenerated
public final class NotificationGrpc {

  private NotificationGrpc() {}

  public static final java.lang.String SERVICE_NAME = "com.chuntao.Notification";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<com.chuntao.service.SensorResponse,
      com.chuntao.service.NotificationMessage> getSensorNotificationsMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "SensorNotifications",
      requestType = com.chuntao.service.SensorResponse.class,
      responseType = com.chuntao.service.NotificationMessage.class,
      methodType = io.grpc.MethodDescriptor.MethodType.SERVER_STREAMING)
  public static io.grpc.MethodDescriptor<com.chuntao.service.SensorResponse,
      com.chuntao.service.NotificationMessage> getSensorNotificationsMethod() {
    io.grpc.MethodDescriptor<com.chuntao.service.SensorResponse, com.chuntao.service.NotificationMessage> getSensorNotificationsMethod;
    if ((getSensorNotificationsMethod = NotificationGrpc.getSensorNotificationsMethod) == null) {
      synchronized (NotificationGrpc.class) {
        if ((getSensorNotificationsMethod = NotificationGrpc.getSensorNotificationsMethod) == null) {
          NotificationGrpc.getSensorNotificationsMethod = getSensorNotificationsMethod =
              io.grpc.MethodDescriptor.<com.chuntao.service.SensorResponse, com.chuntao.service.NotificationMessage>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.SERVER_STREAMING)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "SensorNotifications"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.chuntao.service.SensorResponse.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.chuntao.service.NotificationMessage.getDefaultInstance()))
              .setSchemaDescriptor(new NotificationMethodDescriptorSupplier("SensorNotifications"))
              .build();
        }
      }
    }
    return getSensorNotificationsMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.chuntao.service.HVACResponse,
      com.chuntao.service.NotificationMessage> getVentNotificationsMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "VentNotifications",
      requestType = com.chuntao.service.HVACResponse.class,
      responseType = com.chuntao.service.NotificationMessage.class,
      methodType = io.grpc.MethodDescriptor.MethodType.SERVER_STREAMING)
  public static io.grpc.MethodDescriptor<com.chuntao.service.HVACResponse,
      com.chuntao.service.NotificationMessage> getVentNotificationsMethod() {
    io.grpc.MethodDescriptor<com.chuntao.service.HVACResponse, com.chuntao.service.NotificationMessage> getVentNotificationsMethod;
    if ((getVentNotificationsMethod = NotificationGrpc.getVentNotificationsMethod) == null) {
      synchronized (NotificationGrpc.class) {
        if ((getVentNotificationsMethod = NotificationGrpc.getVentNotificationsMethod) == null) {
          NotificationGrpc.getVentNotificationsMethod = getVentNotificationsMethod =
              io.grpc.MethodDescriptor.<com.chuntao.service.HVACResponse, com.chuntao.service.NotificationMessage>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.SERVER_STREAMING)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "VentNotifications"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.chuntao.service.HVACResponse.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.chuntao.service.NotificationMessage.getDefaultInstance()))
              .setSchemaDescriptor(new NotificationMethodDescriptorSupplier("VentNotifications"))
              .build();
        }
      }
    }
    return getVentNotificationsMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static NotificationStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<NotificationStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<NotificationStub>() {
        @java.lang.Override
        public NotificationStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new NotificationStub(channel, callOptions);
        }
      };
    return NotificationStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static NotificationBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<NotificationBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<NotificationBlockingStub>() {
        @java.lang.Override
        public NotificationBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new NotificationBlockingStub(channel, callOptions);
        }
      };
    return NotificationBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static NotificationFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<NotificationFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<NotificationFutureStub>() {
        @java.lang.Override
        public NotificationFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new NotificationFutureStub(channel, callOptions);
        }
      };
    return NotificationFutureStub.newStub(factory, channel);
  }

  /**
   * <pre>
   * Notification Service: monitor air index and analyse data
   * </pre>
   */
  public interface AsyncService {

    /**
     * <pre>
     * Server-side streaming RPC for receiving notifications based on analyze response
     * </pre>
     */
    default void sensorNotifications(com.chuntao.service.SensorResponse request,
        io.grpc.stub.StreamObserver<com.chuntao.service.NotificationMessage> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getSensorNotificationsMethod(), responseObserver);
    }

    /**
     * <pre>
     * Server-side streaming RPC for receiving notifications based on HVAC response
     * </pre>
     */
    default void ventNotifications(com.chuntao.service.HVACResponse request,
        io.grpc.stub.StreamObserver<com.chuntao.service.NotificationMessage> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getVentNotificationsMethod(), responseObserver);
    }
  }

  /**
   * Base class for the server implementation of the service Notification.
   * <pre>
   * Notification Service: monitor air index and analyse data
   * </pre>
   */
  public static abstract class NotificationImplBase
      implements io.grpc.BindableService, AsyncService {

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return NotificationGrpc.bindService(this);
    }

    public abstract StreamObserver<SensorResponse> sensorNotifications(StreamObserver<NotificationMessage> responseObserver);
  }

  /**
   * A stub to allow clients to do asynchronous rpc calls to service Notification.
   * <pre>
   * Notification Service: monitor air index and analyse data
   * </pre>
   */
  public static final class NotificationStub
      extends io.grpc.stub.AbstractAsyncStub<NotificationStub> {
    private NotificationStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected NotificationStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new NotificationStub(channel, callOptions);
    }

    /**
     * <pre>
     * Server-side streaming RPC for receiving notifications based on analyze response
     * </pre>
     */
    public void sensorNotifications(com.chuntao.service.SensorResponse request,
        io.grpc.stub.StreamObserver<com.chuntao.service.NotificationMessage> responseObserver) {
      io.grpc.stub.ClientCalls.asyncServerStreamingCall(
          getChannel().newCall(getSensorNotificationsMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * Server-side streaming RPC for receiving notifications based on HVAC response
     * </pre>
     */
    public void ventNotifications(com.chuntao.service.HVACResponse request,
        io.grpc.stub.StreamObserver<com.chuntao.service.NotificationMessage> responseObserver) {
      io.grpc.stub.ClientCalls.asyncServerStreamingCall(
          getChannel().newCall(getVentNotificationsMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   * A stub to allow clients to do synchronous rpc calls to service Notification.
   * <pre>
   * Notification Service: monitor air index and analyse data
   * </pre>
   */
  public static final class NotificationBlockingStub
      extends io.grpc.stub.AbstractBlockingStub<NotificationBlockingStub> {
    private NotificationBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected NotificationBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new NotificationBlockingStub(channel, callOptions);
    }

    /**
     * <pre>
     * Server-side streaming RPC for receiving notifications based on analyze response
     * </pre>
     */
    public java.util.Iterator<com.chuntao.service.NotificationMessage> sensorNotifications(
        com.chuntao.service.SensorResponse request) {
      return io.grpc.stub.ClientCalls.blockingServerStreamingCall(
          getChannel(), getSensorNotificationsMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * Server-side streaming RPC for receiving notifications based on HVAC response
     * </pre>
     */
    public java.util.Iterator<com.chuntao.service.NotificationMessage> ventNotifications(
        com.chuntao.service.HVACResponse request) {
      return io.grpc.stub.ClientCalls.blockingServerStreamingCall(
          getChannel(), getVentNotificationsMethod(), getCallOptions(), request);
    }
  }

  /**
   * A stub to allow clients to do ListenableFuture-style rpc calls to service Notification.
   * <pre>
   * Notification Service: monitor air index and analyse data
   * </pre>
   */
  public static final class NotificationFutureStub
      extends io.grpc.stub.AbstractFutureStub<NotificationFutureStub> {
    private NotificationFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected NotificationFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new NotificationFutureStub(channel, callOptions);
    }
  }

  private static final int METHODID_SENSOR_NOTIFICATIONS = 0;
  private static final int METHODID_VENT_NOTIFICATIONS = 1;

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
        case METHODID_SENSOR_NOTIFICATIONS:
          serviceImpl.sensorNotifications((com.chuntao.service.SensorResponse) request,
              (io.grpc.stub.StreamObserver<com.chuntao.service.NotificationMessage>) responseObserver);
          break;
        case METHODID_VENT_NOTIFICATIONS:
          serviceImpl.ventNotifications((com.chuntao.service.HVACResponse) request,
              (io.grpc.stub.StreamObserver<com.chuntao.service.NotificationMessage>) responseObserver);
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
          getSensorNotificationsMethod(),
          io.grpc.stub.ServerCalls.asyncServerStreamingCall(
            new MethodHandlers<
              com.chuntao.service.SensorResponse,
              com.chuntao.service.NotificationMessage>(
                service, METHODID_SENSOR_NOTIFICATIONS)))
        .addMethod(
          getVentNotificationsMethod(),
          io.grpc.stub.ServerCalls.asyncServerStreamingCall(
            new MethodHandlers<
              com.chuntao.service.HVACResponse,
              com.chuntao.service.NotificationMessage>(
                service, METHODID_VENT_NOTIFICATIONS)))
        .build();
  }

  private static abstract class NotificationBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    NotificationBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return com.chuntao.service.NotificationService.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("Notification");
    }
  }

  private static final class NotificationFileDescriptorSupplier
      extends NotificationBaseDescriptorSupplier {
    NotificationFileDescriptorSupplier() {}
  }

  private static final class NotificationMethodDescriptorSupplier
      extends NotificationBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final java.lang.String methodName;

    NotificationMethodDescriptorSupplier(java.lang.String methodName) {
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
      synchronized (NotificationGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new NotificationFileDescriptorSupplier())
              .addMethod(getSensorNotificationsMethod())
              .addMethod(getVentNotificationsMethod())
              .build();
        }
      }
    }
    return result;
  }
}
