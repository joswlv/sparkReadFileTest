import java.io.{BufferedReader, InputStreamReader}
import java.nio.file.{Files, Paths}

import org.apache.spark.SparkContext

/**
	* Created by SeungWanJo on 2018. 8. 29.
	*/
object SparkResourceTest {
	def main(args: Array[String]): Unit = {
		val sc = new SparkContext()

		//class에서 getResource(AsStream)을 이용한 방식 (상대경로 절대경로 test도 추가)
		readFileByResourceAsStream("config.txt")
		readFileByResourceAsStream("/config.txt")
		readFileByResource("r1/config.txt")
		readFileByResource("/r1/config.txt")

		//classLoader에서 getResource(AsStream)을 이용한 방식 (상대경로 절대경로 test도 추가)
		readFileByClassLoaderResourceAsStream("r1/config.txt")
		readFileByClassLoaderResourceAsStream("/r1/config.txt")
		readFileByClassLoaderResource("r1/config.txt")
		readFileByClassLoaderResource("/r1/config.txt")

		println("class loaders start")
		var loader = SparkResourceTest.getClass.getClassLoader
		do {
			println(s"class loader : ${loader}")
		} while ( {
			loader = loader.getParent;
			loader != null
		})
		println("class loaders end")
	}

	def readFileByClassLoaderResourceAsStream(name: String) = {
		println(s"read file by ClassloaderResourceAsStream : ${name}")
		try {
			val reader = new BufferedReader(new InputStreamReader(SparkResourceTest.getClass.getClassLoader.getResourceAsStream(name)))
			var line: String = null
			while ( {
				line = reader.readLine();
				line != null
			}) {
				println(s"file line : ${line}")
			}
			println("after read file")
		} catch {
			case e: Exception => println(s"getClass file read error : ${e}")
		}
	}

	def readFileByClassLoaderResource(name: String) = {
		println(s"read file by ClassloaderResource : ${name}")
		try {
			val reader = Files.newBufferedReader(Paths.get(SparkResourceTest.getClass.getClassLoader.getResource(name).toURI))
			var line: String = null
			while ( {
				line = reader.readLine();
				line != null
			}) {
				println(s"file line : ${line}")
			}
			println("after read file")
		} catch {
			case e: Exception => println(s"getClass file read error : ${e}")
		}
	}

	def readFileByResourceAsStream(name: String) = {
		println(s"read file by ResourceAsStream : ${name}")
		try {
			val reader = new BufferedReader(new InputStreamReader(SparkResourceTest.getClass.getResourceAsStream(name)))
			var line: String = null
			while ( {
				line = reader.readLine();
				line != null
			}) {
				println(s"file line : ${line}")
			}
			println("after read file")
		} catch {
			case e: Exception => println(s"getClass file read error : ${e}")
		}
	}

	def readFileByResource(name: String) = {
		println(s"read file by Resource : ${name}")
		try {
			val reader = Files.newBufferedReader(Paths.get(SparkResourceTest.getClass.getResource(name).toURI))
			var line: String = null
			while ( {
				line = reader.readLine();
				line != null
			}) {
				println(s"file line : ${line}")
			}
			println("after read file")
		} catch {
			case e: Exception => println(s"getClass file read error : ${e}")
		}
	}
}
