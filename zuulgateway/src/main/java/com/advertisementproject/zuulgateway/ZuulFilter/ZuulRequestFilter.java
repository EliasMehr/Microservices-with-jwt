package com.advertisementproject.zuulgateway.ZuulFilter;

import com.advertisementproject.zuulgateway.security.Utils.JwtUtils;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RequiredArgsConstructor
public class ZuulRequestFilter extends ZuulFilter {

    private final JwtUtils jwtUtils;

    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 1;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() {
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();

        log.info(String.format("%s request to %s", request.getMethod(), request.getRequestURL().toString()));

        String header = request.getHeader("Authorization");
        if(header == null || !header.startsWith("Bearer ")){
            return null;
        }
        String token = header.replace("Bearer ", "");
        String userId = jwtUtils.extractSubject(token);

        ctx.addZuulRequestHeader("userId", userId);
        return null;
    }

}
