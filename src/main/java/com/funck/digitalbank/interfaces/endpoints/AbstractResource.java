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

    default URI createURI(final String path, final String id, final String value) {
        return ServletUriComponentsBuilder.fromCurrentServletMapping()
                .path(path)
                .buildAndExpand(id, value)
                .toUri();
    }

}
