package com.outbrain.swinfra.metrics.codahale3;

import com.codahale.metrics.Metric;
import com.codahale.metrics.MetricRegistry;
import com.outbrain.swinfra.metrics.api.Counter;
import com.outbrain.swinfra.metrics.api.Gauge;
import com.outbrain.swinfra.metrics.api.Histogram;
import com.outbrain.swinfra.metrics.api.Meter;
import com.outbrain.swinfra.metrics.api.MetricFactory;
import com.outbrain.swinfra.metrics.api.Timer;

import java.util.HashMap;
import java.util.Map;

import static com.outbrain.swinfra.metrics.MetricsContext.DEFAULT_METRIC_REGISTRY;

public class CodahaleMetricsFactory implements MetricFactory {

  private final MetricRegistry registry;

  @Deprecated
  public CodahaleMetricsFactory(final MetricRegistry registry) {
    this.registry = registry;
  }

  @Override
  public Timer createTimer(final String component, final String methodName) {
    final com.codahale.metrics.Timer timer = registry.timer(MetricRegistry.name(component, methodName));
    return new com.outbrain.swinfra.metrics.codahale3.Timer(getOrAdd(component, methodName, timer));
  }

  @Override
  public Counter createCounter(final String component, final String methodName) {
    final com.codahale.metrics.Counter counter = registry.counter(MetricRegistry.name(component, methodName));
    return new com.outbrain.swinfra.metrics.codahale3.Counter(getOrAdd(component, methodName, counter));
  }

  @Override
  public <T> Gauge<T> registerGauge(final String component, final String methodName, final Gauge<T> gauge) {
    final com.codahale.metrics.Gauge<T> codahaleGauge = new com.codahale.metrics.Gauge<T>() {
      @Override
      public T getValue() {
        return gauge.getValue();
      }
    };
    registry.register(MetricRegistry.name(component, methodName), codahaleGauge);
    getOrAdd(component, methodName, codahaleGauge);
    return gauge;
  }

  @Override
  public Meter createMeter(final String component, final String methodName, final String eventType) {
    final com.codahale.metrics.Meter meter = registry.meter(MetricRegistry.name(component, methodName, eventType));
    return new com.outbrain.swinfra.metrics.codahale3.Meter(getOrAdd(component, methodName, meter));
  }

  @Override
  public Histogram createHistogram(final String component, final String methodName, final boolean biased) {
    final com.codahale.metrics.Histogram histogram = registry.histogram(MetricRegistry.name(component, methodName));
    return new com.outbrain.swinfra.metrics.codahale3.Histogram(getOrAdd(component, methodName, histogram));
  }

  private Map<String, String> toLabels(final String component, final String methodName) {
    final Map<String, String> labels = new HashMap<>();
    labels.put("component", component);
    labels.put("methodName", methodName);
    return labels;
  }

  @SuppressWarnings("unchecked")
  private <S extends Metric> S getOrAdd(final String component, final String methodName, final S metric) {
    return (S) DEFAULT_METRIC_REGISTRY.getOrAdd(metric, toLabels(component, methodName));
  }
}
