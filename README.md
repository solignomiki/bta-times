![icon](icon.png)

# Times
> Better than Adventure Mod

Allows you to change duration of seasons or to tie them to real world seasons


> Made for servers, but you need to have the mod on client to see actual seasons from server. 
> 
> It will work anyway if not installed on client, 
> but players will have schizophrenia moment when **snow will fall at Summer**

Config file will be located in your server folder at `config/times.cfg` after first start of server with mod.
You can also create it by yourself

## Config

- `Mode` — Mode in which mod will work<br /> Available options:
    - `LENGTH` — Changing length of each individual season
    - `REALTIME` — Seasons length will be calculated based on current real year. During start of the server it will set time to current real one
- `TurnOffSleep` — Option to turn off sleep. Can be either `true` or `false`. Default is `false`
- `[LENGTH]` - Options for LENGTH mode
    - `SpringLength` — Length of a Spring for `LENGTH` mode. Integer
    - `SummerLength` — Length of a Summer for `LENGTH` mode. Integer
    - `FallLength` — Length of a Fall for `LENGTH` mode. Integer
    - `WinterLength` — Length of a Winter for `LENGTH` mode. Integer
- `[REALTIME]` - Options for REALTIME mode
    - `Hemisphere` — Affects order of seasons. For places southern side of Ecuator `SOUTHERN` will be true while for northern, `NORTHERN`.<br/>Can be `NORTHERN` or `SOUTHERN`. Default is `NORTHERN` 

<br />

---

# Времечко

> Мод для Better than Adventure

Позволяет поменять длительность сезонов или привязать их к сезонам в реальном мире

> Сделан для серверов, но вам нужно иметь мод на клиенте чтобы видеть настоящие сезоны с сервера.
> 
> Работать в ином случае оно будет, но у игроков будет эффект легкой шизофрении в тот момент когда **снег начнёт падать Летом**

Конфиг файл будет расположен в папке вашего сервера по пути `config/times.cfg` после первого запуска сервера с модом.
Вы также можете создать его сами

## Конфиг

- `Mode` — Режим в котором будет работать мод<br /> Возможные опции:
  - `LENGTH` — Изменение длины каждого конкретного сезона
  - `REALTIME` — Длина сезонов будет расчитываться исходя из текущего года. При старте сервера, установит время на текущее реальное
- `TurnOffSleep` — Возможность выключить сон. Может быть или `true` или `false`. По умолчанию `false`
- `[LENGTH]` - Настройки для режима `LENGTH`
    - `SpringLength` — Длина весны для режима `LENGTH`. Целое число
    - `SummerLength` — Длина лета для режима `LENGTH`. Целое число
    - `FallLength` — Длина осени для режима `LENGTH`. Целое число
    - `WinterLength` — Длина зимы для режима `LENGTH`. Целое число
- `[REALTIME]` - Настройки для режима `REALTIME`
    - `Hemisphere` — Влияет на порядок сезонов. Для мест по южную сторону экватора будет верна опция `SOUTHERN`, а для тех что по северную, `NORTHERN`<br/>Может быть `NORTHERN` или `SOUTHERN`. По умолчанию — `NORTHERN`
