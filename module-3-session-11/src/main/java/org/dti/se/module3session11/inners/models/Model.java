package org.dti.se.module3session11.inners.models;

import com.ongres.scram.common.bouncycastle.base64.Base64;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import java.io.Serializable;
import java.lang.reflect.Field;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@Accessors(chain = true)
public class Model implements Serializable {

    @SuppressWarnings("unchecked")
    public <T> T patchFrom(T object) {
        try {
            for (Field field : this.getClass().getDeclaredFields()) {
                field.setAccessible(true);
                Object value = field.get(object);
                if (value != null) {
                    field.set(this, value);
                }
            }
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }

        return (T) this;
    }

    public String toJsonString() {
        return Jackson2ObjectMapperBuilder.json().build().valueToTree(this).toString();
    }

    public String toJsonStringHash() {
        return Base64.toBase64String(this.toJsonString().getBytes());
    }

}
