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

import java.util.UUID;

public class TripBookingActivitiesImpl implements TripBookingActivities {
  @Override
  public String reserveCar(String name) {
     System.err.println("reserve car for '" + name + "'");
    return UUID.randomUUID().toString();
  }

  @Override
  public String bookFlight(String name) {
     System.err.println("Exception in book flight for '" + name + "'");
    // throw new RuntimeException("Flight booking did not work");
    return UUID.randomUUID().toString();
  }

  @Override
  public String bookHotel(String name) {
     System.err.println("booking hotel for '" + name + "'");
    return UUID.randomUUID().toString();
  }

  @Override
  public String cancelFlight(String reservationID, String name) {
     System.err.println("cancelling flight reservation '" + reservationID + "' for '" + name + "'");
    return UUID.randomUUID().toString();
  }

  @Override
  public String cancelHotel(String reservationID, String name) {
     System.err.println("cancelling hotel reservation '" + reservationID + "' for '" + name + "'");
    return UUID.randomUUID().toString();
  }

  @Override
  public String cancelCar(String reservationID, String name) {
     System.err.println("cancelling car reservation '" + reservationID + "' for '" + name + "'");
    return UUID.randomUUID().toString();
  }
}
