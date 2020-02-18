package com.smoothstack.borrower.controller;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.smoothstack.borrower.domain.CheckOutDetails;
import com.smoothstack.borrower.services.BorrowerServices;

@Component
@Path("/member")
public class BorrowerController {

	private BorrowerServices borrowerService;

	public BorrowerController(BorrowerServices borrowerService) {
		this.borrowerService = borrowerService;

	}

	@POST
	@Path("/checkout")
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })

	public Response checkOutBook(@QueryParam("branchId") int branchId, @QueryParam("bookId") int bookId,
			@QueryParam("cardNo") int cardNo) {

		String details;

		try {

			details = borrowerService.checkOutBook(branchId, bookId, cardNo);

			return Response.ok(details).build();

		} catch (Exception e) {
			String resp = "Failed to check out a book; " + e.getMessage();
			return Response.status(Response.Status.BAD_REQUEST).entity(resp).build();
		}

	}

	@POST
	@Path("/checkin")
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Response checkInBook(@QueryParam("bookId") int bookId, @QueryParam("cardNo") int cardNo) {

		String resp = "";
		try {
			boolean status = borrowerService.checkInBook(bookId, cardNo);
			if (status) {
				resp = "Book has been checked in successfully.";
				return Response.ok(resp).build();
			} else {
				resp = "Failed to check in, please try again.";
				return Response.status(Response.Status.NOT_MODIFIED).entity(resp).build();
			}

		} catch (Exception e) {
			resp = "Failed to check in the book.";
			return Response.status(Response.Status.BAD_REQUEST).entity(resp).build();
		}

	}
}
