package com.outbrain.swinfra.metrics.api;

/**
 * A metric which calculates the distribution of a value.
 *
 * @author Eran Harel
 */
public interface Histogram {

  /**
   * Adds a recorded value.
   *
   * @param value the length of the value
   */
  void update(int value);

  /**
   * Adds a recorded value.
   *
   * @param value the length of the value
   */
  void update(long value);

}
