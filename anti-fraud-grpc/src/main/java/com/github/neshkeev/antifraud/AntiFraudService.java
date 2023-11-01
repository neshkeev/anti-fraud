package com.github.neshkeev.antifraud;

import com.githib.neshkeev.antifraud.workflow.BlackListDecideWorkflow;
import com.githib.neshkeev.antifraud.workflow.Request;
import io.grpc.stub.StreamObserver;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.stereotype.Service;

@Service
public class AntiFraudService extends AntiFraudGrpc.AntiFraudImplBase {
    private final ObjectFactory<BlackListDecideWorkflow> workflow;

    public AntiFraudService(ObjectFactory<BlackListDecideWorkflow> workflow) {
        this.workflow = workflow;
    }

    @Override
    public void decide(CheckRequest request, StreamObserver<CheckResponse> responseObserver) {
        CheckResponse result = checkRequest(request);
        responseObserver.onNext(result);
        responseObserver.onCompleted();
    }

    private CheckResponse checkRequest(CheckRequest request) {
        final var r = new Request(request.getFrom(),
                request.getTo(),
                Integer.toString(request.getAmount())
        );
        final var voter = workflow.getObject();
        return CheckResponse
                .newBuilder()
                .setResult(voter.decide(r))
                .build();
    }
}
