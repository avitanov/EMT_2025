package finki.ukim.mk.config;

import finki.ukim.mk.model.domain.Country;
import finki.ukim.mk.model.domain.Host;
import finki.ukim.mk.model.domain.User;
import finki.ukim.mk.model.enumerations.Role;
import finki.ukim.mk.repository.AccommodationRepository;
import finki.ukim.mk.repository.CountryRepository;
import finki.ukim.mk.repository.HostRepository;
import finki.ukim.mk.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final CountryRepository countryRepository;
    private final HostRepository hostRepository;


    public DataInitializer(

            UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            CountryRepository countryRepository,
            HostRepository hostRepository
    ) {

        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.countryRepository=countryRepository;
        this.hostRepository=hostRepository;
    }

    public void init() {
        countryRepository.save(new Country("Macedonia","Europe"));
        countryRepository.save(new Country("Japan","Asia"));
        countryRepository.save(new Country("Canada","America"));


        //        categoryRepository.save(new Category("Food", "Food category"));
//        categoryRepository.save(new Category("Music", "Music category"));
//
//        manufacturerRepository.save(new Manufacturer("Nike", "USA"));
//        manufacturerRepository.save(new Manufacturer("KFC", "USA"));
//        manufacturerRepository.save(new Manufacturer("A Records", "UK"));

        userRepository.save(new User(
                "av",
                passwordEncoder.encode("av"),
                "Atanas",
                "Vitanov",
                Role.ROLE_ADMIN
        ));
    }
}

