# rTimeConf

## En
**rTimeConf** — a lightweight Minecraft plugin for changing personal time and weather. Supports [MiniMessage](https://docs.papermc.io/adventure/minimessage/format/) formatting (e.g., `<gray>` / `<#d187ff>`).

❄ *You can see the configuration at the bottom of the page.*

### Commands
* `/ptime <day|noon|night|midnight|reset|number>`  
  ↳ Changes or resets personal time.
* `/pweather <sun|rain|reset>`  
  ↳ Changes or resets personal weather.
* `/pt reload`  
  ↳ Reloads the plugin configuration.
* `/pt world`  
  ↳ Synchronizes time and weather with the world settings.

---

## Ru
**rTimeConf** — легкий Майнкрафт плагин для изменения персонального времени и погоды. Поддерживает форматирование [MiniMessage](https://docs.papermc.io/adventure/minimessage/format/) (например: `<gray>` / `<#d187ff>`).

### Команды
* `/ptime <day|noon|night|midnight|reset|число>`  
  ↳ Изменяет или сбрасывает персональное время.
* `/pweather <sun|rain|reset>`  
  ↳ Изменяет или сбрасывает персональную погоду.
* `/pt reload`  
  ↳ Перезагружает конфиг плагина.
* `/pt world`  
  ↳ Синхронизирует время и погоду с миром.

---

<details>
<summary><b>config.yml</b></summary>

```yaml

# [https://docs.advntr.dev/minimessage/format.html](https://docs.advntr.dev/minimessage/format.html)

prefix: "<#d187ff>Ⓣ "

messages:
  no-permission: "{prefix}<#d187ff>You don't have permission to use this command!"
  only-players: "{prefix}<#d187ff>This command can only be used in-game!"
  invalid-args-time: "{prefix}<#d187ff>Usage: /ptime <day|night|reset|number>"
  invalid-args-weather: "{prefix}<#d187ff>Usage: /pweather <sun|rain|reset>"
  invalid-args-admin: "{prefix}<#d187ff>Usage: /pt <reload|world>"
  
  time-changed: "{prefix}<#d187ff>Your personal time has been set to: <white>{value}"
  time-reset: "{prefix}<#d187ff>Your time has been synchronized with the server."
  
  weather-changed: "{prefix}<#d187ff>Your personal weather has been changed to: <white>{value}"
  weather-reset: "{prefix}<#d187ff>Your weather has been synchronized with the server."
  
  reload-success: "{prefix}<#d187ff>Config reloaded successfully!"
  world-enabled: "{prefix}<#d187ff>You now see the real time of the world."
````
</details>

## Modrinth link:
https://modrinth.com/plugin/rtimeconf

## License 
MIT — see [LICENSE](LICENSE)
