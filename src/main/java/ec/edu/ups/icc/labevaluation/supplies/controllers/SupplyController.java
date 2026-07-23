package ec.edu.ups.icc.labevaluation.supplies.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import ec.edu.ups.icc.labevaluation.supplies.dtos.CreateSupplyDto;
import ec.edu.ups.icc.labevaluation.supplies.dtos.SupplyResponseDto;
import ec.edu.ups.icc.labevaluation.supplies.dtos.UpdateSupplyQuantityDto;
import ec.edu.ups.icc.labevaluation.supplies.services.SupplyService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/supplies")

public class SupplyController {

    private final SupplyService service;

    public SupplyController(SupplyService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAnyRole('ADMIN', 'COORDINATOR')")
    public SupplyResponseDto create(
            @Valid @RequestBody CreateSupplyDto dto) {
        return service.create(dto);
    }

    @GetMapping("/low-stock")
    @PreAuthorize("isAuthenticated()")
    public List<SupplyResponseDto> findLowStock(
            @RequestParam(defaultValue = "10") Integer maxQuantity) {
        return service.findLowStock(maxQuantity);
    }

    @PatchMapping("/{id}/quantity")
    @PreAuthorize("hasAnyRole('ADMIN', 'COORDINATOR')")
    public SupplyResponseDto updateQuantity(
            @PathVariable Long id,
            @Valid @RequestBody UpdateSupplyQuantityDto dto) {
        return service.updateQuantity(id, dto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAnyRole('ADMIN', 'COORDINATOR')")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}