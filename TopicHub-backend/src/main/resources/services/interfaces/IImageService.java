package com.example.topichubbackend.services.interfaces;

import java.io.*;

public interface IImageService {
    byte[] fetch(String userId);

    void save(String userId, InputStream fileContent) throws IOException;
}
