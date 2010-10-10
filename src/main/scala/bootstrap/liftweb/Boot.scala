package bootstrap.liftweb

import net.liftweb.http.LiftRules
import net.liftweb.sitemap.{SiteMap, Menu}

class Boot {
  def boot {
    LiftRules.addToPackages("com.gu.mostviewed")
    LiftRules.setSiteMap(SiteMap(Menu("Home") / "index"))
  }
}