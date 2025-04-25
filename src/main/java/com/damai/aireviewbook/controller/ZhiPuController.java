package com.damai.aireviewbook.controller;

import com.damai.aireviewbook.dto.AIReviewResult;
import jakarta.annotation.Resource;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ZhiPuController {

    @Resource
    ChatClient.Builder builder;

    @PostMapping("/ai/generate")
    public List<AIReviewResult> generate(@RequestBody AIReviewResult reviewResult) {

        ChatClient chatClient = builder.build();

        List<AIReviewResult> entity = chatClient.prompt()
                .system("""
                        你是一名教辅出版行业的编辑。
                        请以专业 K12 教育视角审校以下教辅内容，需要重点检查：
                        字词句错误(word)：检查内容是是否存在错字；
                        知识性错误(knowledge)：检查内容中是否在常识性、学科知识性的错误；
                        教育合规性(compliance)：是否存在敏感内容，价值观引导是否正向。
                        请逐条列出问题并给出修改建议,如果没有错误返回空列表,如果有错误返回有效的JSON结构。
                        ```
                        [{
                            "content": "small",
                            "suggest": "normal",
                            "type": ""
                        }]
                        ```
                        字段说明:
                        content: 返回错误附近的上下文，20个字左右。
                        suggest: 返回说明具体错误原因和修改建议。
                        type: 错误类型，只能在 word、knowledge、compliance 中选择。
                        """)
                .user(reviewResult.getContent())
                .call()
                .entity(new ParameterizedTypeReference<List<AIReviewResult>>() {});


        return entity;
    }

}
