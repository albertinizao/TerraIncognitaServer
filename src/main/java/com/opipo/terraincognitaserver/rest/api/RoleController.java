package com.opipo.terraincognitaserver.rest.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.opipo.terraincognitaserver.dto.Role;
import com.opipo.terraincognitaserver.service.RoleService;
import com.opipo.terraincognitaserver.service.ServiceDTOInterface;

import io.swagger.annotations.Api;

@RestController
@RequestMapping("/role")
@Api(value = "REST API to manage users")
public class RoleController extends AbstractCRUDController<Role, String> {

    @Autowired
    private RoleService service;

    @Override
    protected ServiceDTOInterface<Role, String> getService() {
        return service;
    }

    @Override
    protected String getIdFromElement(Role element) {
        return element.getName();
    }

}
