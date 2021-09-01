/*
 * Copyright 2018 Red Hat, Inc. and/or its affiliates
 * and other contributors as indicated by the @author tags.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.keycloak.theme;

import freemarker.template.TemplateMethodModelEx;
import freemarker.template.TemplateModelException;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.owasp.html.PolicyFactory;

/**
 * Allows sanitizing of html that uses Freemarker ?no_esc.  This way, html
 * can be allowed but it is still cleaned up for safety.  Tags and attributes
 * deemed unsafe will be stripped out.
 */
public class KeycloakSanitizerMethod implements TemplateMethodModelEx {
    
    private static final PolicyFactory KEYCLOAK_POLICY = KeycloakSanitizerPolicy.POLICY_DEFINITION;

    private static final Pattern HREF_PATTERN = Pattern.compile("\\s+href=\"([^\"]*)\"");
    
    @Override
    public Object exec(List list) throws TemplateModelException {
        if ((list.isEmpty()) || (list.get(0) == null)) {
            throw new NullPointerException("Can not escape null value.");
        }
        
        String html = list.get(0).toString();
        String sanitized = KEYCLOAK_POLICY.sanitize(html);
        
        return fixURLs(sanitized);
    }

    private String fixURLs(String msg) {
        Matcher matcher = HREF_PATTERN.matcher(msg);
        if (matcher.find()) {
            int last = 0;
            StringBuilder result = new StringBuilder(msg.length());
            do {
                String href = matcher.group(1).replaceAll("&#61;", "=")
                        .replaceAll("\\.\\.", ".")
                        .replaceAll("&amp;", "&");
                result.append(msg.substring(last, matcher.start(1))).append(href);
                last = matcher.end(1);
            } while (matcher.find());
            result.append(msg.substring(last));
            return result.toString();
        }
        return msg;
    }
    
}
