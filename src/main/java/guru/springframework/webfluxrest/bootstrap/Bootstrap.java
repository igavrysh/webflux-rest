package guru.springframework.webfluxrest.bootstrap;

import guru.springframework.webfluxrest.domain.Category;
import guru.springframework.webfluxrest.domain.Vendor;
import guru.springframework.webfluxrest.repositories.CategoryRepository;
import guru.springframework.webfluxrest.repositories.VendorRepository;
import org.springframework.boot.CommandLineRunner;

public class Bootstrap implements CommandLineRunner {

    private final CategoryRepository categoryRepository;

    private final VendorRepository vendorRepository;

    public Bootstrap(CategoryRepository categoryRepository, VendorRepository vendorRepository) {
        this.categoryRepository = categoryRepository;
        this.vendorRepository = vendorRepository;
    }

    @Override
    public void run(String... args) throws Exception {

        if (categoryRepository.count().block() == 0) {
            // load data
            System.out.println("#### LOADING DATA ON BOOTSTRAP ####");

            categoryRepository.save(Category.builder().description("Fruits").build()).block();

            categoryRepository.save(Category.builder().description("Nuts").build()).block();

            categoryRepository.save(Category.builder().description("Breads").build()).block();

            categoryRepository.save(Category.builder().description("Meats").build()).block();

            categoryRepository.save(Category.builder().description("Eggs").build()).block();

            System.out.println("Loaded Categories: " + categoryRepository.count().block());

            vendorRepository.save(Vendor.builder().firstName("Ievgen").lastName("Gavrysh").build()).block();

            vendorRepository.save(Vendor.builder().firstName("Iryna").lastName("Doroshenko").build()).block();

            System.out.println("Loaded Vendors: " + vendorRepository.count().block());
        }
    }
}
