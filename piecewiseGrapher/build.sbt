name := "piecewiseGrapher"

version := "1.0"

scalaVersion := "2.11.2"

libraryDependencies ++=
  Seq( "org.scala-lang.modules" %% "scala-parser-combinators" % "1.0.1",
    "org.scalatest" % "scalatest_2.11" % "2.1.7",
    "org.scala-lang" % "scala-compiler" % scalaVersion.value,
    "org.scalafx" % "scalafx_2.11" % "8.0.5-R5")

libraryDependencies  ++= Seq(
  "org.scalanlp" %% "breeze" % "0.10",
  "org.scalanlp" %% "breeze-natives" % "0.10",
  "org.sameersingh.scalaplot" % "scalaplot" % "0.0.3",
  "org.scala-lang" % "scala-swing" % "2.9.2"
)

resolvers ++= Seq(
  "Sonatype Releases" at "https://oss.sonatype.org/content/repositories/releases/"
)