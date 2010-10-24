package com.gu.mostviewed.snippet

import net.liftweb.util.BindHelpers
import com.gu.mostviewed.SectionApi
import xml.{Text, Unparsed, NodeSeq}
import com.gu.openplatform.contentapi.model.Content

class SectionMostViewed extends BindHelpers{

  private def renderContent(xml: NodeSeq, c: Content) = bind("c", chooseTemplate("content", "list", xml),
        "name" -> Text(c.webTitle),
        AttrBindParam("href", c.webUrl, "href"),
        "trail-image" -> renderOptionalTrailImage(xml, c.fields.get.get("thumbnail")),
        "trail-text" -> renderOptionalTrailText(xml, c.fields.get.get("trailText")))

  private def renderOptionalTrailImage(xml: NodeSeq, imageUrl: Option[String]) =
    imageUrl.toList.flatMap(url => bind("i", chooseTemplate("image", "optional", xml), AttrBindParam("src", url, "src")))

  private def renderOptionalTrailText(xml: NodeSeq, trailText: Option[String]) =
      trailText.toList.flatMap(text => bind("t", chooseTemplate("text", "optional", xml), "trail-text" -> Unparsed(text)))


  def render(xml: NodeSeq) = {
    bind("section", xml, 
      "name" -> SectionApi.fetch.section.get.webTitle,
      "content" -> SectionApi.fetch.mostViewed.flatMap(c => renderContent(xml, c)))
  }

}