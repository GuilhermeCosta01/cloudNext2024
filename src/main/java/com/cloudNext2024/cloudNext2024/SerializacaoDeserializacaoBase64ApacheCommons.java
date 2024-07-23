package com.cloudNext2024.cloudNext2024;

import org.apache.commons.codec.binary.Base64;

public class SerializacaoDeserializacaoBase64ApacheCommons {

	

		String textoOriginal = "esta é uma string de teste para serialização/deserialização em Base64";

//		System.out.println("Texto original: " + textoOriginal);

		Base64 base64 = new Base64();

		String textoSerializado = base64.encodeAsString(textoOriginal.getBytes());// Converte a string original em um array de bytes usando o método getBytes().

//		System.out.println("Texto em Base64: " + textoSerializado);

		String textoDeserializado = new String(base64.decode(textoSerializado));// Decodifica a string Base64 de volta para bytes usando o método decode do objeto Base64.

//		System.out.println("Texto deserializado: " + textoDeserializado);

	

}
