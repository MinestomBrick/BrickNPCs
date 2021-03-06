package org.minestombrick.npcs.app.data.converters;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonObject;
import net.minestom.server.entity.PlayerSkin;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class PlayerSkinConverter implements AttributeConverter<PlayerSkin, String> {

    private final static JsonDeserializer<PlayerSkin> deserializer = (json, typeOfT, context) -> {
        JsonObject obj = json.getAsJsonObject();
        return new PlayerSkin(
                obj.get("textures").getAsString(),
                obj.has("signature") ? obj.get("signature").getAsString() : null
        );
    };

    private final static Gson gson = new GsonBuilder()
            .registerTypeAdapter(PlayerSkin.class, deserializer)
            .create();

    @Override
    public String convertToDatabaseColumn(PlayerSkin attribute) {
        return gson.toJson(attribute);
    }

    @Override
    public PlayerSkin convertToEntityAttribute(String dbData) {
        return gson.fromJson(dbData, PlayerSkin.class);
    }
}