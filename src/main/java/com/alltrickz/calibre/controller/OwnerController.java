package com.alltrickz.calibre.controller;

import com.alltrickz.calibre.entity.Owner;
import com.alltrickz.calibre.service.OwnerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/owner")
@RequiredArgsConstructor
public class OwnerController {

    private final OwnerService ownerService;

    @PostMapping("/new")
    public ResponseEntity<Owner> createOwner(@RequestBody Owner owner) {
        Owner created = ownerService.createOwner(owner);
        return ResponseEntity.ok(created);
    }

    @GetMapping("/get")
    public ResponseEntity<List<Owner>> getAllOwners() {
        return ResponseEntity.ok(ownerService.getAllOwners());
    }

    @GetMapping("get/{id}")
    public ResponseEntity<Owner> getOwnerById(@PathVariable Long id) {
        return ResponseEntity.ok(ownerService.getOwnerById(id));
    }
}
