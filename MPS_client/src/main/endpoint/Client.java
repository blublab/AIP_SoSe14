package main.endpoint;

public class Client {

	public static void main (String[] args) {
		ClientAdapterImplService clientAdapterService = new ClientAdapterImplService();
		ClientAdapterImpl clientAdapter = clientAdapterService.getClientAdapterImplPort();
		
		System.out.println("------->>  Call Started");
		System.out.println(clientAdapter.saySomething("HALLO????"));
		System.out.println("------->>  Call Ended");
	}
}
