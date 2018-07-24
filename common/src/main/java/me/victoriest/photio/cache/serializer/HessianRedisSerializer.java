/*
 * 2011-9-2 o≧﹏≦o Powered by EXvision
 * 2017-6-27 edit by Victorest
 */

package me.victoriest.photio.cache.serializer;

import com.caucho.hessian.io.Hessian2Input;
import com.caucho.hessian.io.Hessian2Output;
import com.caucho.hessian.io.SerializerFactory;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

/**
 * @author VictoriEST
 */
public class HessianRedisSerializer implements RedisSerializer<Object> {
    private static final SerializerFactory _serializerFactory = new SerializerFactory();

    @Override
    public byte[] serialize(Object object) throws SerializationException {
        if (object == null) {
            return new byte[0];
        }
        try {
            ByteArrayOutputStream stream = new ByteArrayOutputStream();

            Hessian2Output output = new Hessian2Output(stream);
            output.setSerializerFactory(_serializerFactory);
            output.writeObject(object);
            output.flush();
            byte[] bytes = stream.toByteArray();
            stream.close();
            return bytes;
        } catch (Exception ex) {
            throw new SerializationException("Cannot serialize", ex);
        }
    }

    @Override
    public Object deserialize(byte[] bytes) throws SerializationException {
        if ((bytes == null || bytes.length == 0)) {
            return null;
        }
        try {
            ByteArrayInputStream inputStream = new ByteArrayInputStream(bytes);
            Hessian2Input input = new Hessian2Input(inputStream);
            input.setSerializerFactory(_serializerFactory);
            Object obj = input.readObject();
            input.close();
            return obj;
        } catch (Exception ex) {
            throw new SerializationException("Cannot deserialize", ex);
        }
    }

}
