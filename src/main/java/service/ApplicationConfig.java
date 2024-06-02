package service;
import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;
import org.glassfish.jersey.media.multipart.MultiPartFeature;
import java.util.HashSet;
import java.util.Set;

@ApplicationPath("/rest")
public class ApplicationConfig extends Application{

    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> resources = new HashSet<>();
        resources.add(FileUploadResource.class);
        resources.add(MultiPartFeature.class);
        resources.add(UserService.class);
        resources.add(LabService.class);
        resources.add(ProjectService.class);
        resources.add(SkillService.class);
        return resources;
    }
}
