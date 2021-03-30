package Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import controllers.Constante;

public class BuscaRepositorios {

	private static int HTTP_COD_SUCESSO = 200;

	public List<String> apiRepositorio(String buscaOrganizacao) {

		HttpURLConnection con = null;

		String urlx = "https://api.github.com/orgs/" + buscaOrganizacao + "/repos" + "?access_token=" + Constante.GH_TOKEN;
		
		//busca por repositorios
		
		List<String> listaContribuidor = new ArrayList<>();
		
		try {

			URL url = new URL(urlx);
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
				String full_name = "\"full_name\":";
				
				for (String st : sep) {

					String limpa = st.toString().replace("[", "").replace("{", "").replace(full_name, "").replace("\"", "");
					
					if (st.contains(full_name)) {
						listaContribuidor.add(limpa);
					} 
					
				}

				con.disconnect();
				return listaContribuidor;
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			con.disconnect();
			e.printStackTrace();
		}
		con.disconnect();

		return listaContribuidor;

	}
}