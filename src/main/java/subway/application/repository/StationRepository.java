package subway.application.repository;

import subway.application.domain.Station;

import java.util.List;

public interface StationRepository {

    Station insert(Station station);

    Station findById(Long id);

    List<Station> findAll();

    void update(Station newStation);

    void deleteById(Long id);
}
