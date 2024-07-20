package br.com.fiap.soat07.clean.infra.rest.presenter;

import br.com.fiap.soat07.clean.core.domain.value.CPF;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

public class CPFSerializer extends JsonSerializer<CPF> {
    @Override
    public void serialize(CPF cpf, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeString(cpf.toString());
    }
}
