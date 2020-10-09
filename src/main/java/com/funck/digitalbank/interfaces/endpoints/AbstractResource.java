package com.funck.digitalbank.interfaces.endpoints;

import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

public interface AbstractResource {

    default URI createURI(String id) {
        return ServletUriComponentsBuilder.fromCurrentRequestUri()
                .path("/{id}/endereco")
                .buildAndExpand(id)
                .toUri();
    }

}
