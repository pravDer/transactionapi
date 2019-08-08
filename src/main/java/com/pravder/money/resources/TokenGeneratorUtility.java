package com.pravder.money.resources;

import com.pravder.money.api.Request;
import org.joda.time.DateTime;

/**
 * Utility class to generate a unique request id for a given transaction request.
 */

final class TokenGeneratorUtility {

  /**
   * Provides a unique token for a request.
   *
   * @param request request object for which request is made
   * @return unique token
   */
  public static String getTokenForRequest(Request request) {
    String token = "";
    Double random = Math.random();
    DateTime dateTime = DateTime.now();
    token = token.concat(random.toString())
        .concat(String.valueOf(request.hashCode()))
        .concat(dateTime.toString());
    return token;
  }
}
