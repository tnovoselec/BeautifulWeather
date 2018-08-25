package com.tnovoselec.beautifulweather.ui.util;

import android.text.TextUtils;

public class FormatUtils {

  public static String formatDescription(String description) {
    StringBuilder stringBuilder = new StringBuilder();
    for (String part : description.split(" ")) {
      stringBuilder.append(capitalize(part));
      stringBuilder.append(" ");
    }
    return stringBuilder.toString();
  }

  public static String capitalize(String input) {
    if (TextUtils.isEmpty(input)) {
      return input;
    }
    return input.substring(0, 1).toUpperCase() + input.substring(1);
  }
}
