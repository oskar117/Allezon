package pl.edu.pjwstk.jazapp.admin;

import pl.edu.pjwstk.jazapp.entity.TestRepository;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@RequestScoped
public class SectionController {

    @Inject
    private SectionRequest sectionRequest;

    @Inject
    private TestRepository testRepository;

    public void addSection() {
        testRepository.addSection(sectionRequest.getName());
    }
}
