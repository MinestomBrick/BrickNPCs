package org.minestombrick.npcs.app.data.converters;

import net.minestom.server.entity.EntityType;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class EntityTypeConverter implements AttributeConverter<EntityType, String> {

    @Override
    public String convertToDatabaseColumn(EntityType attribute) {
        return attribute.name();
    }

    @Override
    public EntityType convertToEntityAttribute(String dbData) {
        return EntityType.fromNamespaceId(dbData);
    }
}