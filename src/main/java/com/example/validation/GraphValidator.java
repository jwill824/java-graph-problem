package com.example.validation;

import com.beust.jcommander.IValueValidator;
import com.beust.jcommander.ParameterException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GraphValidator implements IValueValidator<String> {
  public void validate(String name, String value) throws ParameterException {
    String regex = "^(?:[A-Z]{2}[0-9],)*[A-Z]{2}[0-9]$";
    Pattern pattern = Pattern.compile(regex);
    Matcher matcher = pattern.matcher(value);
    if (!matcher.find()) {
      throw new ParameterException(
          "Graph " + name + " should be in " + regex + " format" + " (found " + value + ")");
    }
  }
}
