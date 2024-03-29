package guru.springframework.webfluxrest.controllers;

import guru.springframework.webfluxrest.domain.Category;
import guru.springframework.webfluxrest.domain.Vendor;
import guru.springframework.webfluxrest.repositories.CategoryRepository;
import guru.springframework.webfluxrest.repositories.VendorRepository;
import org.graalvm.compiler.phases.verify.VerifyBailoutUsage;
import org.reactivestreams.Publisher;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class VendorController {

    private final VendorRepository vendorRepository;

    public VendorController(VendorRepository vendorRepository) {
        this.vendorRepository = vendorRepository;
    }

    @GetMapping("/api/v1/vendors")
    Flux<Vendor> list() {
        return vendorRepository.findAll();
    }

    @GetMapping("/api/v1/vendors/{id}")
    Mono<Vendor> getById(@PathVariable String id) {
        return vendorRepository.findById(id);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/api/v1/vendors")
    Mono<Void> create(@RequestBody Publisher<Vendor> vendorStream) {
        return vendorRepository.saveAll(vendorStream).then();
    }

    @PutMapping("/api/v1/vendors/{id}")
    Mono<Vendor> update(@PathVariable String id, @RequestBody Vendor vendor) {
        vendor.setId(id);
        return vendorRepository.save(vendor);
    }

    @ResponseStatus(HttpStatus.OK)
    @PatchMapping("/api/v1/vendors/{id}")
    Mono<Vendor> patch(@PathVariable String id, @RequestBody Vendor vendor) {

        Vendor foundVendor = vendorRepository.findById(id).block();

        boolean hasChanged = false;

        if (foundVendor.getFirstName() != vendor.getFirstName()) {
            foundVendor.setFirstName(vendor.getFirstName());
            hasChanged = true;
        }

        if (foundVendor.getLastName() != vendor.getFirstName()) {
            foundVendor.setLastName(vendor.getLastName());
            hasChanged = true;
        }

        if (hasChanged) {
            return vendorRepository.save(foundVendor);
        }

        return Mono.just(foundVendor);
    }
}
