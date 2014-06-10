
package main.endpoint;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the main.endpoint package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _SaySomething_QNAME = new QName("http://endpoint.main/", "saySomething");
    private final static QName _SaySomethingResponse_QNAME = new QName("http://endpoint.main/", "saySomethingResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: main.endpoint
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link SaySomething }
     * 
     */
    public SaySomething createSaySomething() {
        return new SaySomething();
    }

    /**
     * Create an instance of {@link SaySomethingResponse }
     * 
     */
    public SaySomethingResponse createSaySomethingResponse() {
        return new SaySomethingResponse();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SaySomething }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://endpoint.main/", name = "saySomething")
    public JAXBElement<SaySomething> createSaySomething(SaySomething value) {
        return new JAXBElement<SaySomething>(_SaySomething_QNAME, SaySomething.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SaySomethingResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://endpoint.main/", name = "saySomethingResponse")
    public JAXBElement<SaySomethingResponse> createSaySomethingResponse(SaySomethingResponse value) {
        return new JAXBElement<SaySomethingResponse>(_SaySomethingResponse_QNAME, SaySomethingResponse.class, null, value);
    }

}
