package com.outbrain.swinfra.metrics;

import com.codahale.metrics.MetricRegistry;
import com.outbrain.swinfra.metrics.api.MetricFactory;
import com.outbrain.swinfra.metrics.codahale3.CodahaleMetricRegistry;
import com.outbrain.swinfra.metrics.codahale3.CodahaleMetricsFactory;

public class MetricsContext {
  public static final MetricFactory DEFAULT_METRIC_FACTORY = new CodahaleMetricsFactory(new MetricRegistry());
  public static final CodahaleMetricRegistry DEFAULT_METRIC_REGISTRY = new CodahaleMetricRegistry();
}
