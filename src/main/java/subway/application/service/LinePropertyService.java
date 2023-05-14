package subway.application.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import subway.application.domain.LineProperty;
import subway.application.repository.LinePropertyRepository;
import subway.presentation.dto.LineRequest;
import subway.presentation.dto.LineResponse;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class LinePropertyService {

    private final LinePropertyRepository linePropertyRepository;

    public LinePropertyService(LinePropertyRepository linePropertyRepository) {
        this.linePropertyRepository = linePropertyRepository;
    }

    public LineResponse saveLine(LineRequest request) {
        LineProperty lineProperty = linePropertyRepository.insert(
                new LineProperty(null, request.getName(), request.getColor()));
        return LineResponse.of(lineProperty);
    }

    public List<LineResponse> findLineResponses() {
        List<LineProperty> allLineProperties = findLines();
        return allLineProperties.stream()
                .map(LineResponse::of)
                .collect(Collectors.toList());
    }

    public List<LineProperty> findLines() {
        return linePropertyRepository.findAll();
    }

    public LineResponse findLineResponseById(Long id) {
        LineProperty lineProperty = linePropertyRepository.findById(id);
        return LineResponse.of(lineProperty);
    }

    public void updateLine(Long id, LineRequest lineUpdateRequest) {
        linePropertyRepository.update(new LineProperty(id, lineUpdateRequest.getName(), lineUpdateRequest.getColor()));
    }

    public void deleteLineById(Long id) {
        linePropertyRepository.deleteById(id);
    }
}
