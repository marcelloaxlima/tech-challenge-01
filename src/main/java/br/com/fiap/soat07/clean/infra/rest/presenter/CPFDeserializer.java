package br.com.fiap.soat07.clean.infra.rest.presenter;

import br.com.fiap.soat07.clean.core.domain.value.CPF;
import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.IOException;

public class CPFDeserializer extends JsonDeserializer<CPF> {
    @Override
    public CPF deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JacksonException {
        ObjectCodec oc = jsonParser.getCodec();
        JsonNode node = oc.readTree(jsonParser);
        return CPF.parse(node.get("cpf").textValue()).orElse(CPF.NULL);
    }
}
