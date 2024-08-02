import model.settings.{Difficulty, Settings, SettingsManager}
import org.scalatest.BeforeAndAfterAll
import org.scalatest.funsuite.AnyFunSuite

import java.nio.file.{Files, Paths}

class SettingsTest extends AnyFunSuite with BeforeAndAfterAll:

  private val projectRoot: String = System.getProperty("user.dir")
  private val settingsFilePath: String = s"$projectRoot/settingsTest/settings.json"

  test("Updating the settings"):
    val settingsManager: SettingsManager = SettingsManager(settingsFilePath)
    assert(settingsManager.settings.equals(Settings.DEFAULT_SETTINGS))
    settingsManager.updateSettings(Settings(Difficulty.Hard, 5, 1))
    assert(settingsManager.settings.equals(Settings(Difficulty.Hard, 5, 1)))

  test("Check if the settings saves and loads the data"):
    val oldSettingsManager: SettingsManager = SettingsManager(settingsFilePath)
    oldSettingsManager.updateSettings(Settings(Difficulty.Hard, 7, 0))
    val newerSettingsManager: SettingsManager = SettingsManager(settingsFilePath)
    assert(newerSettingsManager.settings.equals(Settings(Difficulty.Hard, 7, 0)))

  test("Resetting the settings"):
    val settingsManager: SettingsManager = SettingsManager(settingsFilePath)
    assert(settingsManager.settings.equals(Settings(Difficulty.Hard, 7, 0)))
    settingsManager.updateSettings(Settings.DEFAULT_SETTINGS)
    assert(settingsManager.settings.equals(Settings.DEFAULT_SETTINGS))

  override def afterAll(): Unit =
    Files.delete(Paths.get(settingsFilePath))
    Files.delete(Paths.get(settingsFilePath).getParent)
