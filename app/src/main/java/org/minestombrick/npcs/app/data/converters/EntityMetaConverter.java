package org.minestombrick.npcs.app.data.converters;

import net.minestom.server.entity.Metadata;
import net.minestom.server.utils.binary.BinaryReader;
import net.minestom.server.utils.binary.BinaryWriter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Map;

@Converter(autoApply = true)
public class EntityMetaConverter implements AttributeConverter<Metadata, byte[]> {

    private static Field metadataMapField;

    static {
        try {
            metadataMapField = Metadata.class.getDeclaredField("metadataMap");
            metadataMapField.setAccessible(true);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
    }

    @Override
    public byte[] convertToDatabaseColumn(Metadata attribute) {
        try ( BinaryWriter writer = new BinaryWriter(); ) {
            attribute.getEntries().forEach(entry -> entry.write(writer));
            return writer.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new byte[0];
    }

    @Override
    public Metadata convertToEntityAttribute(byte[] dbData) {
        try ( BinaryReader reader = new BinaryReader(dbData) ) {
            Metadata metadata = new Metadata(null);
            Map<Short, Metadata.Entry<?>> map = (Map<Short, Metadata.Entry<?>>) metadataMapField.get(metadata);

            while (true) {
                byte index = reader.readByte();
                if (index == (byte) 0xFF) { // reached the end
                    break;
                }
                map.put((short) index, new Metadata.Entry<>(reader));
            }

            return metadata;
        } catch (IllegalAccessException | IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}