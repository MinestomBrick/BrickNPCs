# BrickNPCs

An extension for [Minestom](https://github.com/Minestom/Minestom) to create persistent NPCs.

## Install

Get the latest jar file from [Github actions](https://github.com/MinestomBrick/BrickWorlds/actions) 
and place it in the extension folder of your minestom server.

## Usage
### Templates

An NPC templatate contains all the necessary information that makes the npc unique (name, skin, traits, ...). 
A single template can be used for multiple npcs, changing a template will also update all npcs.

| Command                                        | Permission                         |
|------------------------------------------------|------------------------------------|
| /bn template list                              | bricknpcs.template.list            |
| /bn template create (name) (entitytype)        | bricknpcs.template.create          |
| /bn template delete (template)                 | bricknpcs.template.delete          |
| /bn template edit customname (template) (name) | bricknpcs.template.edit.customname |
| /bn template edit skin (template) (player)     | bricknpcs.template.edit.skin       |

### Spawns

A spawn is a position in the world where an npc is spawned with a specific template.
You can create multiple spawns with the same template.

| Command                            | Permission                    |
|------------------------------------|-------------------------------|
| /bn spawn list                     | bricknpcs.spawn.list          |
| /bn spawn create (name) (template) | bricknpcs.spawn.create        |
| /bn spawn delete (spawn)           | bricknpcs.spawn.delete        |
| /bn spawn edit lookhere (spawn)    | bricknpcs.spawn.edit.lookhere |

### NPCS

You can instantly create an template and a spawn of the same name with the following command.


| Command                            | Permission           |
|------------------------------------|----------------------|
| /bn npc create (name) (entitytype) | bricknpcs.npc.create |

## Database

You can change the database settings in the `config.json`.

```json
{
  "database": {
    "dsn": "jdbc:h2:file:./extensions/BrickPermissions/data/database.h2",
    "username": "dbuser",
    "password": "dbuser"
  }
}
```

MySQL is supported, use the following format:

````
"dsn": "jdbc:mysql://<hostname>:<ip>/<database>"
````

## Credits

* The [Minestom](https://github.com/Minestom/Minestom) project

## Contributing

Check our [contributing info](CONTRIBUTING.md)

