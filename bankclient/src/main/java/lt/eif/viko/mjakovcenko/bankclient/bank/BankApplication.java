package lt.eif.viko.mjakovcenko.bankclient.bank;

import lt.eif.viko.mjakovcenko.bankclient.bank.client.BankClient;
import lt.eif.viko.mjakovcenko.bankclient.bank.wsdl.GetClientResponse;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@ServletComponentScan
public class BankApplication {

	public static void main(String[] args) {
		SpringApplication.run(BankApplication.class, args);
	}


}
