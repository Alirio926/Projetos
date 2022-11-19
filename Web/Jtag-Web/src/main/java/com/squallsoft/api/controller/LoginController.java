package com.squallsoft.api.controller;


import javax.annotation.PostConstruct;
import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.squallsoft.api.config.DBContextHolder;
import com.squallsoft.api.config.DBTypeEnum;
import com.squallsoft.api.dominios.User;
import com.squallsoft.api.service.UserService;

//https://medium.com/@gustavo.ponce.ch/spring-boot-spring-mvc-spring-security-mysql-a5d8545d837d#id_token=eyJhbGciOiJSUzI1NiIsImtpZCI6ImQzZmZiYjhhZGUwMWJiNGZhMmYyNWNmYjEwOGNjZWI4ODM0MDZkYWMiLCJ0eXAiOiJKV1QifQ.eyJpc3MiOiJodHRwczovL2FjY291bnRzLmdvb2dsZS5jb20iLCJuYmYiOjE2MjE1MzY0MzAsImF1ZCI6IjIxNjI5NjAzNTgzNC1rMWs2cWUwNjBzMnRwMmEyamFtNGxqZGNtczAwc3R0Zy5hcHBzLmdvb2dsZXVzZXJjb250ZW50LmNvbSIsInN1YiI6IjExNTA0Mzc1OTIxMDgxODA5NTA3NCIsImVtYWlsIjoiYWxpcmlvZmlsaG85MjZAZ21haWwuY29tIiwiZW1haWxfdmVyaWZpZWQiOnRydWUsImF6cCI6IjIxNjI5NjAzNTgzNC1rMWs2cWUwNjBzMnRwMmEyamFtNGxqZGNtczAwc3R0Zy5hcHBzLmdvb2dsZXVzZXJjb250ZW50LmNvbSIsIm5hbWUiOiJBbGlyaW8gRnJlaXJlIiwicGljdHVyZSI6Imh0dHBzOi8vbGgzLmdvb2dsZXVzZXJjb250ZW50LmNvbS9hL0FBVFhBSnhLNzFRTFVhazI2N2FjeU9PeXotRlZ3b0FOeEx6WWcyVThDUG4yPXM5Ni1jIiwiZ2l2ZW5fbmFtZSI6IkFsaXJpbyIsImZhbWlseV9uYW1lIjoiRnJlaXJlIiwiaWF0IjoxNjIxNTM2NzMwLCJleHAiOjE2MjE1NDAzMzAsImp0aSI6IjcwNmMyNzNmYjNlNmU1NzgwM2Y4NmU2N2MyMWE5MjI1ZDkwYmM4N2IifQ.Z2-Ant4KJuLyyBw5F-10Gwx3xG756gRo0iByoCDcAEx1g9l5Pop0HzSzjg8wHXUL3X4d9wd07OfBy0plTlRtDkjM9yjb13HKbsPY8QfUT-AArx5bLG9iNS5rNznDhyx_vuezs-QLitm9-pOSRAACzI965ZlpK9Y-UlT9wkZPpJzkHmTbqXNzV4oUK0v9W7BE7Ya2zMzDiT3xROYXIqfyAnizRVKsCqVZ-EaOolY3v-DdhR1xK-oa26djhvgVcoWlRn7q1alEPesR3pb2RYuZxa8-UH85zuNt8nm-OGbHVG4rVn_p0f534KmRaKKITr7B4skJxoz1DK0qzJIr2Jo0-A
//https://www.codejava.net/frameworks/spring-boot/get-logged-in-user-details
@Controller
@RequestMapping("/")
public class LoginController {

    @Autowired
    private UserService userService;

    //@PostConstruct
    void init() {
		User u = new User();
		u.setActive(Boolean.TRUE);
		u.setEmail("aliriofilho926@yahoo.com.br");
		u.setLastName("Oliveira");
		u.setName("Alirio");
		u.setPassword("141926");
		u.setUserName("squall");
		
		userService.saveUser(u);
	}
    
    
    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String getIndex(Model model) {    	
    	return "home.bemvindo.tiles";    	
    }
    
    //---------
    // Login: admin
    // Senha: retroXXX
    //---------
    // Login: retrox
    // Senha: retroXXX
    //---------
    // Login: ninten
    // Senha: duino
    //---------
    // Login: prisma
    // Senha: games
    //---------
    
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(){
        return "home.login.tiles";
    }
    
    @RequestMapping(value ="/access-denied", method = RequestMethod.GET)
    public String denied() {
    	//return "home.denied.tiles";
    	return "redirect:/index";
    }
        
    //@RolesAllowed("ADMIN")
    @RequestMapping(value="/admin/registration", method = RequestMethod.GET)
    public String registration(Model model){
        User user = new User();
        
        model.addAttribute("user", user);
        
        return "home.registration.tiles";
    }

    @RolesAllowed("ADMIN")
    @RequestMapping(value = "/admin/registration", method = RequestMethod.POST)
    public String createNewUser(@Valid User user, BindingResult bindingResult, Model model) {
        ModelAndView modelAndView = new ModelAndView();
        User userExists = userService.findUserByUserName(user.getUserName());
        if (userExists != null) {
        	System.out.println("Error ja existe");
        	        	
            bindingResult
                    .rejectValue("userName", "error.user",
                            "There is already a user registered with the user name provided");
        }
        if (bindingResult.hasErrors()) {
        	System.out.println("Error no form");
        	        	
        	return "home.registration.tiles";
        } else {
        	System.out.println("New User");
            userService.saveUser(user);
            modelAndView.addObject("successMessage", "User has been registered successfully");
            modelAndView.addObject("user", new User());
            modelAndView.setViewName("registration");
            
            System.out.println(user.getEmail());
        }
        return "home.bemvindo.tiles";
    }

    @RolesAllowed("ADMIN")
    @RequestMapping(value="/admin/home", method = RequestMethod.GET)
    public String home(Model model){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByUserName(auth.getName());
        
        model.addAttribute("userName", "Welcome " + user.getUserName() + "/" + user.getName() + " " + user.getLastName() + " (" + user.getEmail() + ")");        
        model.addAttribute("adminMessage", "Content Available Only for Users with Admin Role");
        
        return "";
    }


}