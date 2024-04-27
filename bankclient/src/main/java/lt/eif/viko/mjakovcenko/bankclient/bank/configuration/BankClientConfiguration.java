package lt.eif.viko.mjakovcenko.bankclient.bank.configuration;
import lt.eif.viko.mjakovcenko.bankclient.bank.client.BankClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

import javax.xml.transform.stream.StreamResult;
import java.io.StringWriter;

@Configuration
public class BankClientConfiguration {


   @Bean
    public Jaxb2Marshaller marshaller() {
        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        // this package must match the package in the <generatePackage> specified in
        // pom.xml
        marshaller.setContextPath("lt.eif.viko.mjakovcenko.bankclient.bank.wsdl");

        return marshaller;
    }

    @Bean
    public BankClient bankClient(Jaxb2Marshaller marshaller) {
        BankClient client = new BankClient();
        client.setDefaultUri("http://localhost:7777/ws");
        client.setMarshaller(marshaller);
        client.setUnmarshaller(marshaller);
        return client;
    }

}
