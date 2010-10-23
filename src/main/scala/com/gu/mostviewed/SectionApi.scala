package com.gu.mostviewed

import net.liftweb.http.{S, RequestVar}
import com.gu.openplatform.contentapi.Api
import com.gu.openplatform.contentapi.connection.JavaNetHttp
import com.gu.openplatform.contentapi.model.{ItemResponse, Section, Tag, Content}

object SectionApi {

  private object sectionApi extends RequestVar[ItemResponse]({
    val sectionId = S.param("section").get
    println("Fetching " + sectionId)

    val api: Api = new Api with JavaNetHttp
    api.item.showMostViewed.showFields("trailText,thumbnail").itemId(sectionId).response
  })

  def fetch() = sectionApi.get
}