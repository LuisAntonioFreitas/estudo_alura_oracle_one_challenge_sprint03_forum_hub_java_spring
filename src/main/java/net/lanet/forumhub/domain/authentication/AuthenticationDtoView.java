package net.lanet.forumhub.domain.authentication;

public record AuthenticationDtoView(
        String token,
        String refreshToken
) {
}
