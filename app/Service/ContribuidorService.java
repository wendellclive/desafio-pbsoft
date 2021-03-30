package Service;

import java.util.List;

import javax.inject.Inject;

import controllers.Constante;
import model.Contribuidor;


public class ContribuidorService {

	@Inject
	private Conecta conecta;

	public List<Contribuidor> buscarOrganizacao(String buscaOrganizacao) {

//		String url = "https://api.github.com/repos/octocat/Hello-World/contributors"; 
//		+ "?access_token=" + Constante.GH_TOKEN;
		
		return conecta.api(buscaOrganizacao);

	}
}
