package com.gu.mostviewed.snippet

import net.liftweb.util.BindHelpers
import com.gu.mostviewed.SectionApi
import xml.{Text, Unparsed, NodeSeq}
import com.gu.openplatform.contentapi.model.Content

class SectionMostViewed extends BindHelpers{

  def name(xml: NodeSeq) = {
    bind("section", xml, "name" -> SectionApi.fetch.section.get.webTitle)
  }

  def mostViewed(xml: NodeSeq) = SectionApi.fetch.mostViewed.flatMap( c => bindContent(xml, c))

  def bindContent(xml: NodeSeq, c: Content) = bind("content", xml, "name" --> c.webTitle,
    "trail-text" --> Unparsed(c.fields.get.get("trailText").get),
    AttrBindParam("href", c.webUrl, "href"),
    "trail-image" --> toTrailImage(c))

  def toTrailImage(c: Content) = c.fields.get.get("thumbnail") match {
    case Some(url) => <img src={url}/>
    case None => Text("")
  }

}