package lt.eif.viko.mjakovcenko.bankclient.bank.client;

import lt.eif.viko.mjakovcenko.bankclient.bank.wsdl.GetClientRequest;
import lt.eif.viko.mjakovcenko.bankclient.bank.wsdl.GetClientResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.stereotype.Component;
import org.springframework.ws.client.core.WebServiceTemplate;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
import org.springframework.ws.soap.client.core.SoapActionCallback;

@Component
public class BankClient extends WebServiceGatewaySupport {
    private static final Logger log = LoggerFactory.getLogger(BankClient.class);

    public GetClientResponse getClient(String name) {
        GetClientRequest request = new GetClientRequest();
        request.setName(name);


        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        marshaller.setContextPath("lt.eif.viko.mjakovcenko.bankclient.bank.wsdl");


        setMarshaller(marshaller);
        setUnmarshaller(marshaller);

        GetClientResponse response = (GetClientResponse) getWebServiceTemplate()
                .marshalSendAndReceive("http://localhost:7777/ws/client", request,
                        new SoapActionCallback(
                                "http://eif.viko.lt/mj/springsoap/gen/GetClientRequest"));

        return response;
    }


}
