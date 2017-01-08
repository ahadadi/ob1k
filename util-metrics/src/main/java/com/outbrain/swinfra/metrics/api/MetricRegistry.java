package com.outbrain.swinfra.metrics.api;

import java.util.Map;
import java.util.Set;

/**
 * The metrics registry. This class hold all logic related to storing metrics and looking up existing ones.
 */
public interface MetricRegistry<T> {

  /**
   * Adds a metric to this registry. If a metric already exists with the same labels then the existing metric
   * will be returned
   */
   T getOrAdd(final T metric, final Map<String, String> labels);

  /**
   * Returns a set of all collected metrics
   */
  Set<MetricData<T>> getAll();

}
