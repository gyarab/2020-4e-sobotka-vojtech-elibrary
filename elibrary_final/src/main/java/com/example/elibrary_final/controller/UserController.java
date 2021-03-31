package com.example.elibrary_final.controller;

import com.example.elibrary_final.model.Book;
import com.example.elibrary_final.model.ERole;
import com.example.elibrary_final.model.Role;
import com.example.elibrary_final.model.User;
import com.example.elibrary_final.repository.BookRepository;
import com.example.elibrary_final.repository.RoleRepository;
import com.example.elibrary_final.repository.UserRepository;
import com.example.elibrary_final.security.JWTUtils;
import com.example.elibrary_final.security.UserDetailsImpl;
import com.example.elibrary_final.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    UserRepository userRepository;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    JWTUtils jwtUtils;
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    BookRepository bookRepository;

    @PostMapping("/signup")
    public ResponseEntity<? extends Object> signup(@RequestBody SignUpReq signUpReq) {
        if (userRepository.existsByUsername(signUpReq.getUsername())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Username is already taken"));
        }
        User user = new User(signUpReq.getUsername(), passwordEncoder.encode(signUpReq.getPassword()));
        Set<String> stringRoles = signUpReq.getRoles();
        Set<Role> roles = new HashSet<>();
        if (stringRoles == null) {
            Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                    .orElseThrow(() -> new RuntimeException("Role is not found."));
            roles.add(userRole);

        } else {
            stringRoles.forEach(role -> {
                switch (role) {
                    case "admin":
                        Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
                                .orElseThrow(() -> new RuntimeException("Role is not found."));
                        roles.add(adminRole);

                        break;

                    default:
                        Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                                .orElseThrow(() -> new RuntimeException("Role is not found."));
                        roles.add(userRole);
                }
            });
        }

        user.setRoles(roles);
        userRepository.save(user);

        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }

    @PostMapping("/signin")
    public ResponseEntity<?> signIn(@RequestBody SignInReq signInReq) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(signInReq.getUsername(), signInReq.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = jwtUtils.generateJwtToken(authentication);
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());


        return ResponseEntity.ok(new JwtResponse(jwt,
                userDetails.getId(),
                userDetails.getUsername(),
                roles));
    }

    @PostMapping("/logout")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity logoutUser() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        securityContext.setAuthentication(null);
        return ResponseEntity.ok(new MessageResponse("logout successful"));
    }

    @PostMapping("/changePassword")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<MessageResponse> changePassword(@RequestBody ChangePasswordReq changePasswordReq) {

        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<User> optionalUser = userRepository.findByUsername(userDetails.getUsername());
        if (!optionalUser.isEmpty()) {
            System.out.println(optionalUser.get().getUsername());
            if (passwordEncoder.matches(changePasswordReq.getCurrentPassword(), userDetails.getPassword())) {

                User user = optionalUser.get();
                user.setPassword(passwordEncoder.encode(changePasswordReq.getNewPassword()));
                userRepository.save(user);
                System.out.println("here");
                return new ResponseEntity<MessageResponse>(new MessageResponse("Password changed succesfully"), HttpStatus.OK);
            } else {
                return new ResponseEntity<MessageResponse>(new MessageResponse("Current password is not right"), HttpStatus.I_AM_A_TEAPOT);
            }
        } else {
            return new ResponseEntity<MessageResponse>(new MessageResponse("User not found"), HttpStatus.NOT_FOUND);
        }

    }

    @PostMapping("/whoAmI")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<UserDetails> getUserDetails() {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return new ResponseEntity<>(userDetails, HttpStatus.OK);
    }

    @GetMapping("/")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<User>> getAllUsers() {
        List userList = userRepository.findAll();
        if (!userList.isEmpty()) {
            return new ResponseEntity<>(userList, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    @GetMapping("/userDetails")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<User> getUserInfo(@RequestParam String username) {
        Optional<User> optionalUser = userRepository.findByUsername(username);
        if (optionalUser.isPresent()) {
            return new ResponseEntity<>(optionalUser.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    @DeleteMapping("/deleteUser/{username}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<MessageResponse> deleteUser(@PathVariable String username) {
        List<Book> userBooks = bookRepository.findByBelongsToUser(username);
        if (userBooks.isEmpty()) {
            userRepository.deleteByUsername(username);
            return new ResponseEntity<>(new MessageResponse("User deleted successfully"), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new MessageResponse("USer got unreturned books"), HttpStatus.FORBIDDEN);
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/searchByUsername/{username}")
    public ResponseEntity<List<User>> findUsersByString(@PathVariable String username) {
        List<User> userList = userRepository.findByUsernameContaining(username);
        if (!userList.isEmpty()) {
            return new ResponseEntity<>(userList, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    @GetMapping("/setAsAdmin/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<MessageResponse> setUserAsAdmin(@PathVariable String id) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        User user = optionalUser.get();
        Set<Role> roles = new HashSet<>();
        Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
                .orElseThrow(() -> new RuntimeException("Role is not found."));
        roles.add(adminRole);
        user.setRoles(roles);
        userRepository.save(user);
        return new ResponseEntity<MessageResponse>(new MessageResponse("User set as admin"), HttpStatus.OK);
    }


    @PreAuthorize("hasRole('ADMIN') or hasRole('USER') ")
    public ResponseEntity<MessageResponse> deleteUser() {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = userDetails.getUsername();
        List<Book> userBooks = bookRepository.findByBelongsToUser(username);
        if (userBooks.isEmpty()) {
            userRepository.deleteByUsername(username);
            return new ResponseEntity<>(new MessageResponse("User deleted successfully"), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new MessageResponse("USer got unreturned books"), HttpStatus.FORBIDDEN);
        }
    }
}
