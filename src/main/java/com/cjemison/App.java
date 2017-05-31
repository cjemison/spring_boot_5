package com.cjemison;

import com.cjemison.domain.PersonVO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import java.util.Random;
import java.util.UUID;
import java.util.stream.IntStream;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8;
import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;
import static org.springframework.web.reactive.function.server.RequestPredicates.path;
import static org.springframework.web.reactive.function.server.RouterFunctions.nest;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

/**
 * Created by cjemison on 5/31/17.
 */
@SpringBootApplication
public class App {
  private static final Logger LOGGER = LoggerFactory.getLogger(App.class);

  public static void main(String[] args) throws Exception {
    SpringApplication.run(App.class, args);
  }

  @Bean
  public RouterFunction<ServerResponse> routes() {
    return nest(path("/v1"), nest(accept(APPLICATION_JSON_UTF8),
          route(GET("/person/{id}"), request -> {
            final PersonVO personVO = new PersonVO(UUID.randomUUID().toString(),
                  UUID.randomUUID().toString(),
                  UUID.randomUUID().toString());
            return ok().contentType(MediaType.APPLICATION_JSON_UTF8).body(Mono.just(personVO),
                  PersonVO.class);
          }).andRoute(GET("/person"), request -> {
            Flux<PersonVO> flux = Flux.create(emitter -> {
              IntStream.range(0, Math.abs(new Random().nextInt() % 1000)).boxed()
                    .forEach(integer -> emitter.next(new PersonVO(UUID.randomUUID().toString(),
                          UUID.randomUUID().toString(),
                          UUID.randomUUID().toString())));
              emitter.complete();
            });
            return ok().contentType(MediaType.TEXT_EVENT_STREAM).body(flux, PersonVO.class);
          })).andRoute(POST("/person"), request -> {
      final Mono<PersonVO> mono = request.bodyToMono(PersonVO.class);
      return ok().contentType(MediaType.APPLICATION_JSON_UTF8).body(mono,
            PersonVO.class);
    }));
  }

}
