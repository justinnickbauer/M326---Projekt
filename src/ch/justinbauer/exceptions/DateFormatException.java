package ch.justinbauer.exceptions;

public class DateFormatException extends IllegalArgumentException {

	private static final long serialVersionUID = -323912157824179477L;

	public DateFormatException(String message) {
		super(message);
	}
}