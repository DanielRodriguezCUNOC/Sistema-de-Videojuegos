package com.api_videojuego.resources.endpoints.gamer.grupo;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/grupo")
public class GrupoResource {

	@Path("/registrar-grupo")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response registrarGrupo() {
		try {

		} catch (Exception e) {
			// TODO: handle exception
		}
		return null;
	}
}
