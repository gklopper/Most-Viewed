import sbt._

class Project(info: ProjectInfo) extends DefaultWebProject(info) {

  val liftVersion = "2.1"

  val lift = "net.liftweb" %% "lift-webkit" % liftVersion % "compile->default" withSources()
  val liftCommon = "net.liftweb" %% "lift-common" % liftVersion % "compile->default" withSources()
  val liftMapper = "net.liftweb" %% "lift-mapper" % liftVersion % "compile->default" withSources()

  val jettyServer = "org.mortbay.jetty" % "jetty" % "6.1.22" % "test->default"
}