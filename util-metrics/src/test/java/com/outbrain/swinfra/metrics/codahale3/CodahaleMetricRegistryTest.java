package com.outbrain.swinfra.metrics.codahale3;

import com.codahale.metrics.Metric;
import com.outbrain.swinfra.metrics.api.MetricData;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class CodahaleMetricRegistryTest {

  @Rule
  public final ExpectedException expectedException = ExpectedException.none();

  @Test
  public void testAddNewMetric() {
    final CodahaleMetricRegistry registry = new CodahaleMetricRegistry();
    final Metric expectedMetric = createCounter();
    final Metric actualMetric = registry.getOrAdd(expectedMetric, createRandomLabels());
    assertEquals(expectedMetric, actualMetric);
  }

  @Test
  public void testAddExistingMetric() {
    final CodahaleMetricRegistry registry = new CodahaleMetricRegistry();
    final Metric expectedMetric = createCounter();
    final Map<String, String> labels = createRandomLabels();
    registry.getOrAdd(expectedMetric, labels);
    final Metric actualMetric = registry.getOrAdd(createCounter(), labels);
    assertEquals(expectedMetric, actualMetric);
  }

  @Test
  public void testNullMetric() {
    expectedException.expect(NullPointerException.class);

    final CodahaleMetricRegistry registry = new CodahaleMetricRegistry();
    registry.getOrAdd(null, createRandomLabels());
  }

  @Test
  public void testNullLabels() {
    expectedException.expect(NullPointerException.class);

    final CodahaleMetricRegistry registry = new CodahaleMetricRegistry();
    registry.getOrAdd(createCounter(), null);
  }

  @Test
  public void testGetAllMetrics() {
    final CodahaleMetricRegistry registry = new CodahaleMetricRegistry();

    final Metric metric1 = createCounter();
    final Map<String, String> labels1 = createRandomLabels();
    final Metric metric2 = createCounter();
    final Map<String, String> labels2 = createRandomLabels();

    registry.getOrAdd(metric1, labels1);
    registry.getOrAdd(metric2, labels2);
    final Set<MetricData<Metric>> metrics = registry.getAll();
    assertEquals(2, metrics.size());
    assertTrue(metrics.contains(new MetricData<>(metric1, labels1)));
    assertTrue(metrics.contains(new MetricData<>(metric2, labels2)));
  }

  @Test
  public void testGetAllMetricsWithSameLabelsAddedTwice() {
    final CodahaleMetricRegistry registry = new CodahaleMetricRegistry();

    final Metric metric1 = createCounter();
    final Map<String, String> labels1 = createRandomLabels();
    final Metric metric2 = createCounter();
    final Map<String, String> labels2 = new HashMap<>(labels1);

    registry.getOrAdd(metric1, labels1);
    registry.getOrAdd(metric2, labels2);
    final Set<MetricData<Metric>> metrics = registry.getAll();
    assertEquals(1, metrics.size());
    assertTrue(metrics.contains(new MetricData<>(metric1, labels1)));
    assertTrue(metrics.contains(new MetricData<>(metric1, labels2)));
  }

  private Metric createCounter() {
    return new com.codahale.metrics.Counter();
  }

  private Map<String, String> createRandomLabels() {
    final Map<String, String> result = new HashMap<>();
    result.put(UUID.randomUUID().toString(), UUID.randomUUID().toString());
    result.put(UUID.randomUUID().toString(), UUID.randomUUID().toString());
    return result;
  }

}
