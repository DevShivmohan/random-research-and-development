package com.example.test.rd;

import io.temporal.client.WorkflowClient;
import io.temporal.client.WorkflowClientOptions;
import io.temporal.serviceclient.SimpleSslContextBuilder;
import io.temporal.serviceclient.WorkflowServiceStubs;
import io.temporal.serviceclient.WorkflowServiceStubsOptions;
import io.temporal.worker.Worker;
import io.temporal.worker.WorkerFactory;

import javax.net.ssl.SSLException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

/**
 * Creating singleton class for All
 */
public class Beans {
    private static final String TASK_QUEUE = "TripBooking";
    private static Beans beans;
    public static Beans getInstance() throws FileNotFoundException, SSLException {
        if(beans==null)
            beans=new Beans();
        return beans;
    }

    public WorkflowServiceStubs getService() {
        return service;
    }

    public WorkflowClient getClient() {
        return client;
    }

    public WorkerFactory getFactory() {
        return factory;
    }

    public Worker getWorker() {
        return worker;
    }

    private WorkflowServiceStubs service;
    private WorkflowClient client;
    private WorkerFactory factory;
    private Worker worker;

    private Beans() throws FileNotFoundException, SSLException {
        InputStream clientCert = new FileInputStream("/home/shiv/Samta deck/libraries/demolibrary/idigi-test/src/main/resources/ca.pem");
        InputStream clientKey = new FileInputStream("/home/shiv/Samta deck/libraries/demolibrary/idigi-test/src/main/resources/ca_pcks8");

        String targetEndpoint = "idigicloud-test.mevw8.tmprl.cloud:7233";
        String namespace = "idigicloud-test.mevw8";

        service =
                WorkflowServiceStubs.newInstance(
                        WorkflowServiceStubsOptions.newBuilder()
                                .setTarget(targetEndpoint)
                                .setSslContext(SimpleSslContextBuilder.forPKCS8(clientCert, clientKey).build())
                                .build());

        // client that can be used to start and signal workflows
        client =
                WorkflowClient.newInstance(
                        service, WorkflowClientOptions.newBuilder().setNamespace(namespace).build());

        // worker factory that can be used to create workers for specific task queues
        factory = WorkerFactory.newInstance(client);

        // Worker that listens on a task queue and hosts both workflow and activity implementations.
        worker = factory.newWorker(TASK_QUEUE);
    }

}
