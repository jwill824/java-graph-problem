package com.example.validation;

import com.beust.jcommander.IValueValidator;
import com.beust.jcommander.ParameterException;
import com.google.common.collect.Lists;
import java.util.List;

public class ComparatorValidator implements IValueValidator<Integer> {
  public void validate(String name, Integer value) throws ParameterException {
    List<Integer> choices = Lists.newArrayList(0, 1, 2);
    if (!choices.contains(value)) {
      throw new ParameterException(
          "Comparator " + name + " should be from " + choices + " (found " + value + ")");
    }
  }
}
