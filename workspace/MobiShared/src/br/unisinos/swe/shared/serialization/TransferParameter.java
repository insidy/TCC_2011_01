package br.unisinos.swe.shared.serialization;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)       
@Target(ElementType.FIELD)  
public @interface TransferParameter {
	String value();
}
