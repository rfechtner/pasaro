package runner;

import java.io.IOException;
import java.util.Locale;

import validation.Validate;

public class Validator {
	public static void main(String[] args) throws IOException {
		Locale.setDefault(new Locale("US"));
		Validate.validateFile(args[1]);
	}
}
