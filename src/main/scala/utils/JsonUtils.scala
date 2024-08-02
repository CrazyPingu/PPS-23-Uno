package utils

import play.api.libs.json.{Json, Reads, Writes}

import java.nio.file.{Files, Paths, StandardOpenOption}

/**
 * Utility object for JSON operations.
 */
object JsonUtils:

  /**
   * Loads an object from a JSON file.
   *
   * @param filePath The path to the JSON file.
   * @param reads    Implicit `Reads` instance for the type `T`.
   * @tparam T The type of the object to load.
   * @return An `Option` containing the loaded object, or `None` if the file does not exist or cannot be parsed.
   */
  def loadFromFile[T](filePath: String)(implicit reads: Reads[T]): Option[T] =
    val path = Paths.get(filePath)
    if Files.exists(path) then
      val json = new String(Files.readAllBytes(path))
      Json.parse(json).asOpt[T]
    else None

  /**
   * Saves an object to a JSON file.
   *
   * @param filePath The path to the JSON file.
   * @param data     The object to save.
   * @param writes   Implicit `Writes` instance for the type `T`.
   * @tparam T The type of the object to save.
   */
  def saveToFile[T](filePath: String, data: T)(implicit writes: Writes[T]): Unit =
    val json = Json.prettyPrint(Json.toJson(data))
    val path = Paths.get(filePath)
    if !Files.exists(path.getParent) then Files.createDirectories(path.getParent)
    Files.write(path, json.getBytes, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING)
