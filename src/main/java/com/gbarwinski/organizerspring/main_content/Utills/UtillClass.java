package com.gbarwinski.organizerspring.main_content.Utills;

import com.gbarwinski.organizerspring.main_content.model.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class UtillClass {


    public static User getLoggedInUser() {
        HttpSession session = getCurrentHttpRequest().getSession(false);
        if (session!=null)
        return (User) session.getAttribute("appUser");
        else
            return new User();
    }

    public static HttpServletRequest getCurrentHttpRequest() {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        if (requestAttributes instanceof ServletRequestAttributes) {
            HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
            return request;
        }
        return null;
    }

    public static List<String> getListOfIconTitles() throws IOException {
        List<String> result = new ArrayList<>();

        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();

        Resource[] resources = resolver.getResources("classpath*:/**/icons/**/*.png");
        for (Resource resource : resources) {
            result.add(resource.getFilename());
        }
        return result;
    }

    public static List<String> getListOfIconsTitlesWrittenManually() {
        ArrayList<String> strings = new ArrayList<>();

        for (int i = 1; i < 100; i++) {
            String tmp;
            if (i > 0 && i < 10)
                tmp = "00" + i + ".png";
            else
                tmp = "0" + i + ".png";
            strings.add(tmp);
        }

        return strings;
    }
}
