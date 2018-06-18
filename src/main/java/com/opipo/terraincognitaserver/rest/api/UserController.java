package com.opipo.terraincognitaserver.rest.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.opipo.terraincognitaserver.dto.User;
import com.opipo.terraincognitaserver.service.ServiceDTOInterface;
import com.opipo.terraincognitaserver.service.UserService;

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
        return new ResponseEntity<User>(getService().save(element), HttpStatus.ACCEPTED);
    }

}
