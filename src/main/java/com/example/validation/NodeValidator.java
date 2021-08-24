package com.example.validation;

import com.beust.jcommander.IValueValidator;
import com.beust.jcommander.ParameterException;
import com.google.common.collect.Lists;
import java.util.List;

public class NodeValidator implements IValueValidator<String> {
  public void validate(String name, String value) throws ParameterException {
    List<String> choices = Lists.newArrayList("A", "B", "C", "D", "E");
    if (!choices.contains(value)) {
      throw new ParameterException(
          "Node " + name + " should be from " + choices + " (found " + value + ")");
    }
  }
}
