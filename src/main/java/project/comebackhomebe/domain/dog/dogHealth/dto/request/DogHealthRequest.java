package project.comebackhomebe.domain.dog.dogHealth.dto.request;

public record DogHealthRequest(
        String health_status_1,
        String health_status_2,
        String health_status_3,
        String feature
) {
}
