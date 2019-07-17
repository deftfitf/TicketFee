lazy val akkaHttpVersion = "10.1.8"
lazy val akkaVersion    = "2.6.0-M4"

lazy val root = (project in file(".")).
  settings(
    inThisBuild(List(
      organization    := "deftfitf",
      scalaVersion    := "2.12.8"
    )),
    name := "ticket-fee",
    libraryDependencies ++= Seq(
      "com.typesafe.akka" %% "akka-http"            % akkaHttpVersion,
      "com.typesafe.akka" %% "akka-http-spray-json" % akkaHttpVersion,
      "com.typesafe.akka" %% "akka-http-xml"        % akkaHttpVersion,
      "com.typesafe.akka" %% "akka-stream"          % akkaVersion,

      "jp.t2v" %% "holidays" % "6.0",
      "com.github.nscala-time" %% "nscala-time" % "2.22.0",

      "com.typesafe.akka" %% "akka-http-testkit"    % akkaHttpVersion % Test,
      "com.typesafe.akka" %% "akka-testkit"         % akkaVersion     % Test,
      "com.typesafe.akka" %% "akka-stream-testkit"  % akkaVersion     % Test,
      "org.scalatest"     %% "scalatest"            % "3.0.5"         % Test
    )
  )
