import model.settings.{GameSettings, Settings, SettingsImpl}
import org.scalatest.BeforeAndAfterAll
import org.scalatest.funsuite.AnyFunSuite

import java.nio.file.{Files, Paths}

class SettingsTest extends AnyFunSuite with BeforeAndAfterAll:

  val projectRoot: String = System.getProperty("user.dir")
  val settingsFilePath: String = s"$projectRoot/settingsTest/settings.json"

  test("Updating the settings"):
    val settings: Settings = SettingsImpl(settingsFilePath)
    assert(settings.getSettings.equals(GameSettings.defaultSettings))
    settings.updateSettings(GameSettings("hard"))
    assert(settings.getSettings.equals(GameSettings("hard")))

  test("Check if the settings saves and loads the data"):
    var settings: Settings = SettingsImpl(settingsFilePath)
    settings.updateSettings(GameSettings("hard"))
    settings = SettingsImpl(settingsFilePath)
    assert(settings.getSettings.equals(GameSettings("hard")))

  test("Resetting the settings"):

    val settings: Settings = SettingsImpl(settingsFilePath)
    assert(settings.getSettings.equals(GameSettings("hard")))
    settings.resetSettings()
    assert(settings.getSettings.equals(GameSettings.defaultSettings))

  override def afterAll(): Unit =
    Files.delete(Paths.get(settingsFilePath))
    Files.delete(Paths.get(settingsFilePath).getParent)