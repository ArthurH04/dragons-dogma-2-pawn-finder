package com.dd2pawn.pawnapi.exception;

public class DuplicateEntryException extends RuntimeException {

	private final String field;

	public DuplicateEntryException(String field, String message) {
		super(message);
		this.field = field;
	}

	public String getField() {
		return field;
	}
}
