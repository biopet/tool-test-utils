organization := "com.github.biopet"
organizationName := "Biopet"
name := "tool-test-utils"

biopetUrlName := "tool-test-utils"

startYear := Some(2014)

biopetIsTool := false

developers += Developer(id = "ffinfo",
                        name = "Peter van 't Hof",
                        email = "pjrvanthof@gmail.com",
                        url = url("https://github.com/ffinfo"))
developers += Developer(id = "rhpvorderman",
                        name = "Ruben Vorderman",
                        email = "r.h.p.vorderman@lumc.nl",
                        url = url("https://github.com/rhpvorderman"))

scalaVersion := "2.11.11"

libraryDependencies += "com.github.biopet" %% "test-utils" % "0.3-SNAPSHOT" changing ()
libraryDependencies += "com.github.biopet" %% "tool-utils" % "0.3-SNAPSHOT" changing ()
