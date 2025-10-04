package com.dd2pawn.pawnapi.dto;

import java.util.List;

public record ResponseError(int status, String message, List<CustomFieldError> fieldErrors) {
	
	public static ResponseError conflict(String message) {
		return new ResponseError(409, message, List.of());
	}
	
	public static ResponseError defaultResponse(String message) {
		return new ResponseError(400, message, List.of());
	}
}
