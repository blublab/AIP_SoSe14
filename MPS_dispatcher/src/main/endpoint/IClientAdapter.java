package main.endpoint;

import javax.jws.WebMethod;
import javax.jws.WebService;

@WebService
public interface IClientAdapter {

	@WebMethod
	public String saySomething(String s);
}
