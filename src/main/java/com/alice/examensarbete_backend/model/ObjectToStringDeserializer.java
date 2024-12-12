package com.alice.examensarbete_backend.model;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.IOException;

public class ObjectToStringDeserializer extends JsonDeserializer<String> {

  @Override
  public String deserialize(JsonParser p, DeserializationContext context) throws IOException {
    // Om det är en sträng, returnera den direkt
    if (p.getCurrentToken().isScalarValue()) {
      return p.getText();
    }

    // Om det är ett objekt, läs objektet
    ObjectNode node = p.getCodec().readTree(p);

    // Om objektet har fältet "value", hämta det
    if (node.has("value")) {
      return node.get("value").asText();
    }

    // Om ingen beskrivning finns, returnera en tom sträng eller en standardtext
    return "";
  }
}
