package com.gu.mostviewed.snippet

import net.liftweb.util.BindHelpers
import xml.{Text, Unparsed, NodeSeq}
import com.gu.openplatform.contentapi.Api
import net.liftweb.http.{S, RequestVar}
import com.gu.openplatform.contentapi.model.{ItemResponse, Content}

class MostViewed extends BindHelpers{

  private def renderContent(xml: NodeSeq, c: Content) = bind("content", chooseTemplate("content", "list", xml),
        "name" -> Text(c.webTitle),
        AttrBindParam("web-url", c.webUrl, "href"),
        "trail-image" -> renderOptionalTrailImage(xml, c.fields.get.get("thumbnail")),
        "trail-text" -> renderOptionalTrailText(xml, c.fields.get.get("trailText")))

  private def renderOptionalTrailImage(xml: NodeSeq, imageUrl: Option[String]) =
    imageUrl.toList.flatMap(url => bind("i", chooseTemplate("image", "optional", xml), AttrBindParam("thumbnail", url, "src")))

  private def renderOptionalTrailText(xml: NodeSeq, trailText: Option[String]) =
    trailText.toList.flatMap(text => bind("t", chooseTemplate("text", "optional", xml), "trail-text" -> Unparsed(text)))


  def render(xml: NodeSeq) = {
    bind("section", xml, 
      "name" -> Repository.fetch.section.get.webTitle,
      "content" -> Repository.fetch.mostViewed.flatMap(c => renderContent(xml, c)))
  }

}

object Repository {

  private object sectionApi extends RequestVar[ItemResponse]({
    Api.item.showMostViewed.showFields("trailText,thumbnail").itemId(S.param("section").getOrElse("news")).response
  })

  def fetch() = sectionApi.get
}