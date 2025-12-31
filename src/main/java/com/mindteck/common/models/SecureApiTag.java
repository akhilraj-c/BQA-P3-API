package com.mindteck.common.models;

import springfox.documentation.service.Tag;

import java.util.ArrayList;
import java.util.List;

public enum SecureApiTag {

    AUTH_MANAGEMENT(SwaggerHeads.AUTH_API, SwaggerHeads.AUTH_API_DESCRIPTION),
    FORM_MANAGEMENT(SwaggerHeads.FORM_API , SwaggerHeads.FORM_DESCRIPTION),
    USER_MANAGEMENT(SwaggerHeads.USER_API , SwaggerHeads.USER_DESCRIPTION),

    EVALUATION_MANAGEMENT(SwaggerHeads.FORM_EVALUATION, SwaggerHeads.EVALUATION_DESCRIPTION),

    FEEDBACK_MANAGEMENT(SwaggerHeads.FEED_BACK, SwaggerHeads.FEED_BACK_DESCRIPTION),

    MAIL_TEMPLATE_MANAGEMENT(SwaggerHeads.MAIL_TEMPLATE, SwaggerHeads.MAIL_TEMPLATE_DESCRIPTION),

    ;

    private final String name;

    private final String description;

    private SecureApiTag(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public static Tag[] getAll() {
        List<Tag> tags = new ArrayList<>();
        for (SecureApiTag secureApiTag : values()) {
            tags.add(new Tag(secureApiTag.name, secureApiTag.description));
        }
        Tag[] tagArray = new Tag[tags.size()];
        return tags.toArray(tagArray);
    }
}
