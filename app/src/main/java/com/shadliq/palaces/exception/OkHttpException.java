package com.shadliq.palaces.exception;

import java.io.IOException;

public class OkHttpException extends IOException {

    public OkHttpException(String message) {
        super(message);
    }
}
