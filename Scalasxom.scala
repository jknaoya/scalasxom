#!/usr/bin/env scala
# vim:set ft=c:
!#

import java.io.File
import scala.io.Source

object Scalasxom {
	val dataDirectory = "data/"

	class Entry(val file:File) {
		lazy val source = Source.fromFile(file)
		lazy val title  = source.getLine(1)
		lazy val body   = source.getLines.drop(1).mkString("")
		lazy val time   = file.lastModified
		lazy val name   = file.getName.replaceAll("^"+dataDirectory+"|\\..*$", "")

		println("file :"+file)
		println("name :"+name)
		println("title:"+title)
		println("body :"+body)
		println()
	}


	def entries = {
		entryFiles(new File(dataDirectory))
			.filter(
				_.getName.endsWith(".txt")
			)
			.map(
				new Entry(_)
			)
	}

	def entryFiles(dir:File) = {
		def _entryFiles(dirs:List[File], result:List[File]):List[File] = dirs.isEmpty match {
			case true  => result
			case false => {
				val files = dirs.head.listFiles.toList
				_entryFiles(
					dirs.tail ::: files.filter(_.isDirectory),
					result    ::: files.filter(!_.isDirectory)
				)
			}
		}
		_entryFiles( List(dir), List[File]() );
	}

	def run(args: Array[String]) = {
		val e = entries
		println(e)
	}
}

Scalasxom.run(args)

