name := "spark_resource_test"
version := "0.1"
scalaVersion := "2.10.7"
resolvers += Resolver.mavenLocal

val sparkVersion = "1.6.3"
assemblyJarName in assembly :=  artifact.value.name + "-" + version.value + "." + artifact.value.extension

libraryDependencies ++= Seq(
	"org.apache.spark" %% "spark-core" % "1.6.0" % "provided"
)

assemblyMergeStrategy in assembly := {
	case PathList("com","payco",xs @ _*) => MergeStrategy.last
	case _ => MergeStrategy.discard
}