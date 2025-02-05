package com.example.topichubbackend.services.interfaces;

import java.io.*;
import java.util.*;

public interface IFileStorage {
    boolean save(InputStream fileContent, String string);

    Optional<byte[]> findByPath(String userId);
}
