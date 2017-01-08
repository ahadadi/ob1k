package com.outbrain.swinfra.metrics.api;

import java.util.Collections;
import java.util.Map;
import java.util.Objects;

import static java.util.Collections.unmodifiableMap;

public class MetricData<T> {

  private final T metric;
  private final Map<String, String> labels;

  public MetricData(final T metric, final Map<String, String> labels) {
    this.metric = metric;
    this.labels = labels;
  }

  public T getMetric() {
    return metric;
  }

  public Map<String, String> getLabels() {
    return unmodifiableMap(labels);
  }

  @Override
  public boolean equals(final Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    final MetricData that = (MetricData) o;
    return Objects.equals(labels, that.labels);
  }

  @Override
  public int hashCode() {
    return Objects.hash(labels);
  }

  @Override
  public String toString() {
    return "MetricData{" +
        "metric=" + metric +
        ", labels=" + labels +
        '}';
  }
}
