/*
 *  Copyright (c) 2020 Temporal Technologies, Inc. All Rights Reserved
 *
 *  Copyright 2012-2016 Amazon.com, Inc. or its affiliates. All Rights Reserved.
 *
 *  Modifications copyright (C) 2017 Uber Technologies, Inc.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License"). You may not
 *  use this file except in compliance with the License. A copy of the License is
 *  located at
 *
 *  http://aws.amazon.com/apache2.0
 *
 *  or in the "license" file accompanying this file. This file is distributed on
 *  an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 *  express or implied. See the License for the specific language governing
 *  permissions and limitations under the License.
 */

package com.example.test.rd;

import io.temporal.client.WorkflowClient;
import io.temporal.client.WorkflowClientOptions;
import io.temporal.client.WorkflowException;
import io.temporal.client.WorkflowOptions;
import io.temporal.serviceclient.SimpleSslContextBuilder;
import io.temporal.serviceclient.WorkflowServiceStubs;
import io.temporal.serviceclient.WorkflowServiceStubsOptions;
import io.temporal.worker.Worker;
import io.temporal.worker.WorkerFactory;
import io.temporal.worker.WorkflowImplementationOptions;

import javax.net.ssl.SSLException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class TripBookingSagaCloud {

  static final String TASK_QUEUE = "TripBooking";

  @SuppressWarnings("CatchAndPrintStackTrace")
  public static void temporalWorkflow() throws FileNotFoundException, SSLException {
    System.out.println("Starting-----------------------");
    // gRPC stubs wrapper that talks to the local docker instance of temporal service.
//    InputStream clientCert = new FileInputStream("C:\\Users\\Neosoft\\OneDrive\\Documents\\Temporal\\20230209\\20230209\\ca.pem");
    // PKCS8 client key, which should look like:
    // -----BEGIN PRIVATE KEY-----
    // ...
    // -----END PRIVATE KEY-----
    InputStream clientCert = new FileInputStream("/home/shiv/Samta deck/libraries/demolibrary/idigi-test/src/main/resources/ca.pem");
    InputStream clientKey = new FileInputStream("/home/shiv/Samta deck/libraries/demolibrary/idigi-test/src/main/resources/ca_pcks8");

    String targetEndpoint = "idigicloud-test.mevw8.tmprl.cloud:7233";
    String namespace = "idigicloud-test.mevw8";

    WorkflowServiceStubs service =
        WorkflowServiceStubs.newInstance(
            WorkflowServiceStubsOptions.newBuilder()
                .setTarget(targetEndpoint)
                .setSslContext(SimpleSslContextBuilder.forPKCS8(clientCert, clientKey).build())
                .build());

    // client that can be used to start and signal workflows
    WorkflowClient client =
        WorkflowClient.newInstance(
            service, WorkflowClientOptions.newBuilder().setNamespace(namespace).build());

    // worker factory that can be used to create workers for specific task queues
    WorkerFactory factory = WorkerFactory.newInstance(client);

    // Worker that listens on a task queue and hosts both workflow and activity implementations.
    Worker worker = factory.newWorker(TASK_QUEUE);

    // Workflows are stateful. So you need a type to create instances.
    worker.registerWorkflowImplementationTypes(WorkflowImplementationOptions.newBuilder().build());

    // Activities are stateless and thread safe. So a shared instance is used.
    TripBookingActivities tripBookingActivities = new TripBookingActivitiesImpl();
    worker.registerActivitiesImplementations(tripBookingActivities);

    // Start all workers created by this factory.
    factory.start();
     System.err.println("Worker started for task queue: " + TASK_QUEUE);

    // now we can start running instances of our saga - its state will be persisted
    WorkflowOptions options = WorkflowOptions.newBuilder().setTaskQueue(TASK_QUEUE).build();
    TripBookingWorkflow trip1 = client.newWorkflowStub(TripBookingWorkflow.class, options);
    String workFlowResult;
    try {
      workFlowResult = trip1.bookTrip("trip1");
       System.err.println("Result is " + workFlowResult);
    } catch (WorkflowException e) {
      e.printStackTrace();
      // Expected
    }

    /*    try {
      TripBookingWorkflow trip2 = client.newWorkflowStub(TripBookingWorkflow.class, options);
      workFlowResult = trip2.bookTrip("trip2");
       System.err.println("&&&& Result is " + workFlowResult);
    } catch (WorkflowException e) {
      // Expected
    }*/

//    System.exit(0);
  }
}
