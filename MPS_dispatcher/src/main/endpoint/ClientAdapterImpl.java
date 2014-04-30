package main.endpoint;

import javax.jws.WebService;

@WebService(endpointInterface = "main.endpoint.IClientAdapter")
public class ClientAdapterImpl implements IClientAdapter {

	@Override
	public String saySomething(String s) {
		return "ECHO: " + s;
	}
}
