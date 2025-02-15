package com.example.topichubbackend.controller.rest.admin;

import com.example.topichubbackend.dto.*;
import com.example.topichubbackend.security.util.*;
import com.example.topichubbackend.services.interfaces.*;
import com.example.topichubbackend.util.*;
import jakarta.validation.*;
import lombok.*;
import lombok.extern.slf4j.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import java.util.*;

// Вроде ? в ResponseEntity<> не совсем хорошо
// Сам когда смотрел много где видел, что так и оставляют
// но, если не ошибаюсь, лучше что-то конкретное в тело ответа запихивать стараться
// (этот доеб ко всем контроллерам, не дублирую дальше)

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/admin/article")
public class ArticleModerationController {

    private final IArticleService articleService;
    private final CustomSecurityExpression customSecurityExpression;

    // В дополнение к комментарию выше, как можно было бы сделать
    // Ты точно, значешь, что возвращаешь articles типа PageResponse<ArticleDto>,
    // поэтому можно в декларации метода детализировать тип и поставить ResponseEntity<ArticleStatusDto>.
    // Это снова один из моментов, касательно которых не совсем уверен, так ты обязательно пиши,
    // если не согласен и не впадлу доказать, что я зря докапался
    @GetMapping("/fetch")
    public ResponseEntity<?> getModeration(
            @RequestParam Map<String, String> reqParam
    ){
        var articleFilter = HttpRequestUtils.parseFilterParams(reqParam);
        articleFilter.setUserId(customSecurityExpression.getUserId());
        PageResponse<ArticleDto> articles = articleService.fetch(articleFilter);
        return new ResponseEntity<>(articles, HttpStatus.OK);
    }

    @PostMapping("/status")
    public  ResponseEntity<?> updateStatus(
            @Valid  @RequestBody ArticleStatusDto articleStatusDto
    ){
        articleService.update(articleStatusDto);
        return new ResponseEntity<>(articleStatusDto, HttpStatus.OK);
    }
    @DeleteMapping("/del")
    public  ResponseEntity<?> delete(
           @RequestParam("id") String id
    ){
        articleService.deleteAdmin(id);
        return new ResponseEntity<>(id, HttpStatus.OK);
    }


}
