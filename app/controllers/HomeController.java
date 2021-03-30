package controllers;

import java.util.List;

import javax.inject.Inject;

import com.google.gson.Gson;

import Service.ContribuidorService;
import model.Contribuidor;
import play.mvc.Controller;
import play.mvc.Result;

public class HomeController extends Controller {

	@Inject
	public ContribuidorService contribuidorService;
	
	
    public Result lista(String buscaOrganizacao) {
    	
    	List<Contribuidor> x = contribuidorService.buscarOrganizacao(buscaOrganizacao); 
    	
    	Gson gson = new Gson();
    	
    	return ok(gson.toJson(x));

    }

}
