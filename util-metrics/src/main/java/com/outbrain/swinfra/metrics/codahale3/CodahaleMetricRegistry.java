package com.outbrain.swinfra.metrics.codahale3;

import com.codahale.metrics.Metric;
import com.outbrain.swinfra.metrics.api.MetricData;
import com.outbrain.swinfra.metrics.api.MetricRegistry;

import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import static java.util.Objects.requireNonNull;

public class CodahaleMetricRegistry implements MetricRegistry<Metric> {

  private static final String DELIMITER = "_";
  private final ConcurrentMap<String, MetricData<Metric>> metrics = new ConcurrentHashMap<>();

  /**
   * Adds a metric to this registry and returns it. If a metric was already added with the same labels then the
   * existing metric will be returned.
   */
  public Metric getOrAdd(final Metric metric, final Map<String, String> labels) {
    requireNonNull(metric, "metric cannot be null");
    requireNonNull(labels, "labels cannot be null");

    final String key = createKey(labels);
    metrics.putIfAbsent(key, new MetricData<>(metric, labels));
    return metrics.get(key).getMetric();
  }

  private String createKey(final Map<String, String> labels) {
    final StringBuilder sb = new StringBuilder();
    for (final Entry<String, String> entry : labels.entrySet()) {
      sb.append(entry.getKey()).append(DELIMITER).append(entry.getValue()).append(DELIMITER);
    }
    sb.deleteCharAt(sb.length() - 1);
    return sb.toString();
  }

  public Set<MetricData<Metric>> getAll() {
    return new HashSet<>(metrics.values());
  }

}
