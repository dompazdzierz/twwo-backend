package pl.pwr.dissertation.api.controller;

import com.github.javafaker.Faker;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.pwr.dissertation.logic.domain.enums.*;
import pl.pwr.dissertation.persistance.entity.LecturerEntity;
import pl.pwr.dissertation.persistance.entity.Role;
import pl.pwr.dissertation.persistance.entity.StudentEntity;
import pl.pwr.dissertation.persistance.entity.TopicEntity;
import pl.pwr.dissertation.persistance.repository.LecturerRepository;
import pl.pwr.dissertation.persistance.repository.StudentRepository;
import pl.pwr.dissertation.persistance.repository.TopicRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;

@RequiredArgsConstructor
@RestController
@RequestMapping("pumper")
public class PumpController {

    private final PasswordEncoder passwordEncoder;
    private final LecturerRepository lecturerRepository;
    private final TopicRepository topicRepository;
    private final StudentRepository studentRepository;

    private final List<String> roles = List.of(Role.LECTURER, Role.STUDENT);

    @GetMapping("lecturer")
    public void pumpLecturer() {
        LecturerEntity build = LecturerEntity.lecturerBuilder()
                .firstName("Dawid")
                .lastName("Olbinski")
                .password(passwordEncoder.encode("a"))
                .enabled(true)
                .username("a")
                .authorities(Set.of(Role.PROGRAMME_BOARD, Role.LECTURER, Role.STUDENT))
                .build();

        lecturerRepository.save(build);
    }

    @GetMapping("student")
    public void pumpStudent() {
        StudentEntity build = StudentEntity.userBuilder()
                .firstName("Dawid")
                .lastName("Olbinski")
                .password(passwordEncoder.encode("ab"))
                .enabled(true)
                .username("ab")
                .authorities(Set.of(Role.STUDENT))
                .build();

        studentRepository.save(build);
    }


    @GetMapping("topics")
    public void topiCRevisions() {

        Iterable<LecturerEntity> lecturers = getLecturers();
        List<TopicEntity> topics = new ArrayList<>();
        Random random = new Random();
        for (LecturerEntity lecturer : lecturers) {
            int topicNbr = random.nextInt(5) + 5;

            for (int i = 0; i < topicNbr; i++) {
                Faker instance = Faker.instance();
                TopicEntity ml = TopicEntity.builder()
                        .name(instance.book().title())
                        .description(instance.chuckNorris().fact())
                        .descriptionEng(instance.gameOfThrones().quote())
                        .status(TopicStatus.values()[random.nextInt(TopicStatus.values().length)])
                        .faculty(Faculty.values()[random.nextInt(Faculty.values().length)])
                        .field(Field.values()[random.nextInt(Field.values().length)])
                        .level(Level.values()[random.nextInt(Level.values().length)])
                        .promoter(lecturer)
                        .tags(Set.of("ml"))
                        .term(Term.WINTER_2022)
                        .build();
                topics.add(ml);
            }

        }
        this.topicRepository.saveAll(topics);
    }

    private Iterable<LecturerEntity> getLecturers() {

        List<LecturerEntity> lecturerEntities = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            Faker faker = Faker.instance();

            int roleIndex = faker.random().nextInt(roles.size());

            LecturerEntity build = LecturerEntity.lecturerBuilder()
                    .firstName(faker.name().firstName())
                    .lastName(faker.name().lastName())
                    .username(faker.name().username())
                    .password(passwordEncoder.encode(faker.name().username()))
                    .authorities(Set.of(roles.get(roleIndex)))
                    .enabled(true)
                    .build();
            lecturerEntities.add(build);
        }

        return this.lecturerRepository.saveAll(lecturerEntities);
    }


}
