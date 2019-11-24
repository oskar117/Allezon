package pl.edu.pjwstk.jazapp.admin;

import pl.edu.pjwstk.jazapp.entity.TestRepository;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;

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

    public List<String> getSections() {
        return testRepository.getSections();
    }

    public String delete() {
        System.out.println("test");
        return "adminSection.xhtml";
    }
}
