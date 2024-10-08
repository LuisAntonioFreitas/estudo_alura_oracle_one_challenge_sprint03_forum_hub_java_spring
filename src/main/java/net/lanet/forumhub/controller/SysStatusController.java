package net.lanet.forumhub.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import net.lanet.forumhub.domain.sysstatus.ISysStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Tag(name = "Status") // Swagger
@RequestMapping(path = "${api.config.path}/status")
public class SysStatusController {

    @Autowired
    @Qualifier("sysStatusService")
    private ISysStatusService service;

    @CrossOrigin(allowedHeaders = "**", origins = "**", methods = {RequestMethod.GET})
    @GetMapping(path = {""})
    public ResponseEntity<Object> getStatus() {
        return service.getStatus();
    }

    @SecurityRequirement(name = "bearer-key") // Swagger
    @Operation(summary = "verifica acesso ao database") // Swagger
    @GetMapping(path = {"/db"})
    public ResponseEntity<Object> getConnectIpDb() {
        return service.getConnectIpDb();
    }

    @SecurityRequirement(name = "bearer-key") // Swagger
    @Operation(summary = "verifica acesso ao ip") // Swagger
    @GetMapping(path = {"/ip/{ip}"})
    public ResponseEntity<Object> getConnectIp(@PathVariable(value = "ip") String ip) {
        return service.getConnectIp(ip);
    }

    //    @Operation(hidden = true) // Swagger
    @Operation(summary = "reinicia à aplicação") // Swagger
    @SecurityRequirement(name = "bearer-key") // Swagger
    @GetMapping(path = {"/restart"}, produces = MediaType.TEXT_HTML_VALUE)
    public String getRestart() { return service.getRestart(); }
}

