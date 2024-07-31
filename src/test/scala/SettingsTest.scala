import model.settings.{Difficulty, GameSettings, Settings, SettingsImpl}
import org.scalatest.BeforeAndAfterAll
import org.scalatest.funsuite.AnyFunSuite

import java.nio.file.{Files, Paths}

class SettingsTest extends AnyFunSuite with BeforeAndAfterAll:

  private val projectRoot: String = System.getProperty("user.dir")
  private val settingsFilePath: String = s"$projectRoot/settingsTest/settings.json"

  test("Updating the settings"):
    val settings: Settings = SettingsImpl(settingsFilePath)
    assert(settings.gameSettings.equals(GameSettings.DEFAULT_SETTINGS))
    settings.updateSettings(GameSettings(Difficulty.Hard, 5, 1))
    assert(settings.gameSettings.equals(GameSettings(Difficulty.Hard, 5, 1)))

  test("Check if the settings saves and loads the data"):
    var settings: Settings = SettingsImpl(settingsFilePath)
    settings.updateSettings(GameSettings(Difficulty.Hard, 7, 0))
    settings = SettingsImpl(settingsFilePath)
    assert(settings.gameSettings.equals(GameSettings(Difficulty.Hard, 7, 0)))

  test("Resetting the settings"):
    val settings: Settings = SettingsImpl(settingsFilePath)
    assert(settings.gameSettings.equals(GameSettings(Difficulty.Hard, 7, 0)))
    settings.resetSettings()
    assert(settings.gameSettings.equals(GameSettings.DEFAULT_SETTINGS))

  override def afterAll(): Unit =
    Files.delete(Paths.get(settingsFilePath))
    Files.delete(Paths.get(settingsFilePath).getParent)
