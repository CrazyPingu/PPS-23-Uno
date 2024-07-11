import model.settings.{GameSettings, Settings, SettingsImpl}
import org.scalatest.funsuite.AnyFunSuite

class SettingsTest extends AnyFunSuite:

  test("Updating the settings"):
    val settings: Settings = SettingsImpl("/jsonTest/settings.json")
    assert(settings.getSettings.equals(GameSettings.defaultSettings))
    settings.updateSettings(GameSettings("hard"))
    assert(settings.getSettings.equals(GameSettings("hard")))

  test("Check if the settings saves and loads the data"):
    var settings: Settings = SettingsImpl("/jsonTest/settings.json")
    settings.updateSettings(GameSettings("hard"))
    settings = SettingsImpl("/jsonTest/settings.json")
    assert(settings.getSettings.equals(GameSettings("hard")))

  test("Resetting the settings"):
    val settings: Settings = SettingsImpl("/jsonTest/settings.json")
    assert(settings.getSettings.equals(GameSettings("hard")))
    settings.resetSettings()
    assert(settings.getSettings.equals(GameSettings.defaultSettings))
