package main.endpoint;

import javax.jws.WebService;

@WebService(endpointInterface = "main.endpoint.IClientAdapter")
public class ClientAdapterImpl implements IClientAdapter {

	@Override
	public String saySomething(String s) {
		System.out.println("aargh");
		return "ECHO: " + s;
	}
}
