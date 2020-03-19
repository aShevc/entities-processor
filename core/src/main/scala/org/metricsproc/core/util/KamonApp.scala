package org.metricsproc.core.util

import kamon.Kamon

/*
 It is important for KamonApp extension to be extended first in the resulting object, so that Kamon would be initialized
 before an app starts.
*/
trait KamonApp {
   this: Config =>
  Kamon.init(config)
}
