package com.fededge.grpc;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.61.1)",
    comments = "Source: fededge.proto")
@io.grpc.stub.annotations.GrpcGenerated
public final class FederatedCoordinatorGrpc {

  private FederatedCoordinatorGrpc() {}

  public static final java.lang.String SERVICE_NAME = "fededge.FederatedCoordinator";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<com.fededge.grpc.RegisterClientRequest,
      com.fededge.grpc.RegisterClientResponse> getRegisterClientMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "RegisterClient",
      requestType = com.fededge.grpc.RegisterClientRequest.class,
      responseType = com.fededge.grpc.RegisterClientResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.fededge.grpc.RegisterClientRequest,
      com.fededge.grpc.RegisterClientResponse> getRegisterClientMethod() {
    io.grpc.MethodDescriptor<com.fededge.grpc.RegisterClientRequest, com.fededge.grpc.RegisterClientResponse> getRegisterClientMethod;
    if ((getRegisterClientMethod = FederatedCoordinatorGrpc.getRegisterClientMethod) == null) {
      synchronized (FederatedCoordinatorGrpc.class) {
        if ((getRegisterClientMethod = FederatedCoordinatorGrpc.getRegisterClientMethod) == null) {
          FederatedCoordinatorGrpc.getRegisterClientMethod = getRegisterClientMethod =
              io.grpc.MethodDescriptor.<com.fededge.grpc.RegisterClientRequest, com.fededge.grpc.RegisterClientResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "RegisterClient"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.fededge.grpc.RegisterClientRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.fededge.grpc.RegisterClientResponse.getDefaultInstance()))
              .setSchemaDescriptor(new FederatedCoordinatorMethodDescriptorSupplier("RegisterClient"))
              .build();
        }
      }
    }
    return getRegisterClientMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.fededge.grpc.GetTrainingJobRequest,
      com.fededge.grpc.GetTrainingJobResponse> getGetTrainingJobMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetTrainingJob",
      requestType = com.fededge.grpc.GetTrainingJobRequest.class,
      responseType = com.fededge.grpc.GetTrainingJobResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.fededge.grpc.GetTrainingJobRequest,
      com.fededge.grpc.GetTrainingJobResponse> getGetTrainingJobMethod() {
    io.grpc.MethodDescriptor<com.fededge.grpc.GetTrainingJobRequest, com.fededge.grpc.GetTrainingJobResponse> getGetTrainingJobMethod;
    if ((getGetTrainingJobMethod = FederatedCoordinatorGrpc.getGetTrainingJobMethod) == null) {
      synchronized (FederatedCoordinatorGrpc.class) {
        if ((getGetTrainingJobMethod = FederatedCoordinatorGrpc.getGetTrainingJobMethod) == null) {
          FederatedCoordinatorGrpc.getGetTrainingJobMethod = getGetTrainingJobMethod =
              io.grpc.MethodDescriptor.<com.fededge.grpc.GetTrainingJobRequest, com.fededge.grpc.GetTrainingJobResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "GetTrainingJob"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.fededge.grpc.GetTrainingJobRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.fededge.grpc.GetTrainingJobResponse.getDefaultInstance()))
              .setSchemaDescriptor(new FederatedCoordinatorMethodDescriptorSupplier("GetTrainingJob"))
              .build();
        }
      }
    }
    return getGetTrainingJobMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.fededge.grpc.ModelUpdate,
      com.fededge.grpc.SubmitUpdateResponse> getSubmitUpdateMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "SubmitUpdate",
      requestType = com.fededge.grpc.ModelUpdate.class,
      responseType = com.fededge.grpc.SubmitUpdateResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.fededge.grpc.ModelUpdate,
      com.fededge.grpc.SubmitUpdateResponse> getSubmitUpdateMethod() {
    io.grpc.MethodDescriptor<com.fededge.grpc.ModelUpdate, com.fededge.grpc.SubmitUpdateResponse> getSubmitUpdateMethod;
    if ((getSubmitUpdateMethod = FederatedCoordinatorGrpc.getSubmitUpdateMethod) == null) {
      synchronized (FederatedCoordinatorGrpc.class) {
        if ((getSubmitUpdateMethod = FederatedCoordinatorGrpc.getSubmitUpdateMethod) == null) {
          FederatedCoordinatorGrpc.getSubmitUpdateMethod = getSubmitUpdateMethod =
              io.grpc.MethodDescriptor.<com.fededge.grpc.ModelUpdate, com.fededge.grpc.SubmitUpdateResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "SubmitUpdate"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.fededge.grpc.ModelUpdate.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.fededge.grpc.SubmitUpdateResponse.getDefaultInstance()))
              .setSchemaDescriptor(new FederatedCoordinatorMethodDescriptorSupplier("SubmitUpdate"))
              .build();
        }
      }
    }
    return getSubmitUpdateMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.fededge.grpc.HeartbeatRequest,
      com.fededge.grpc.HeartbeatResponse> getHeartbeatMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "Heartbeat",
      requestType = com.fededge.grpc.HeartbeatRequest.class,
      responseType = com.fededge.grpc.HeartbeatResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.fededge.grpc.HeartbeatRequest,
      com.fededge.grpc.HeartbeatResponse> getHeartbeatMethod() {
    io.grpc.MethodDescriptor<com.fededge.grpc.HeartbeatRequest, com.fededge.grpc.HeartbeatResponse> getHeartbeatMethod;
    if ((getHeartbeatMethod = FederatedCoordinatorGrpc.getHeartbeatMethod) == null) {
      synchronized (FederatedCoordinatorGrpc.class) {
        if ((getHeartbeatMethod = FederatedCoordinatorGrpc.getHeartbeatMethod) == null) {
          FederatedCoordinatorGrpc.getHeartbeatMethod = getHeartbeatMethod =
              io.grpc.MethodDescriptor.<com.fededge.grpc.HeartbeatRequest, com.fededge.grpc.HeartbeatResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "Heartbeat"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.fededge.grpc.HeartbeatRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.fededge.grpc.HeartbeatResponse.getDefaultInstance()))
              .setSchemaDescriptor(new FederatedCoordinatorMethodDescriptorSupplier("Heartbeat"))
              .build();
        }
      }
    }
    return getHeartbeatMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.fededge.grpc.GetGlobalModelRequest,
      com.fededge.grpc.GetGlobalModelResponse> getGetGlobalModelMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetGlobalModel",
      requestType = com.fededge.grpc.GetGlobalModelRequest.class,
      responseType = com.fededge.grpc.GetGlobalModelResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.fededge.grpc.GetGlobalModelRequest,
      com.fededge.grpc.GetGlobalModelResponse> getGetGlobalModelMethod() {
    io.grpc.MethodDescriptor<com.fededge.grpc.GetGlobalModelRequest, com.fededge.grpc.GetGlobalModelResponse> getGetGlobalModelMethod;
    if ((getGetGlobalModelMethod = FederatedCoordinatorGrpc.getGetGlobalModelMethod) == null) {
      synchronized (FederatedCoordinatorGrpc.class) {
        if ((getGetGlobalModelMethod = FederatedCoordinatorGrpc.getGetGlobalModelMethod) == null) {
          FederatedCoordinatorGrpc.getGetGlobalModelMethod = getGetGlobalModelMethod =
              io.grpc.MethodDescriptor.<com.fededge.grpc.GetGlobalModelRequest, com.fededge.grpc.GetGlobalModelResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "GetGlobalModel"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.fededge.grpc.GetGlobalModelRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.fededge.grpc.GetGlobalModelResponse.getDefaultInstance()))
              .setSchemaDescriptor(new FederatedCoordinatorMethodDescriptorSupplier("GetGlobalModel"))
              .build();
        }
      }
    }
    return getGetGlobalModelMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static FederatedCoordinatorStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<FederatedCoordinatorStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<FederatedCoordinatorStub>() {
        @java.lang.Override
        public FederatedCoordinatorStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new FederatedCoordinatorStub(channel, callOptions);
        }
      };
    return FederatedCoordinatorStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static FederatedCoordinatorBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<FederatedCoordinatorBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<FederatedCoordinatorBlockingStub>() {
        @java.lang.Override
        public FederatedCoordinatorBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new FederatedCoordinatorBlockingStub(channel, callOptions);
        }
      };
    return FederatedCoordinatorBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static FederatedCoordinatorFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<FederatedCoordinatorFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<FederatedCoordinatorFutureStub>() {
        @java.lang.Override
        public FederatedCoordinatorFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new FederatedCoordinatorFutureStub(channel, callOptions);
        }
      };
    return FederatedCoordinatorFutureStub.newStub(factory, channel);
  }

  /**
   */
  public interface AsyncService {

    /**
     */
    default void registerClient(com.fededge.grpc.RegisterClientRequest request,
        io.grpc.stub.StreamObserver<com.fededge.grpc.RegisterClientResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getRegisterClientMethod(), responseObserver);
    }

    /**
     */
    default void getTrainingJob(com.fededge.grpc.GetTrainingJobRequest request,
        io.grpc.stub.StreamObserver<com.fededge.grpc.GetTrainingJobResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGetTrainingJobMethod(), responseObserver);
    }

    /**
     */
    default void submitUpdate(com.fededge.grpc.ModelUpdate request,
        io.grpc.stub.StreamObserver<com.fededge.grpc.SubmitUpdateResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getSubmitUpdateMethod(), responseObserver);
    }

    /**
     */
    default void heartbeat(com.fededge.grpc.HeartbeatRequest request,
        io.grpc.stub.StreamObserver<com.fededge.grpc.HeartbeatResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getHeartbeatMethod(), responseObserver);
    }

    /**
     */
    default void getGlobalModel(com.fededge.grpc.GetGlobalModelRequest request,
        io.grpc.stub.StreamObserver<com.fededge.grpc.GetGlobalModelResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGetGlobalModelMethod(), responseObserver);
    }
  }

  /**
   * Base class for the server implementation of the service FederatedCoordinator.
   */
  public static abstract class FederatedCoordinatorImplBase
      implements io.grpc.BindableService, AsyncService {

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return FederatedCoordinatorGrpc.bindService(this);
    }
  }

  /**
   * A stub to allow clients to do asynchronous rpc calls to service FederatedCoordinator.
   */
  public static final class FederatedCoordinatorStub
      extends io.grpc.stub.AbstractAsyncStub<FederatedCoordinatorStub> {
    private FederatedCoordinatorStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected FederatedCoordinatorStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new FederatedCoordinatorStub(channel, callOptions);
    }

    /**
     */
    public void registerClient(com.fededge.grpc.RegisterClientRequest request,
        io.grpc.stub.StreamObserver<com.fededge.grpc.RegisterClientResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getRegisterClientMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void getTrainingJob(com.fededge.grpc.GetTrainingJobRequest request,
        io.grpc.stub.StreamObserver<com.fededge.grpc.GetTrainingJobResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetTrainingJobMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void submitUpdate(com.fededge.grpc.ModelUpdate request,
        io.grpc.stub.StreamObserver<com.fededge.grpc.SubmitUpdateResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getSubmitUpdateMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void heartbeat(com.fededge.grpc.HeartbeatRequest request,
        io.grpc.stub.StreamObserver<com.fededge.grpc.HeartbeatResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getHeartbeatMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void getGlobalModel(com.fededge.grpc.GetGlobalModelRequest request,
        io.grpc.stub.StreamObserver<com.fededge.grpc.GetGlobalModelResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetGlobalModelMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   * A stub to allow clients to do synchronous rpc calls to service FederatedCoordinator.
   */
  public static final class FederatedCoordinatorBlockingStub
      extends io.grpc.stub.AbstractBlockingStub<FederatedCoordinatorBlockingStub> {
    private FederatedCoordinatorBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected FederatedCoordinatorBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new FederatedCoordinatorBlockingStub(channel, callOptions);
    }

    /**
     */
    public com.fededge.grpc.RegisterClientResponse registerClient(com.fededge.grpc.RegisterClientRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getRegisterClientMethod(), getCallOptions(), request);
    }

    /**
     */
    public com.fededge.grpc.GetTrainingJobResponse getTrainingJob(com.fededge.grpc.GetTrainingJobRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetTrainingJobMethod(), getCallOptions(), request);
    }

    /**
     */
    public com.fededge.grpc.SubmitUpdateResponse submitUpdate(com.fededge.grpc.ModelUpdate request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getSubmitUpdateMethod(), getCallOptions(), request);
    }

    /**
     */
    public com.fededge.grpc.HeartbeatResponse heartbeat(com.fededge.grpc.HeartbeatRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getHeartbeatMethod(), getCallOptions(), request);
    }

    /**
     */
    public com.fededge.grpc.GetGlobalModelResponse getGlobalModel(com.fededge.grpc.GetGlobalModelRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetGlobalModelMethod(), getCallOptions(), request);
    }
  }

  /**
   * A stub to allow clients to do ListenableFuture-style rpc calls to service FederatedCoordinator.
   */
  public static final class FederatedCoordinatorFutureStub
      extends io.grpc.stub.AbstractFutureStub<FederatedCoordinatorFutureStub> {
    private FederatedCoordinatorFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected FederatedCoordinatorFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new FederatedCoordinatorFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.fededge.grpc.RegisterClientResponse> registerClient(
        com.fededge.grpc.RegisterClientRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getRegisterClientMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.fededge.grpc.GetTrainingJobResponse> getTrainingJob(
        com.fededge.grpc.GetTrainingJobRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetTrainingJobMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.fededge.grpc.SubmitUpdateResponse> submitUpdate(
        com.fededge.grpc.ModelUpdate request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getSubmitUpdateMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.fededge.grpc.HeartbeatResponse> heartbeat(
        com.fededge.grpc.HeartbeatRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getHeartbeatMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.fededge.grpc.GetGlobalModelResponse> getGlobalModel(
        com.fededge.grpc.GetGlobalModelRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetGlobalModelMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_REGISTER_CLIENT = 0;
  private static final int METHODID_GET_TRAINING_JOB = 1;
  private static final int METHODID_SUBMIT_UPDATE = 2;
  private static final int METHODID_HEARTBEAT = 3;
  private static final int METHODID_GET_GLOBAL_MODEL = 4;

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
        case METHODID_REGISTER_CLIENT:
          serviceImpl.registerClient((com.fededge.grpc.RegisterClientRequest) request,
              (io.grpc.stub.StreamObserver<com.fededge.grpc.RegisterClientResponse>) responseObserver);
          break;
        case METHODID_GET_TRAINING_JOB:
          serviceImpl.getTrainingJob((com.fededge.grpc.GetTrainingJobRequest) request,
              (io.grpc.stub.StreamObserver<com.fededge.grpc.GetTrainingJobResponse>) responseObserver);
          break;
        case METHODID_SUBMIT_UPDATE:
          serviceImpl.submitUpdate((com.fededge.grpc.ModelUpdate) request,
              (io.grpc.stub.StreamObserver<com.fededge.grpc.SubmitUpdateResponse>) responseObserver);
          break;
        case METHODID_HEARTBEAT:
          serviceImpl.heartbeat((com.fededge.grpc.HeartbeatRequest) request,
              (io.grpc.stub.StreamObserver<com.fededge.grpc.HeartbeatResponse>) responseObserver);
          break;
        case METHODID_GET_GLOBAL_MODEL:
          serviceImpl.getGlobalModel((com.fededge.grpc.GetGlobalModelRequest) request,
              (io.grpc.stub.StreamObserver<com.fededge.grpc.GetGlobalModelResponse>) responseObserver);
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
          getRegisterClientMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              com.fededge.grpc.RegisterClientRequest,
              com.fededge.grpc.RegisterClientResponse>(
                service, METHODID_REGISTER_CLIENT)))
        .addMethod(
          getGetTrainingJobMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              com.fededge.grpc.GetTrainingJobRequest,
              com.fededge.grpc.GetTrainingJobResponse>(
                service, METHODID_GET_TRAINING_JOB)))
        .addMethod(
          getSubmitUpdateMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              com.fededge.grpc.ModelUpdate,
              com.fededge.grpc.SubmitUpdateResponse>(
                service, METHODID_SUBMIT_UPDATE)))
        .addMethod(
          getHeartbeatMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              com.fededge.grpc.HeartbeatRequest,
              com.fededge.grpc.HeartbeatResponse>(
                service, METHODID_HEARTBEAT)))
        .addMethod(
          getGetGlobalModelMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              com.fededge.grpc.GetGlobalModelRequest,
              com.fededge.grpc.GetGlobalModelResponse>(
                service, METHODID_GET_GLOBAL_MODEL)))
        .build();
  }

  private static abstract class FederatedCoordinatorBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    FederatedCoordinatorBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return com.fededge.grpc.FedEdgeProto.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("FederatedCoordinator");
    }
  }

  private static final class FederatedCoordinatorFileDescriptorSupplier
      extends FederatedCoordinatorBaseDescriptorSupplier {
    FederatedCoordinatorFileDescriptorSupplier() {}
  }

  private static final class FederatedCoordinatorMethodDescriptorSupplier
      extends FederatedCoordinatorBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final java.lang.String methodName;

    FederatedCoordinatorMethodDescriptorSupplier(java.lang.String methodName) {
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
      synchronized (FederatedCoordinatorGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new FederatedCoordinatorFileDescriptorSupplier())
              .addMethod(getRegisterClientMethod())
              .addMethod(getGetTrainingJobMethod())
              .addMethod(getSubmitUpdateMethod())
              .addMethod(getHeartbeatMethod())
              .addMethod(getGetGlobalModelMethod())
              .build();
        }
      }
    }
    return result;
  }
}
