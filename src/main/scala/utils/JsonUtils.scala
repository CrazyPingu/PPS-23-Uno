package utils

import play.api.libs.json.{Json, Reads, Writes}

import java.nio.file.{Files, Paths, StandardOpenOption}

object JsonUtils:

  def loadFromFile[T](filePath: String)(implicit reads: Reads[T]): Option[T] =
    val path = Paths.get(filePath)
    if Files.exists(path) then
      val json = new String(Files.readAllBytes(path))
      Json.parse(json).asOpt[T]
    else
      None

  def saveToFile[T](filePath: String, data: T)(implicit writes: Writes[T]): Unit =
    val json = Json.prettyPrint(Json.toJson(data))
    val path = Paths.get(filePath)
    if !Files.exists(path.getParent) then
      Files.createDirectories(path.getParent)
    Files.write(path, json.getBytes, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING)