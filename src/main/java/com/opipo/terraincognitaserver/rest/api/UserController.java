package com.opipo.terraincognitaserver.rest.api;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.common.exceptions.UnauthorizedUserException;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.opipo.terraincognitaserver.dto.Role;
import com.opipo.terraincognitaserver.dto.User;
import com.opipo.terraincognitaserver.security.Usuario;
import com.opipo.terraincognitaserver.service.ServiceDTOInterface;
import com.opipo.terraincognitaserver.service.UserService;
import com.opipo.terraincognitaserver.service.impl.AbstractServiceDTO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping("/user")
@Api(value = "REST API to manage users")
public class UserController extends AbstractCRUDController<User, String> {

    @Autowired
    private UserService service;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    protected ServiceDTOInterface<User, String> getService() {
        return service;
    }

    @Override
    protected String getIdFromElement(User element) {
        return element.getUsername();
    }

    @Override
    @RequestMapping(value = "/{id}", method = RequestMethod.POST)
    @ApiOperation(value = "Create", notes = "Create a element if you know the id")
    public @ResponseBody ResponseEntity<User> create(@PathVariable("id") String id,
            @ApiParam(value = "Element to create if you want to save it directly", required = false) @RequestBody(required = false) User element) {
        Assert.isNull(getService().find(id), "The element exists now");
        if (element == null) {
            element = getService().create(id);
        } else {
            Assert.isTrue(checkIdFromElement(id, element), "The id is not the expected");
        }
        element.setPassword(passwordEncoder.encode(element.getPassword()));
        return super.create(id, element);
    }

    @RequestMapping(value = "/{id}/changePassword", method = RequestMethod.PUT)
    @ApiOperation(value = "Update", notes = "Update one element given the element")
    @PreAuthorize("hasPermission(#id, 'create')")
    public @ResponseBody ResponseEntity<User> passwordUpdate(
            @ApiParam(value = "The identifier of the element", required = true) @PathVariable("id") String id,
            @ApiParam(value = "Element to update with the changes", required = true) @RequestBody Usuario element) {
        User user = getService().find(element.getUsername());
        if (!passwordEncoder.matches(element.getOldPassword(), user.getPassword())) {
            throw new UnauthorizedUserException(AbstractServiceDTO.WRONG_PASSWORD);
        }
        user.setPassword(passwordEncoder.encode(element.getPassword()));
        Assert.isTrue(checkIdFromElement(id, user), "The id is not the expected");
        Assert.notNull(getService().find(id), "The element doesn't exist");
        return new ResponseEntity<User>(service.changePassword(user), HttpStatus.ACCEPTED);
    }

    @Override
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    @ApiOperation(value = "Update", notes = "Update one element given the element")
    @PreAuthorize("hasPermission(#element, 'create')")
    public @ResponseBody ResponseEntity<User> save(
            @ApiParam(value = "The identifier of the element", required = true) @PathVariable("id") String id,
            @ApiParam(value = "Element to update with the changes", required = true) @RequestBody User element) {
        return super.save(id, element);
    }

    @Override
    @RequestMapping(value = "/{id}", method = RequestMethod.PATCH)
    @ApiOperation(value = "Partial update", notes = "Update the especified attributes of the object")
    @PreAuthorize("hasPermission(#element, 'create')")
    public @ResponseBody ResponseEntity<User> partialUpdate(
            @ApiParam(value = "The identifier of the element", required = true) @PathVariable("id") String id,
            @ApiParam(value = "Element to update with the changes", required = true) @RequestBody User element) {
        return super.partialUpdate(id, element);
    }

    @Override
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ApiOperation(value = "Delete", notes = "Delete element by id")
    @PreAuthorize("hasPermission(#id, 'delete')")
    public @ResponseBody ResponseEntity<String> delete(
            @ApiParam(value = "The identifier of the element", required = true) @PathVariable("id") String id) {
        return super.delete(id);
    }

    @GetMapping("/{id}/role")
    @ApiOperation(value = "ListRoles", notes = "List all the roles of the user")
    @PreAuthorize("hasPermission(#id, 'userrol')")
    public @ResponseBody ResponseEntity<Collection<Role>> getRoles(
            @ApiParam(value = "The identificer of the user", required = true) @PathVariable("id") String id) {
        return new ResponseEntity<>(getService().find(id).getRoles(), HttpStatus.OK);
    }

    @GetMapping("/{id}/role/{role}")
    @ApiOperation(value = "GetRole", notes = "Get the role of a user if exists")
    @PreAuthorize("hasPermission(#id, 'userrol')")
    public @ResponseBody ResponseEntity<Role> getRole(
            @ApiParam(value = "The identificer of the user", required = true) @PathVariable("id") String id,
            @ApiParam(value = "The name of the role", required = true) @PathVariable("role") String role) {
        return new ResponseEntity<>(getService().find(id).getRoles().stream()
                .filter(p -> role.equalsIgnoreCase(p.getAuthority())).findFirst().get(), HttpStatus.OK);
    }

    @PostMapping("/{id}/role/{role}")
    @ApiOperation(value = "AddRole", notes = "Add role to user")
    public @ResponseBody ResponseEntity<User> addRole(
            @ApiParam(value = "The identificer of the user", required = true) @PathVariable("id") String id,
            @ApiParam(value = "The name of the role", required = true) @PathVariable("role") String role) {
        return new ResponseEntity<>(service.addRole(id, role), HttpStatus.ACCEPTED);
    }

}
