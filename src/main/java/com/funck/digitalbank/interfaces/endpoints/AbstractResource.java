package com.funck.digitalbank.interfaces.endpoints;

import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

public interface AbstractResource {

    default URI createURI(final String path, final String id) {
        return ServletUriComponentsBuilder.fromCurrentServletMapping()
                .path(path)
                .buildAndExpand(id)
                .toUri();
    }

}
