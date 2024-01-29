package enviando.email;

/**
 * Unit test for simple App.
 */
public class AppTest {

	@org.junit.Test
	public void testeEmail() throws Exception {

		StringBuilder stringBuilderTextoEmail = new StringBuilder();
		stringBuilderTextoEmail.append("Testando envio com HTML.<br><br>");
		stringBuilderTextoEmail.append("<a target=\"_blank\" href=\"https://www.youtube.com\">Acessar Site</a>");

		ObjetoEnviaEmail enviaEmail = new ObjetoEnviaEmail("cristianopxt@hotmail.com", "Cristiano Pxt Dev",
				"Teste de email com Java", stringBuilderTextoEmail.toString());

		enviaEmail.enviarEmailAnexo(true);
	}
}
