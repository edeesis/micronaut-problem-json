package io.micronaut.problem;

import io.micronaut.http.HttpStatus;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Status;
import io.micronaut.problem.violations.ConstraintViolationThrowableProblem;
import io.micronaut.problem.violations.Violation;
import org.zalando.problem.Problem;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@Controller("/product")
public class ProductController {
    @Get
    @Status(HttpStatus.OK)
    public void index() {
        throw Problem.builder()
                .withType(URI.create("https://example.org/out-of-stock"))
                .withTitle("Out of Stock")
                .withStatus(new HttpStatusType(HttpStatus.BAD_REQUEST))
                .withDetail("Item B00027Y5QG is no longer available")
                .with("product", "B00027Y5QG")
                .build();
    }

    @Get("constraint")
    @Status(HttpStatus.OK)
    public void constraintViolation() {
        List<Violation> violations = new ArrayList<>();
        violations.add(new Violation("foo", "bar"));
        throw new ConstraintViolationThrowableProblem(new HttpStatusType(HttpStatus.BAD_REQUEST), violations);
    }
}
