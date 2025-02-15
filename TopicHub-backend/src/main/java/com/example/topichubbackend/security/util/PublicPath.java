package com.example.topichubbackend.security.util;

// У тебя есть этот енам, а в SecurityConfig в securityFilterChain все public урлы все равно статическими строками
// вручную прописаны (наверное там тоже можно было бы использовать?)
public enum PublicPath {
    AUTH("/auth"),
    API_AUTH("/api/v1/auth/**"),
    API_ARTICLE("/api/v1/article"),
    API_SEARCH("/api/v1/search"),
    API_PROF_SEARCH("/api/v1/profile/search"),
    API_ANSWERS("/api/v1/answers"),
    API_IMAGE("/api/v1/image"),
    API_HUBS("/api/v1/hubs");
    private final String type;

    PublicPath(String type) {
        this.type = type;
    } // Вот здесь type как будто обоснован, а в StatusDto
                                                  // и RoleDto у меня вопросики были

    public String type() {
        return type;
    }
}
