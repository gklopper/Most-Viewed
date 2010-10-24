package bootstrap.liftweb

import net.liftweb.sitemap.{SiteMap, Menu}
import net.liftweb.http.{RewriteResponse, ParsePath, RewriteRequest, LiftRules}
import net.liftweb.util.NamedPF

class Boot {

  def boot {
    LiftRules.addToPackages("com.gu.mostviewed")

    LiftRules.early.append(r => r.setCharacterEncoding("UTF-8"))

    LiftRules.autoIncludeAjax = _ => false

    LiftRules.autoIncludeComet = _ => false


    LiftRules.statefulRewrite.prepend(NamedPF("SectionMostViewedRewrite") {
      case RewriteRequest(ParsePath("mostviewed" :: section :: Nil, _, _,_), _, _) =>
        RewriteResponse("mostviewed" :: Nil, Map("section" -> section))
    })



    LiftRules.setSiteMap(SiteMap(Menu("Home") / "mostviewed"))
  }
  
}