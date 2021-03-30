package Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import controllers.Constante;
import model.Contribuidor;

public class Conecta {

	@Inject
	private BuscaRepositorios buscaRepositorios;

	private static int HTTP_COD_SUCESSO = 200;

	public List<Contribuidor> api(String buscaOrganizacao) {
	
		HttpURLConnection con = null;

		// busca por repositorios
		List<String> repositorios = buscaRepositorios.apiRepositorio(buscaOrganizacao);
		
		for (String string : repositorios) {
			System.out.println(string);
			
		}
		
		List<Contribuidor> listaContribuidor = new ArrayList<Contribuidor>();


		for (String repo : repositorios) {

			System.out.println(repo);

			String urlRepo = "https://api.github.com/repos/" + repo + "/contributors"+ "?access_token=" + Constante.GH_TOKEN;
			
			System.out.println(urlRepo);

			try {
				URL url = new URL(urlRepo);
				con = (HttpURLConnection) url.openConnection();

				if (con.getResponseCode() != HTTP_COD_SUCESSO) {

					throw new RuntimeException("HTTP error code : " + con.getResponseCode());

				} else {

					StringBuffer resp = new StringBuffer();
					InputStream resp2 = con.getInputStream();
					BufferedReader reader = new BufferedReader(new InputStreamReader(resp2));

					String linha = reader.toString();

					while ((linha = reader.readLine()) != null) {

						resp.append(linha);

					}

					String[] sep = resp.toString().split(",");
					String name = "\"login\":";
					String contributions = "\"contributions\":";

					Contribuidor c = new Contribuidor();
					boolean nameB = false;
					boolean contribuidorB = false;

					for (String st : sep) {

						String limpaName = st.toString().replace("[", "").replace("{", "").replace(name, "")
								.replace("\"", "");
						String limpaContribuidor = st.toString().replace("]", "").replace("}", "")
								.replace(contributions, "").replace("\"", "");

						if (st.contains(name)) {
							System.out.println(limpaName);
							c.setName(limpaName);
							nameB = true;
						}

						if (st.contains(contributions)) {
							System.out.println(limpaContribuidor);
							c.setContributions(limpaContribuidor);
							contribuidorB = true;
						}

						if (!st.contains(name) && !st.contains(contributions)) {
							System.out.println("passou aqui");
						} else {
							System.out.println("passou LA");
						}

						if (nameB == true && contribuidorB == true) {

							listaContribuidor.add(c);
							c = new Contribuidor();
							nameB = false;
							contribuidorB = false;

						}

					}

					con.disconnect();

				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				con.disconnect();
				e.printStackTrace();
			}
			con.disconnect();

		}
		return listaContribuidor;
	}
}