package com.example.test;

import com.example.test.rd.Beans;
import com.example.test.rd.TripBookingActivities;
import com.example.test.rd.TripBookingActivitiesImpl;
import com.example.test.rd.TripBookingWorkflow;
import io.temporal.client.WorkflowClient;
import io.temporal.client.WorkflowOptions;
import io.temporal.worker.Worker;
import io.temporal.worker.WorkerFactory;
import io.temporal.worker.WorkflowImplementationOptions;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;
import javax.net.ssl.SSLException;
import java.io.FileNotFoundException;

@SpringBootApplication
public class IdigiTestApplication {
	private static final String TASK_QUEUE = "TripBooking";

	public static void main(String[] args) {
		SpringApplication.run(IdigiTestApplication.class, args);
	}

	/**
	 * Uses of singleton beans
	 * @throws FileNotFoundException
	 * @throws SSLException
	 */
	@PostConstruct
	public void init() throws FileNotFoundException, SSLException {

		System.out.println("Starting init----------------");

		Beans beans=Beans.getInstance();

		Worker worker=beans.getWorker();
		// client that can be used to start and signal workflows
		WorkflowClient client = beans.getClient();

		// worker factory that can be used to create workers for specific task queues
		WorkerFactory factory = beans.getFactory();
		// Workflows are stateful. So you need a type to create instances.
        worker.registerWorkflowImplementationTypes(WorkflowImplementationOptions.newBuilder().build());
//		worker.registerWorkflowImplementationTypes(TripBookingWorkflow.class);
//
		// Activities are stateless and thread safe. So a shared instance is used.
		TripBookingActivities tripBookingActivities = new TripBookingActivitiesImpl();
		worker.registerActivitiesImplementations(tripBookingActivities);

		// Start all workers created by this factory.
		factory.start();
		System.err.println("Worker started for task queue: " + TASK_QUEUE);

		// now we can start running instances of our saga - its state will be persisted
		WorkflowOptions options = WorkflowOptions.newBuilder().setTaskQueue(TASK_QUEUE).build();
		TripBookingWorkflow trip1 = client.newWorkflowStub(TripBookingWorkflow.class, options);
		String workFlowResult = trip1.bookTrip("trip1");
		System.err.println("Result is " + workFlowResult);
	}

}
