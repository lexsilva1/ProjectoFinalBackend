package service;
import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;
import java.util.HashSet;
import java.util.Set;



@ApplicationPath("/rest")
public class ApplicationConfig extends Application{

    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> resources = new HashSet<>();
        resources.add(MultipartFormDataInput.class);
        resources.add(UserService.class);
        resources.add(LabService.class);
        resources.add(ProjectService.class);
        resources.add(SkillService.class);
        resources.add(InterestService.class);
        resources.add(PhotoUploadService.class);
        resources.add(ObjectMapperContextResolver.class);
        resources.add(ResourceService.class);
        return resources;
    }

}

