///*
// *  Copyright (c) 2020 Temporal Technologies, Inc. All Rights Reserved
// *
// *  Copyright 2012-2016 Amazon.com, Inc. or its affiliates. All Rights Reserved.
// *
// *  Modifications copyright (C) 2017 Uber Technologies, Inc.
// *
// *  Licensed under the Apache License, Version 2.0 (the "License"). You may not
// *  use this file except in compliance with the License. A copy of the License is
// *  located at
// *
// *  http://aws.amazon.com/apache2.0
// *
// *  or in the "license" file accompanying this file. This file is distributed on
// *  an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
// *  express or implied. See the License for the specific language governing
// *  permissions and limitations under the License.
// */
//
//package com.example.test.rd;
//
//import io.temporal.activity.ActivityOptions;
//import io.temporal.common.RetryOptions;
//import io.temporal.failure.ActivityFailure;
//import io.temporal.workflow.Saga;
//import io.temporal.workflow.Workflow;
//
//import java.time.Duration;
//
//public class TripBookingWorkflowImpl implements TripBookingWorkflow {
//
//  private final ActivityOptions options =
//      ActivityOptions.newBuilder()
//          .setStartToCloseTimeout(Duration.ofHours(1))
//          // disable retries for example to run faster
//          .setRetryOptions(RetryOptions.newBuilder().setMaximumAttempts(1).build())
//          .build();
//  private final TripBookingActivities activities =
//      Workflow.newActivityStub(TripBookingActivities.class, options);
//
//  @Override
//  public String bookTrip(String name) {
//    // Configure SAGA to run compensation activities in parallel
//    Saga.Options sagaOptions = new Saga.Options.Builder().setParallelCompensation(true).build();
//    Saga saga = new Saga(sagaOptions);
//    try {
//      // Car 2 String
//      String carReservationID = activities.reserveCar(name); // MS1
//      saga.addCompensation(activities::cancelCar, carReservationID, name);
//
//      // Hotel 3 String
//      String hotelReservationID = activities.bookHotel(name); // MS2
//      saga.addCompensation(activities::cancelHotel, hotelReservationID, name);
//
//      // Flight 1 String
//      String flightReservationID = activities.bookFlight(name); // MS3
//      saga.addCompensation(activities::cancelFlight, flightReservationID, name);
//
//       System.err.println("Workflow Run ID " + Workflow.getInfo().getRunId());
//      return "success";
//    } catch (ActivityFailure e) {
//      // saga.compensate();
//      throw e;
//    }
//  }
//}
