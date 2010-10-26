package bootstrap.liftweb

import net.liftweb.util.NamedPF
import net.liftweb.http._
import provider.{HTTPParam, HTTPResponse}

class Boot {

  def boot {
    LiftRules.addToPackages("com.gu.mostviewed")

    LiftRules.early.append(r => r.setCharacterEncoding("UTF-8"))
    LiftRules.beforeSend.append{
      case (basicResponse: BasicResponse, httpResponse: HTTPResponse, _, _ ) => httpResponse.addHeaders(HTTPParam("Cache-Control", "max-age=500") :: Nil)
    }
        
    LiftRules.autoIncludeAjax = _ => false

    LiftRules.autoIncludeComet = _ => false

    LiftRules.enableContainerSessions = false

    LiftRules.statefulRewrite.prepend(NamedPF("SectionMostViewedRewrite") {
      case RewriteRequest(ParsePath("mostviewed" :: section :: Nil, _, _,_), _, _) =>
        RewriteResponse("mostviewed" :: Nil, Map("section" -> section))
    })
  }
}