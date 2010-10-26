package com.gu.mostviewed.snippet

import net.liftweb.util.BindHelpers
import com.gu.openplatform.contentapi.Api
import net.liftweb.http.{S, RequestVar}
import scala.xml._
import org.joda.time.DateTime
import com.gu.openplatform.contentapi.model.{Section, ItemResponse, Content}

class MostViewed extends BindHelpers {

  def render(xml: NodeSeq) = bind("section", xml,
      "name" -> (Repository.fetch.section match {
        case Some(s) => s.webTitle 
        case None => "guardian.co.uk"
      }),

      "content" -> Repository.fetch.mostViewed.flatMap(c => renderContent(xml, c)))

  private def renderContent(xml: NodeSeq, c: Content) = bind("content", chooseTemplate("section", "content", xml),
    "name" -> Unparsed(c.webTitle).toString,
    AttrBindParam("web-url", c.webUrl, "href"),
    "trail-image" -> renderOptionalTrailImage(xml, c.fields.get.get("thumbnail")),
    "trail-text" -> Unparsed(c.fields.get.get("trailText").getOrElse("")).toSeq)

  private def renderOptionalTrailImage(xml: NodeSeq, imageUrl: Option[String]) = imageUrl match {
      case Some(url) => bind("i", chooseTemplate("content", "trail-image", xml), AttrBindParam("thumbnail", url, "src"))
      case None => Text("")
    }
}

object Repository {

  private object sectionApi extends RequestVar[ItemResponse]({

    //stubApi

    Api.item.showMostViewed().showFields("trailText,thumbnail").itemId(S.param("section").getOrElse("/")).response
  })

  def fetch() = sectionApi.get

  def stubApi: ItemResponse = {
    val fields1 = Some(Map("thumbnail" -> "http:image.com"))

    val fields2 = Some(Map("trailText" -> "The trail text"))

    val content = List(Content("id", None, None, new DateTime, "Title 1", "http://www.google.com", "apiurl", fields1, Nil, Nil, Nil, None),
      Content("id", None, None, new DateTime, "Title 2", "http://www.google.com", "apiurl", fields2, Nil, Nil, Nil, None))

    ItemResponse("OK",
      "",
      None,
      None,
      None,
      None,
      None,
      None,
      None,
      Some(Section("id", "Section name", "http://www.foo.bar.com", "apiurl")),
      None,
      Nil,
      Nil,
      Nil,
      content)
  }

}