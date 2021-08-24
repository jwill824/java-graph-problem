package com.example.validation;

import com.beust.jcommander.IValueValidator;
import com.beust.jcommander.ParameterException;
import com.google.common.collect.Lists;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PathValidator implements IValueValidator<String> {
  public void validate(String name, String value) throws ParameterException {
    String regex = "[^,]+";
    Pattern pattern = Pattern.compile(regex);
    Matcher matcher = pattern.matcher(value);
    List<String> choices = Lists.newArrayList("A", "B", "C", "D", "E");
    if (!matcher.find() || !choices.containsAll(Arrays.asList(value.split(",")))) {
      throw new ParameterException(
          "Path "
              + name
              + " should be in "
              + regex
              + " format and from "
              + choices
              + " (found "
              + value
              + ")");
    }
  }
}
