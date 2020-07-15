package crossway.codec;

import crossway.ext.ExtensionLoaderFactory;

public class SerializerFactory {

    public static Serializer getSerializer(String id) {
        return ExtensionLoaderFactory.getExtensionLoader(Serializer.class).getExtension(id);
    }
}
