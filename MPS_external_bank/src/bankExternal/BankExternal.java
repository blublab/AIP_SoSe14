package bankExternal;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.json.simple.JSONObject;

import com.rabbitmq.client.*;

public class BankExternal {

	private static final String configFile = "bankexternal.cfg";
	private static Properties prop = new Properties();	
	private static String queueName = null;
	private static String hostName = null;
	
	public static void main(String[] args) {
		
		//Overriding args
//		args = new String[]{"dummy", "-b", "1400", "-r", "1"};
		
		setupProperties();
		
		try {
			Options options = new Options();
			options.addOption("r", true, "Rechnungsnummer");
			options.addOption("b", true, "Betrag");
			CommandLineParser parser = new org.apache.commons.cli.GnuParser();
			CommandLine cmd = parser.parse(options, args);
			if(cmd.getOptionValue("r") == null || cmd.getOptionValue("b") == null) {
				HelpFormatter formatter = new HelpFormatter();
				formatter.printHelp( "bankexternal", options );
				System.exit(1);
			}
			
			System.out.println("RMQ Host: " + hostName);	
			System.out.println("Queue Name: " + queueName);
			
			// Get option values
			Integer rechnungsnummer = Integer.parseInt(cmd.getOptionValue("r"));
			Integer betrag = Integer.parseInt(cmd.getOptionValue("b"));
			
			// Assemble message
			JSONObject message = new JSONObject();
			message.put("rechnungsNr", rechnungsnummer);
			message.put("betrag", betrag);
			
			// Establish connection
			ConnectionFactory factory = new ConnectionFactory();
			factory.setHost(hostName);
			Connection con = factory.newConnection();
			Channel chan = con.createChannel();
			chan.queueDeclare(queueName, false, false, false, null);
			
			// Send message
			System.out.println("Sending message: " + message.toJSONString());
			chan.basicPublish("", queueName, null, message.toJSONString().getBytes());
			System.out.println("done.");
			
			chan.close();
			con.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
		}

	}
	
	private static void setupProperties() {
		File file = new File(configFile);
		if(!file.exists() || file.isDirectory()) {
			generateDefaultProperties();
		}
		InputStream ips;
		try {
			ips = new FileInputStream(file);
			
			prop.load(ips);
			
			hostName = prop.getProperty("HostName");
			queueName = prop.getProperty("QueueName");
						
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private static void generateDefaultProperties() {
			try {
				OutputStream ops = new FileOutputStream(configFile);

				prop.setProperty("QueueName", "bank.hapsaa");
				prop.setProperty("HostName", "localhost");
			
				prop.store(ops, null);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
}
