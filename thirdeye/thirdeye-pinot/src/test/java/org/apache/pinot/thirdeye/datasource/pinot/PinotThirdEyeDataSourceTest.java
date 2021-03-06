/**
 * Copyright (C) 2014-2018 LinkedIn Corp. (pinot-core@linkedin.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.pinot.thirdeye.datasource.pinot;

import org.apache.pinot.thirdeye.constant.MetricAggFunction;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import org.testng.Assert;
import org.testng.annotations.Test;

public class PinotThirdEyeDataSourceTest {

  @Test
  public void testInitializeCacheLoaderFromGivenClass() throws Exception {
    Map<String, Object> properties = new HashMap<>();
    properties.put(PinotThirdEyeDataSource.CACHE_LOADER_CLASS_NAME_STRING,
        PinotControllerResponseCacheLoader.class.getName());

    PinotResponseCacheLoader cacheLoaderInstance = PinotThirdEyeDataSource.getCacheLoaderInstance(properties);
    Assert.assertTrue(PinotControllerResponseCacheLoader.class.equals(cacheLoaderInstance.getClass()));
  }

  @Test
  public void testInitializeCacheLoaderFromEmptyClass() throws Exception {
    Map<String, Object> properties = Collections.emptyMap();

    PinotResponseCacheLoader cacheLoaderInstance = PinotThirdEyeDataSource.getCacheLoaderInstance(properties);
    Assert.assertTrue(PinotControllerResponseCacheLoader.class.equals(cacheLoaderInstance.getClass()));
  }

  @Test
  public void testReduceSum() {
    Assert.assertEquals(PinotThirdEyeDataSource.reduce(10, 3, 4, MetricAggFunction.SUM), 13.0);
  }

  @Test
  public void testReduceAvg() {
    Assert.assertEquals(PinotThirdEyeDataSource.reduce(10, 2, 3, MetricAggFunction.AVG), 8.0);
  }

  @Test
  public void testReduceMax() {
    Assert.assertEquals(PinotThirdEyeDataSource.reduce(10, 3, 12, MetricAggFunction.MAX), 10.0);
  }

  @Test
  public void testReduceCount() {
    Assert.assertEquals(PinotThirdEyeDataSource.reduce(4, 3, 4, MetricAggFunction.COUNT), 5.0);
  }

  @Test
  public void testReduceTDigest() {
    Assert.assertEquals(PinotThirdEyeDataSource.reduce(10, 2, 3, MetricAggFunction.PCT50), 8.0);
    Assert.assertEquals(PinotThirdEyeDataSource.reduce(10, 2, 3, MetricAggFunction.PCT90), 8.0);
  }

}
