package net.lanet.forumhub.infra.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import net.lanet.forumhub.domain.usuario.IUsuarioRepository;
import net.lanet.forumhub.domain.usuario.Usuario;
import net.lanet.forumhub.infra.exception.CustomAuthenticationEntryPoint;
import net.lanet.forumhub.infra.exception.InvalidTokenException;
import net.lanet.forumhub.infra.exception.TokenMissingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.Map;
import java.util.Optional;

import static net.lanet.forumhub.infra.utilities.HandleFindIdOrUuidUtil.getIdOrUuid;


@Component
public class SecurityFilter extends OncePerRequestFilter {
    @Autowired
    private TokenService service;
    @Autowired
    private CustomAuthenticationEntryPoint customAuthenticationEntryPoint;

    @Autowired
    private IUsuarioRepository repositoryUsuario;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        try {
            boolean isPermittedRoute;
            isPermittedRoute =
                    // GET
                    (request.getMethod().equalsIgnoreCase(HttpMethod.GET.toString())
                            && request.getRequestURI().equalsIgnoreCase("/")) ||
                            (request.getMethod().equalsIgnoreCase(HttpMethod.GET.toString())
                                    && request.getRequestURI().equalsIgnoreCase("/status")) ||
                            (request.getMethod().equalsIgnoreCase(HttpMethod.GET.toString())
                                    && request.getRequestURI().startsWith("/error")) ||
                            (request.getMethod().equalsIgnoreCase(HttpMethod.GET.toString())
                                    && request.getRequestURI().startsWith("/doc")) ||
                            (request.getMethod().equalsIgnoreCase(HttpMethod.GET.toString())
                                    && request.getRequestURI().startsWith("/v3/api-docs")) ||
                            (request.getMethod().equalsIgnoreCase(HttpMethod.GET.toString())
                                    && request.getRequestURI().startsWith("/swagger-ui")) ||
                            (request.getMethod().equalsIgnoreCase(HttpMethod.GET.toString())
                                    && request.getRequestURI().equalsIgnoreCase("/swagger-ui.html")) ||
                            // POST
                            (request.getMethod().equalsIgnoreCase(HttpMethod.POST.toString())
                                    && request.getRequestURI().equalsIgnoreCase("/login"))
            ;
            if (isPermittedRoute) { filterChain.doFilter(request, response); return; }

            String token = recoverToken(request, response);
            if (token != null) {
                Map<String, Object> dataToken = service.validateToken(token);
                Object subject = dataToken.get("subject");
                Object id = dataToken.get("id");

                Object[] value = getIdOrUuid(id.toString());
                Long longId = (Long) value[0];
                String uuid = (String) value[1];

                switch (String.valueOf(subject).trim().toLowerCase()) {
                    case "usuario":
                        var item = repositoryUsuario.findFirstByIdOrUuid(longId, uuid);
                        var authentication = new UsernamePasswordAuthenticationToken(item, null, item.getAuthorities());
                        SecurityContextHolder.getContext().setAuthentication(authentication);
                        break;
                    default:
                        throw new InvalidTokenException("");
                }
            } else {
                throw new TokenMissingException("");
            }

            filterChain.doFilter(request, response);
        } catch (Exception ex) {
            customAuthenticationEntryPoint.commence(request, response, new AuthenticationException(ex.getMessage(), ex) {});
        }

    }

    public String recoverToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        var authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader != null) {
            String token = Arrays.stream(authorizationHeader.split(" ")).toList().get(1);
            return token;
        }
        return null;
    }
}
