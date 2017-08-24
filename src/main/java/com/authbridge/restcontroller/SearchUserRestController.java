package com.authbridge.restcontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.authbridge.service.UserService;

@RestController
@RequestMapping(value="/rest")
public class SearchUserRestController {

	@Autowired
	private UserService userService;
	
	/**
	 * To get the details of the user using the user id
	 * @param id
	 * @return user details
	 */
	@RequestMapping(value="/user/{id}" , method=RequestMethod.GET )
	public RestControllerResponse getUser(@PathVariable ("id") Long id){
		RestControllerResponse response = new RestControllerResponse();
		try{
			response.setData((userService.findById(id)).getUserName());
			response.setCode(200);
			response.setStatus("success");
		}catch(Exception e){
			response.setCode(500);
			response.setStatus("error");
			response.setData(e.getMessage());
		}
		
		return response;
		
	}
}
