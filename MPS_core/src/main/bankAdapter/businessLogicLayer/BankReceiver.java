package main.bankAdapter.businessLogicLayer;

import java.io.IOException;

import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConsumerCancelledException;
import com.rabbitmq.client.QueueingConsumer;
import com.rabbitmq.client.ShutdownSignalException;

public class BankReceiver extends Thread {
	private final String QUEUE_NAME = "bank.hapsaa";
	private final String RMQ_SERVER = "localhost";
	private BankAdapterBusinessLogic bankAdapterBusinessLogic;
	
	public BankReceiver(BankAdapterBusinessLogic bbl) {
		this.bankAdapterBusinessLogic = bbl;
	}
	
	public void run() {
		{
			System.out.println("Starting Bankadapter Receiver Thread");
			System.out.println("BankadapterReceiver Queue: " + QUEUE_NAME);
			System.out.println("BankadapterReceiver RMQ Host: " + RMQ_SERVER);
			
			try {
				ConnectionFactory factory = new ConnectionFactory();
				factory.setHost(RMQ_SERVER);
				Connection connection = factory.newConnection();
				Channel channel;

				channel = connection.createChannel();

				channel.queueDeclare(QUEUE_NAME, false, false, false, null);
				
				QueueingConsumer consumer = new QueueingConsumer(channel);
			    channel.basicConsume(QUEUE_NAME, true, consumer);

			    while (true) {
			      QueueingConsumer.Delivery delivery = consumer.nextDelivery();
			      String message = new String(delivery.getBody());
			      System.out.println("BankAdapter Received '" + message + "'");
			      bankAdapterBusinessLogic.verarbeiteZahlungseingang(message);
			    }
			    
			} catch (IOException | ShutdownSignalException | ConsumerCancelledException | InterruptedException e) {
				System.out.println("RMQ connection failed:");
				e.printStackTrace();
			}
		}
	}
}
